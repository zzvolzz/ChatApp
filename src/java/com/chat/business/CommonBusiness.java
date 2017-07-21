/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.business;

import com.chat.dao.DataAccess;
import com.chat.entities.Account;
import com.chat.entities.Info;

/**
 *
 * @author hoangnh
 */
public class CommonBusiness {
    DataAccess dao = new DataAccess();
    
    public Info Login(Account input){
        Info response = new Info();
        Account user = dao.FindUser(input);
        if(input.getLoginId().equals(user.getLoginId())) {
            response.setStatus("success");
            response.setMessage("user allow login");
            response.setCode("201");
        }else{
            response.setStatus("error");
            response.setMessage("invalid user or password");
        }
        return response;
    }
    
    public Info CheckDuplicateUser(Account input){
        Info response = new Info();
        Account user = dao.FindUser(input);
        if(input.getLoginId().equals(user.getLoginId())) {
            response.setStatus("error");
            response.setMessage("this user id has been selected");
        }else{
            response.setStatus("success");
            response.setMessage("you can select this user id");
        }
        return response;
    }
    
    public Info CreateAccount(Account input){
        Info response = new Info();
        Boolean isCreate = dao.CreateUser(input);
        if(isCreate){
            response.setStatus("success");
            response.setMessage("Created success");
        }else{
            response.setStatus("error");
            response.setMessage("Create fail");
        }
        return response;
    }
}
