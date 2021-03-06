package com.evilgeniuses.memoryhack.Model;

public class User {
    public String userID;
    public String userUsername;
    public String userUsernameSearch;
    public String userProfileImageURL;
    public String userName;
    public String userLastname;
    public String userEmail;
    public String userPassword;
    public String userStatus;
    public String userNumber;
    public String userFrendNumber;
    public String userPercent;

    public User() {
    }

    public User(String userID, String userUsername, String userUsernameSearch, String userProfileImageURL, String userName, String userLastname, String userEmail, String userPassword, String userStatus, String userNumber, String userFrendNumber, String userPercent) {
        this.userID = userID;
        this.userUsername = userUsername;
        this.userUsernameSearch = userUsernameSearch;
        this.userProfileImageURL = userProfileImageURL;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userID = userEmail;
        this.userID = userPassword;
        this.userStatus = userStatus;
        this.userNumber = userNumber;
        this.userFrendNumber = userFrendNumber;
        this.userPercent = userPercent;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserUsernameSearch() {
        return userUsernameSearch;
    }

    public void setUserUsernameSearch(String userUsernameSearch) {
        this.userUsernameSearch = userUsernameSearch;
    }

    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserFrendNumber() {
        return userFrendNumber;
    }

    public void setUserFrendNumber(String userFrendNumber) {
        this.userFrendNumber = userFrendNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPercent() {
        return userPercent;
    }

    public void setUserPercent(String userPercent) {
        this.userPercent = userPercent;
    }
}
