/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.dao;

import com.chat.entities.Account;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoangnh
 */
public class DataAccess {
    public DB Connect(){
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("chat_app");
            return db;
        } catch (UnknownHostException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Account FindUser(Account input){
        Account data = new Account();
        DB db = Connect();
        DBCollection account = db.getCollection("account");
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject projection = new BasicDBObject();
        query.put("loginId", input.getLoginId());
        query.put("password", input.getPassword());
        DBCursor cursor = account.find(query, projection);
        while (cursor.hasNext()) {
            BasicDBObject theObj = (BasicDBObject) cursor.next();
            data.setId(theObj.get("_id"));
            data.setLoginId(theObj.getString("loginId"));
            data.setUserName(theObj.getString("userName"));
            data.setPassword_question(theObj.getString("password_question"));
            data.setPassword_answer(theObj.getString("password_answer"));
        }
        return data;
    }
    
    public Boolean CreateUser(Account input){
        Boolean status = false;
        DB db = Connect();
        DBCollection account = db.getCollection("account");
        BasicDBObject document = new BasicDBObject();
        
        document.put("userName", input.getUserName());
        document.put("loginId", input.getLoginId());
        document.put("password", input.getPassword());
        document.put("password_question", input.getPassword_question());
        document.put("password_answer", input.getPassword_answer());
        try {
            account.insert(document);
            return true;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return status;
    }
}
