package com.example.sargiskh.expensecalculation.expensecalculation.models;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECNotesListModel {

    private long groupId = -1;
    private String groupName = "";
    private int priority = -1;

    public ECNotesListModel() {

    }

    public ECNotesListModel(long _groupId, String _groupName, int _priority) {
        groupId = _groupId;
        groupName = _groupName;
        priority = _priority;
    }

    public void setGroupId(long _groupId) {
        groupId = _groupId;
    }
    public long getGroupId() {
        return groupId;
    }

    public void setGroupName(String _group) {
        groupName = _group;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setPriority(int _priority) {
        priority = _priority;
    }
    public int getPriority() {
        return priority;
    }
}
