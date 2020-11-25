package com.lxy.tc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterForm {

    private String username;

    private String password;

    private String repassword;


}
