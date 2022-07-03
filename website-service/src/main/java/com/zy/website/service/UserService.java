package com.zy.website.service;

import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.request.UserLoginRequest;

public interface UserService {
    ApiReturn userLogin(UserLoginRequest request);

    ApiReturn userLogout();
}
