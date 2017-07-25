/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.common;

/**
 *
 * @author hoangnh
 */
public class Common {
    //host db
    public static String host = "localhost";
    public static Integer port = 27017;
    
    //db name
    public static String db = "chat_app";
    
    //colection name
    public static String account = "account";
    public static String group = "group_chat";
    public static String token = "token_storage";
    
    //group state
    public static String in = "In group";
    public static String leave = "Left group";
    public static String wait = "Invited";
    
    //time out token
    public static Integer hour = 12;
    
    //token private key
    public static String private_key = "secret";
}
