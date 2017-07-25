/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.dao;

import com.chat.common.Common;
import com.chat.entities.Account;
import com.chat.entities.Group;
import com.chat.entities.ItemSelected;
import com.chat.entities.Tag;
import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author hoangnh
 */
public class DataAccess {
    public DB Connect(){
        try {
            MongoClient mongo = new MongoClient(Common.host, Common.port);
            DB db = mongo.getDB(Common.db);
            return db;
        } catch (UnknownHostException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public Boolean SaveToken(String token){
//        Boolean status = false;
//        DB db = Connect();
//        DBCollection tok = db.getCollection(Common.token);
//        BasicDBObject document = new BasicDBObject();
//        
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        document.put("token", token);
//        document.put("time", dateFormat.format(date));
//        try {
//            tok.insert(document);
//            return true;
//        } catch (Exception e) {
//            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return status;
//    }
    
//    public Boolean Logout(String token){
//        Boolean status = false;
//        DB db = Connect();
//        DBCollection tok = db.getCollection(Common.token);
//        BasicDBObject document = new BasicDBObject();
//        document.put("token", token);
//        try {
//            tok.remove(document);
//            return true;
//        } catch (Exception e) {
//            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return status;
//    }
    
    public Account FindUser(Account input){
        Account data = new Account();
        DB db = Connect();
        DBCollection account = db.getCollection(Common.account);
        
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
            data.setEmail(theObj.getString("email"));
            data.setPhone(theObj.getString("phone"));
        }
        return data;
    }
    
    public Account FindUserId(Account input){
        Account data = new Account();
        DB db = Connect();
        DBCollection account = db.getCollection(Common.account);
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject projection = new BasicDBObject();
        query.put("loginId", input.getLoginId());
        DBCursor cursor = account.find(query, projection);
        while (cursor.hasNext()) {
            BasicDBObject theObj = (BasicDBObject) cursor.next();
            data.setId(theObj.get("_id"));
            data.setLoginId(theObj.getString("loginId"));
            data.setUserName(theObj.getString("userName"));
            data.setPassword_question(theObj.getString("password_question"));
            data.setPassword_answer(theObj.getString("password_answer"));
            data.setEmail(theObj.getString("email"));
            data.setPhone(theObj.getString("phone"));
        }
        return data;
    }
    
    public Boolean CreateUser(Account input){
        Boolean status = false;
        DB db = Connect();
        DBCollection account = db.getCollection(Common.account);
        BasicDBObject document = new BasicDBObject();
        
        document.put("userName", input.getUserName());
        document.put("loginId", input.getLoginId());
        document.put("password", input.getPassword());
        document.put("password_question", input.getPassword_question());
        document.put("password_answer", input.getPassword_answer());
        document.put("email", input.getEmail());
        document.put("phone", input.getPhone());
        try {
            account.insert(document);
            return true;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return status;
    }
    
    public Boolean ChangePassword(Account input){
        Boolean status = false;
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection account = db.getCollection(Common.account);
        BasicDBObject filter = new BasicDBObject();
        BasicDBObject document = new BasicDBObject();
        filter.put("loginId", input.getLoginId());
        filter.put("password_question", input.getPassword_question());
        filter.put("password_answer", input.getPassword_answer());
        document.append("$set", new BasicDBObject().append("password", input.getPassword()));
        try {
            WriteResult wr = account.update(filter, document);
            Boolean isUpdate = (Boolean) wr.getField("updatedExisting");
            return isUpdate;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return status;
    }
    
    public Account GetUser(String input){
        Account data = new Account();
        DB db = Connect();
        DBCollection account = db.getCollection(Common.account);
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject projection = new BasicDBObject();
        query.put("loginId", input);
        DBCursor cursor = account.find(query, projection);
        while (cursor.hasNext()) {
            BasicDBObject theObj = (BasicDBObject) cursor.next();
            data.setId(theObj.get("_id"));
            data.setLoginId(theObj.getString("loginId"));
            data.setUserName(theObj.getString("userName"));
            data.setPassword_question(theObj.getString("password_question"));
            data.setPassword_answer(theObj.getString("password_answer"));
            data.setEmail(theObj.getString("email"));
            data.setPhone(theObj.getString("phone"));
        }
        return data;
    }
    
    public Boolean RequestChat(Group input){
        Boolean status = false;
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection group = db.getCollection(Common.group);
        
        BasicDBList groupCreator = new BasicDBList();
        BasicDBObject creator = new BasicDBObject();
        for (Tag temp : input.getGroupCreator()) {
            creator.put("name", temp.getName());
            creator.put("id", temp.getId());
            creator.put("status", Common.in);
            groupCreator.add(creator);
        }
        
        BasicDBList groupMember = new BasicDBList();
        for (Tag temp : input.getGroupMember()) {
            BasicDBObject member = new BasicDBObject();
            member.put("name", temp.getName());
            member.put("id", temp.getId());
            if(creator.getString("id").equals(member.getString("id"))){
                member.put("status", Common.in);
            }else{
                member.put("status", Common.wait);
            }
            groupMember.add(member);
        }
        
        BasicDBObject document = new BasicDBObject();
        document.put("groupName", input.getGroupName());
        document.put("groupType", input.getGroupType());
        document.put("groupMember", groupMember);
        document.put("groupCreator", groupCreator);
        
        try {
            group.insert(document);
            return true;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return  status;
    }
    
    public List<Group> GetUserRequest(String input){
        List<Group> data = new ArrayList<Group>();
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection group = db.getCollection(Common.group);
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject document = new BasicDBObject();
        BasicDBObject projection = new BasicDBObject();
        document.put("id", input);
        document.put("status", Common.wait);
        query.append("$elemMatch", document);
        DBCursor cursor = group.find(new BasicDBObject().append("groupMember", query), projection);
        while (cursor.hasNext()) {
            BasicDBObject theObj = (BasicDBObject) cursor.next();
            List<Tag> creator = (List<Tag>) theObj.get("groupCreator");
            Group item = new Group();
            item.setId(theObj.get("_id"));
            item.setGroupName(theObj.getString("groupName"));
            item.setGroupType(theObj.getString("groupType"));
            item.setGroupCreator(creator);
            data.add(item);
        }
        return data;
    }
    
    public Boolean AcceptRequest(ItemSelected input){
        Boolean status = false;
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection group = db.getCollection(Common.group);
        
        
        BasicDBObject filter = new BasicDBObject();
        BasicDBObject document = new BasicDBObject();
        filter.put("_id", new ObjectId(input.getId()));
        filter.put("groupMember.id", input.getLoginId());
        document.append("$set", new BasicDBObject().append("groupMember.$.status", Common.in));
        try {
            WriteResult wr = group.update(filter, document);
            Boolean isUpdate = (Boolean) wr.getField("updatedExisting");
            return isUpdate;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return status;
    }
    
    public List<Group> GetAcceptedChat(String input){
        List<Group> data = new ArrayList<Group>();
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection group = db.getCollection(Common.group);
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject document = new BasicDBObject();
        BasicDBObject projection = new BasicDBObject();
        document.put("id", input);
        document.put("status", Common.in);
        query.append("$elemMatch", document);
        DBCursor cursor = group.find(new BasicDBObject().append("groupMember", query), projection);
        while (cursor.hasNext()) {
            BasicDBObject theObj = (BasicDBObject) cursor.next();
            List<Tag> creator = (List<Tag>) theObj.get("groupCreator");
            Group item = new Group();
            item.setId(theObj.get("_id"));
            item.setGroupName(theObj.getString("groupName"));
            item.setGroupType(theObj.getString("groupType"));
            item.setGroupCreator(creator);
            data.add(item);
        }
        return data;
    }
    
    
    
    
    
    
    
    
    
    
    
    public Boolean AddFriend(Group input){
        Boolean status = false;
        DB db = Connect();
        db.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DBCollection group = db.getCollection(Common.group);
        
        BasicDBList groupMember = new BasicDBList();
        for (Tag temp : input.getGroupMember()) {
            BasicDBObject member = new BasicDBObject();
            member.put("name", temp.getName());
            member.put("id", temp.getId());
            member.put("status", temp.getStatus());
            groupMember.add(member);
        }
        
        BasicDBList groupCreator = new BasicDBList();
        for (Tag temp : input.getGroupCreator()) {
            BasicDBObject creator = new BasicDBObject();
            creator.put("name", temp.getName());
            creator.put("id", temp.getId());
            creator.put("status", temp.getStatus());
            groupCreator.add(creator);
        }
        
        BasicDBObject document = new BasicDBObject();
        document.put("groupName", input.getGroupName());
        document.put("groupType", input.getGroupType());
        document.put("groupMember", groupMember);
        document.put("groupCreator", groupCreator);
        
        try {
            group.insert(document);
            return true;
        } catch (Exception e) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, e);
        }
        return status;
    }
}
