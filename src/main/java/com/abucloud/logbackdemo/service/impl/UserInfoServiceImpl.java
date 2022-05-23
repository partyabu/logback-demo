package com.abucloud.logbackdemo.service.impl;

import com.abucloud.logbackdemo.dto.LoginUserInfoDTO;
import com.abucloud.logbackdemo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author party-abu
 * @Date 2022/5/22 9:13
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public LoginUserInfoDTO userLogin() {
        LoginUserInfoDTO loginUserInfoDTO = new LoginUserInfoDTO();
        loginUserInfoDTO.setPassword("12213");
        loginUserInfoDTO.setLoginAccount("fdsfds");
        return loginUserInfoDTO;
    }
}
