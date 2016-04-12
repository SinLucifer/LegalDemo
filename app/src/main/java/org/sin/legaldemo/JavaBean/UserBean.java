package org.sin.legaldemo.JavaBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Sin on 2016/4/12.
 */
public class UserBean extends BmobUser {

    private String nick;
    private boolean sex;
    private boolean isLayer;

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

    public boolean getSex(){
        return sex;
    }


}

