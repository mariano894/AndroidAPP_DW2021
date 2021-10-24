package com.umg.primarydetail01.data;

import com.umg.primarydetail01.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser promptUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Usuario DW2021");
            return new Result.Success<>(promptUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}