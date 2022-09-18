package com.kcterala.expensetrackerapi.service;

import com.kcterala.expensetrackerapi.entity.User;
import com.kcterala.expensetrackerapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();
}
