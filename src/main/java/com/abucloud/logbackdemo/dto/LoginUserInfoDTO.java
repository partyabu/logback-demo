package com.abucloud.logbackdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginAccount;

    private String password;

}
