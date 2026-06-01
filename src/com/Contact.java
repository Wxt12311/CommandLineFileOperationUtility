package com;



import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String workUnit;
    private String position;
    private String birthday;
    private String remark;


    private Map<String, String> phones = new LinkedHashMap<>();
    private Map<String, String> addresses = new LinkedHashMap<>();
    private Map<String, String> emails = new LinkedHashMap<>();
    private Map<String, String> onlineContacts = new LinkedHashMap<>();


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getWorkUnit() { return workUnit; }
    public void setWorkUnit(String workUnit) { this.workUnit = workUnit; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public Map<String, String> getPhones() { return phones; }
    public void setPhones(Map<String, String> phones) { this.phones = phones; }
    public Map<String, String> getAddresses() { return addresses; }
    public void setAddresses(Map<String, String> addresses) { this.addresses = addresses; }
    public Map<String, String> getEmails() { return emails; }
    public void setEmails(Map<String, String> emails) { this.emails = emails; }
    public Map<String, String> getOnlineContacts() { return onlineContacts; }
    public void setOnlineContacts(Map<String, String> onlineContacts) { this.onlineContacts = onlineContacts; }
}


