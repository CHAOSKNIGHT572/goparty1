package com.example.tansen.goparty1;

import java.io.Serializable;

/**
 * Created by tansen on 8/15/17.
 */

public class User implements Serializable{
    String userName, userEmail, userCell;

    public User() {

    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCell() {
        return userCell;
    }
    public void setUserCell(String userCell) {
        this.userCell = userCell;
    }
}
