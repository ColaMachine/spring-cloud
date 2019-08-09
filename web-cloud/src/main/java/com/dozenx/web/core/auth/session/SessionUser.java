package com.dozenx.web.core.auth.session;

import java.io.Serializable;

/**
 * 存入session中的用户对象
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:19 2018/1/2
 * @Modified By:
 */
public class SessionUser implements Serializable {
    /** 用户id **/
    private Long userId;
    /** 用户名称 **/
    private String userName;
    /** 用户账号 **/
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /** 用户角色 **/

    private String role;

    private String phone;
    private int sex;
    private String address;
    private String birth;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    private String nick ;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    private String face;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
