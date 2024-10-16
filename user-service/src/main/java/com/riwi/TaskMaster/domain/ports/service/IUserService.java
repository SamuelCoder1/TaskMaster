package com.riwi.TaskMaster.domain.ports.service;


import com.riwi.RiwiTech.application.dtos.requests.UserRequest;
import com.riwi.RiwiTech.application.dtos.requests.UserWithoutId;
import com.riwi.RiwiTech.application.dtos.requests.UserWithoutIdAndRole;
import com.riwi.RiwiTech.application.dtos.responses.UserDTO;
import com.riwi.RiwiTech.application.services.generic.*;
import com.riwi.RiwiTech.domain.entities.User;

public interface IUserService extends
        Create<User, UserWithoutId>,
        Register<User, UserWithoutIdAndRole>,
        Update<Long, User, UserWithoutId>,
        Delete<Long>,
        ReadById<User, Long>,
        ReadAll<User>{
    }
