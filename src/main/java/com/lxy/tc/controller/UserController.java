package com.lxy.tc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxy.tc.pojo.Comment;
import com.lxy.tc.pojo.Question;
import com.lxy.tc.pojo.User;
import com.lxy.tc.pojo.UserInfo;
import com.lxy.tc.serivce.CommentService;
import com.lxy.tc.serivce.QuestionService;
import com.lxy.tc.serivce.UserInfoService;
import com.lxy.tc.serivce.UserService;
import com.lxy.tc.vo.LayerPhoto;
import com.lxy.tc.vo.LayerPhotoData;
import com.lxy.tc.vo.RegisterForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * @Author tudte
 * @Date 2020/11/7 13:46
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model) {
        log.info("注册表单信息：{}", registerForm.toString());
        // 判断两次密码输入是否一致
        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            model.addAttribute("registerMsg", "密码输入有误");
            return "register";
        }
        // 用户名已存在
        User hasUser = userService.getOne(new QueryWrapper<User>().eq("username", registerForm.getUsername()));
        if (hasUser != null) {
            model.addAttribute("registerMsg", "用户名已存在");
            return "register";
        }
        // 创建用户
        User user = new User();
        user.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setRoleId(2);
        user.setUsername(registerForm.getUsername());
        // 密码加密
        String bCryptPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
        user.setPassword(bCryptPassword);
        user.setGmtCreate(new Timestamp(new Date().getTime()));
        user.setLoginDate(new Timestamp(new Date().getTime()));
        // 保存对象！
        userService.save(user);
        log.info("注册成功");


        userInfoService.save(new UserInfo().setUid(user.getUid()));

        return "redirect:/toLogin";

    }


    @GetMapping("/user/{uid}")
    public String userIndex(@PathVariable String uid, Model model,HttpSession session) {
        /*if(uid==null){
            User loginUser = (User) session.getAttribute("loginUser");
            uid = loginUser.getUid();
        }*/
        // 用户信息回填
        userInfoCallBack(uid,model);

        Page<Question> pageParam = new Page<>(1, 10);
        questionService.page(pageParam, new QueryWrapper<Question>().eq("author_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Question> blogList = pageParam.getRecords();
        model.addAttribute("questionList", blogList);
        model.addAttribute("pageParam", pageParam);

         Page<Comment> pageParam2 = new Page<>(1, 10);
        commentService.page(pageParam2, new QueryWrapper<Comment>().eq("user_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Comment> commentList = pageParam2.getRecords();
        model.addAttribute("commentList", commentList);
        model.addAttribute("pageParam2", pageParam2);


        return "user/home";
    }


    @GetMapping("/user/question/{uid}/{page}/{limit}")
    public String userIndexQuestion(@PathVariable String uid,
                                    @PathVariable int page,
                                    @PathVariable int limit,
                                    Model model) {
        // 用户信息回填
        userInfoCallBack(uid, model);

        //
        if (page < 1) {
            page = 1;
        }
        Page<Question> pageParam = new Page<>(page, limit);
        questionService.page(pageParam, new QueryWrapper<Question>().eq("author_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Question> blogList = pageParam.getRecords();
        model.addAttribute("questionList", blogList);
        model.addAttribute("pageParam", pageParam);

        return "user/home";
    }

    @GetMapping("/user/comment/{uid}/{page}/{limit}")
    public String userIndexComment(@PathVariable String uid,
                                   @PathVariable int page,
                                   @PathVariable int limit,
                                   Model model) {
        // 用户信息回填
        userInfoCallBack(uid, model);
        //
        if (page < 1) {
            page = 1;
        }
        Page<Comment> pageParam2 = new Page<>(page, limit);
        commentService.page(pageParam2, new QueryWrapper<Comment>().eq("user_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Comment> commentList = pageParam2.getRecords();
        model.addAttribute("commentList", commentList);
        model.addAttribute("pageParam2", pageParam2);

        return "user/home";
    }

    // 用户信息回填
    private void userInfoCallBack(String uid, Model model) {
        log.info("uid:{}",uid);
        UserInfo userInfo = userInfoService.getById(uid);
        log.info("{}",userInfo);
        model.addAttribute("userInfo", userInfo);
        if (userInfo.getHobby() != null && !userInfo.getHobby().equals("")) {
            String[] hobbys = userInfo.getHobby().split(",");
            model.addAttribute("infoHobbys", hobbys);
        }


        int questionCount = questionService.count(new QueryWrapper<Question>().eq("author_id", uid));
        int commentCount = commentService.count(new QueryWrapper<Comment>().eq("user_id", uid));

        model.addAttribute("questionCount", questionCount);
        model.addAttribute("commentCount", commentCount);

    }

    // 捐赠layer弹窗二维码
    @GetMapping("/user/donate/{uid}")
    @ResponseBody
    public String userLayerDonate(@PathVariable String uid) {
        // todo: 数据库设计
        ArrayList<LayerPhotoData> layerPhotos = new ArrayList<>();

        layerPhotos.add(new LayerPhotoData().setAlt("支付宝").setPid(1).setSrc("/images/donate/alipay.png").setThumb(""));
        layerPhotos.add(new LayerPhotoData().setAlt("微信").setPid(2).setSrc("/images/donate/wechat.jpg").setThumb(""));

        LayerPhoto donate = new LayerPhoto().setTitle("赞赏").setId(666).setStart(1);
        donate.setData(layerPhotos);

        String donateJsonString = JSONObject.toJSONString(donate);
        log.info(donateJsonString);
        return donateJsonString;
    }


    @GetMapping("/loginUser")
    public String loginUser(HttpSession session) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loginUser = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername()));
        session.setAttribute("loginUser", loginUser);
        return "index";
    }
}
