/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.rest;

import com.chat.entities.Account;
import com.chat.entities.Info;
import com.chat.business.CommonBusiness;
import com.chat.common.MongoObjectIdTypeAdapte;
import com.chat.entities.Group;
import com.chat.entities.ItemSelected;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

/**
 * REST Web Service
 *
 * @author hoangnh
 */
@Path("generic")
public class GenericResource {
    CommonBusiness function = new CommonBusiness();
    
    @Context
    private UriInfo context;
    
    @Context
    private HttpHeaders httpHeaders;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }
    
    /**
     * Login
     * @param input: Account.loginId; Account.password
     * @return Info
     */
    @POST
    @Path("/login")
    @Produces("application/json")
    public Info login(Account input) throws ParseException, UnsupportedEncodingException{
        Info response = function.Login(input);
        return response;
    }
    
    /**
     * Logout
     * @param input: Account.loginId; Account.password
     * @return Info
     */
    @GET
    @Path("/logout")
    @Produces("application/json")
    public Info logout() throws UnsupportedEncodingException {
        Info response = function.Logout(httpHeaders.getRequestHeaders().get("token").toString());
//        System.out.print(context.getAbsolutePath());
//        System.out.print(httpHeaders.getRequestHeaders().get("token"));
//        return response;
        return response;
    }
    
    /**
     * Creates new user
     * @param input: Account
     * @return Info
     */
    @POST
    @Path("/create")
    @Produces("application/json")
    public Info createAccount(Account input) {
        Info response = function.CreateAccount(input);
        return response;
    }
    
    /**
     * Check userId has been selected or not when create user
     * @param input: Account.userId
     * @return Info
     */
    @POST
    @Path("/checkuser")
    @Produces("application/json")
    public Info checkDuplicate(Account input) {
        Info response = function.CheckDuplicateUser(input);
        return response;
    }
    
    /**
     * Reset password
     * @param input: Account.loginId; Account.password_question; Account.password_answer; Account.password; 
     * @return Info
     */
    @POST
    @Path("/changepassword")
    @Produces("application/json")
    public Info changePassword(Account input) {
        Info response = function.ChangePassword(input);
        return response;
    }
    
    /**
     *
     * @param userid: Account.loginId
     * @return Account
     */
    @GET
    @Path("/getUser/{userId}")
    @Produces("application/json")
    public Account loadUserInfo(@PathParam("userId") String userid) {
        Account data = function.GetUser(userid);
        return data;
    }
    
    /**
     * Add friend
     * @param input: 
     * @return Info
     */
    @POST
    @Path("/addfriend")
    @Produces("application/json")
    public Info addFriend(Group input) {
        Info response = function.AddFriend(input);
        return response;
    }
    
    /**
     * Request friend
     * @param input: Group
     * @return Info
     */
    @POST
    @Path("/requestchat")
    @Produces("application/json")
    public Info requestFriend(Group input) {
        Info response = function.RequestChat(input);
        return response;
    }
    
    /**
     * Load all request chat of user
     * @param userid: Account.loginId
     * @return List<Group>
     */
    @GET
    @Path("/getUserRequest/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String loadUserRequest(@PathParam("userId") String userid) {
        List<Group> output = function.GetUserRequest(userid);
        
        GsonBuilder builder = new GsonBuilder(); 
        builder.registerTypeAdapter(ObjectId.class, new MongoObjectIdTypeAdapte()); 
        Gson gson = builder.create();
        
        
        String json = gson.toJson(output);
        return json;
    }
    
    /**
     * Accept request chat from group or user
     * @param userid: ItemSelected
     * @return Info
     */
    @POST
    @Path("/acceptrequest")
    @Produces("application/json")
    public Info acceptRequest(ItemSelected input) {
        Info response = function.AcceptRequest(input);
        return response;
    }
    
    
    /**
     * Load all user or group after accept request
     * @param userid: Account.loginId
     * @return List<Group>
     */
    @GET
    @Path("/getAccepted/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String loadAcceptedChat(@PathParam("userId") String userid) {
        List<Group> output = function.GetAcceptedChat(userid);
        
        GsonBuilder builder = new GsonBuilder(); 
        builder.registerTypeAdapter(ObjectId.class, new MongoObjectIdTypeAdapte()); 
        Gson gson = builder.create();
        
        
        String json = gson.toJson(output);
        return json;
    }

}
