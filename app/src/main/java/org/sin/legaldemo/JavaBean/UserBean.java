package org.sin.legaldemo.JavaBean;

import cn.bmob.v3.BmobUser;

public class UserBean extends BmobUser {

    private String nick;
    private boolean sex;
    private boolean isLayer;
    private Task mTask;

    public Task getmTask() {
        return mTask;
    }

    public void setmTask(Task mTask) {
        this.mTask = mTask;
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

