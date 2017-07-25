/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.business;

import com.chat.common.BitGenerator;
import com.chat.common.TokenValidate;
import com.chat.dao.DataAccess;
import com.chat.entities.Account;
import com.chat.entities.Group;
import com.chat.entities.Info;
import com.chat.entities.ItemSelected;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

/**
 *
 * @author hoangnh
 */
public class CommonBusiness {
    DataAccess dao = new DataAccess();
    BitGenerator token = new BitGenerator();
    TokenValidate token_validate = new TokenValidate();
    
    @Context private HttpServletRequest request;
    
    public Info Login(Account input) throws ParseException, UnsupportedEncodingException{
        Info response = new Info();
        Account user = dao.FindUser(input);
        if(input.getLoginId().equals(user.getLoginId())) {
            
            String subject = input.getLoginId() + "/" + input.getPassword();
            String name = input.getUserName();
            String scope = "admin";
            
//            HttpSession session = request.getSession();
//            session.setAttribute("subject", subject);
//            session.setAttribute("name", name);
//            session.setAttribute("scope", scope);
            
//            String tok = token.nextSessionId();
            String tok = token_validate.GenerateToken(subject, name, scope);
            
            response.setStatus("success");
            response.setMessage("user allow login");
            response.setToken(tok);
        }else{
            response.setStatus("error");
            response.setMessage("invalid user or password");
        }
        return response;
    }
    
    public Info Logout(String tok) throws UnsupportedEncodingException{
        Info response = new Info();
//        HttpSession session = request.getSession();
//        String subject = session.getAttribute("subject").toString();
        String subject = "dfggf";
        Boolean isLogout = token_validate.CheckToken(tok, subject);
        if(isLogout) {
            response.setStatus("success");
            response.setMessage("user logout");
        }else{
            response.setStatus("error");
            response.setMessage("invalid token");
        }
        return response;
    }
    
    public Info CheckDuplicateUser(Account input){
        Info response = new Info();
        Account user = dao.FindUserId(input);
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
    
    public Info ChangePassword(Account input){
        Info response = new Info();
        Boolean isChange = dao.ChangePassword(input);
        if(isChange){
            response.setStatus("success");
            response.setMessage("Change success");
        }else{
            response.setStatus("error");
            response.setMessage("Change fail");
        }
        return response;
    }
    
    public Account GetUser(String input){
        Account user = dao.GetUser(input);
        return user;
    }
    
    public Info RequestChat(Group input){
        Info response = new Info();
        Boolean isAdd = dao.RequestChat(input);
        if(isAdd){
            response.setStatus("success");
            response.setMessage("Request success");
        }else{
            response.setStatus("error");
            response.setMessage("Request fail");
        }
        return response;
    }
    
    public List<Group> GetUserRequest(String input){
        List<Group> data = dao.GetUserRequest(input);
        return data;
    }
    
    public Info AcceptRequest(ItemSelected input){
        Info response = new Info();
        Boolean isAccept = dao.AcceptRequest(input);
        if(isAccept){
            response.setStatus("success");
            response.setMessage("Accept success");
        }else{
            response.setStatus("error");
            response.setMessage("Accept fail");
        }
        return response;
    }
    
    public List<Group> GetAcceptedChat(String input){
        List<Group> data = dao.GetAcceptedChat(input);
        return data;
    }
    
    
    
    
    
    
    
    
    
    
    public Info AddFriend(Group input){
        Info response = new Info();
        Boolean isAdd = dao.AddFriend(input);
        if(isAdd){
            response.setStatus("success");
            response.setMessage("Add success");
        }else{
            response.setStatus("error");
            response.setMessage("Add fail");
        }
        return response;
    }
}
