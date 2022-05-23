package com.abucloud.logbackdemo.controller;

import com.abucloud.logbackdemo.dto.LoginUserInfoDTO;
import com.abucloud.logbackdemo.exception.BusinessException;
import com.abucloud.logbackdemo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("login")
    public LoginUserInfoDTO userLogin(HttpServletRequest request) throws IOException {

        // String string = new String(StreamUtils.copyToByteArray(request.getInputStream()));
        // log.info("login"+string);
        // log.info("form-data param: {}", request.getParameter("a"));
        return this.userInfoService.userLogin();
    }

    @PostMapping("testEx")
    public LoginUserInfoDTO testEx() {
        throw new BusinessException("testEx异常");
    }

}