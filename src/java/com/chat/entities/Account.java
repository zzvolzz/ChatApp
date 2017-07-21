/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.entities;

/**
 *
 * @author hoangnh
 */
public class Account {
    private Object _id;
    private String userName;
    private String loginId;
    private String password;
    private String password_question;
    private String password_answer;

    public Object getId() {
        return _id;
    }

    public void setId(Object _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_question() {
        return password_question;
    }

    public void setPassword_question(String password_question) {
        this.password_question = password_question;
    }

    public String getPassword_answer() {
        return password_answer;
    }

    public void setPassword_answer(String password_answer) {
        this.password_answer = password_answer;
    }
    
    

}
