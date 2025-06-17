package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;

public interface UserService {
    Response userRegister(UserDto userDto);
}
