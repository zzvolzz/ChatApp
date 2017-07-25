/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.entities;

import java.util.List;

/**
 *
 * @author hoangnh
 */
public class Group {
    private Object _id;
    private String groupName;
    private String groupType;
    private List<Tag> groupMember;
    private List<Tag> groupCreator;

    public Object getId() {
        return _id;
    }

    public void setId(Object _id) {
        this._id = _id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<Tag> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(List<Tag> groupMember) {
        this.groupMember = groupMember;
    }

    public List<Tag> getGroupCreator() {
        return groupCreator;
    }

    public void setGroupCreator(List<Tag> groupCreator) {
        this.groupCreator = groupCreator;
    }
}
