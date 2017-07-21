/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.rest;

import com.chat.entities.Account;
import com.chat.entities.Info;
import com.chat.business.CommonBusiness;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    
    @POST
    @Path("/login")
    @Produces("application/json")
    public Info login(Account input) {
        Info response = function.Login(input);
        return response;
    }
    
    /**
     * Creates new user
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
     */
    @POST
    @Path("/checkuser")
    @Produces("application/json")
    public Info checkDuplicate(Account input) {
        Info response = function.CheckDuplicateUser(input);
        return response;
    }

}
