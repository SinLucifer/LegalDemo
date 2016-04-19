package org.sin.legaldemo.JavaBean;

import cn.bmob.v3.BmobUser;

public class UserBean extends BmobUser {

    private String nick;
    private boolean sex;
    private boolean isLayer;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isLayer() {
        return isLayer;
    }

    public void setIsLayer(boolean isLayer) {
        this.isLayer = isLayer;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean getSex() {
        return sex;
    }


}

