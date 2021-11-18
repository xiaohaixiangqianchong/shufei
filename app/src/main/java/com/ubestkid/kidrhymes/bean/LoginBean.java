package com.ubestkid.kidrhymes.bean;

/**
 * @Des
 * @Date 2020/5/9
 * @Author SunSt
 */
public class LoginBean {

    /**
     * avatarPic : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3167517591,3029646911&fm=11&gp=0.jpg
     * role : null
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MDAwMyIsImFjY291bnRJZCI6IjE3NzI1YzVlMzBjYjRmNmE5ZGQ4NDVlMTU3MjM4NmU3IiwiY2l0eUlkIjpudWxsLCJleHAiOjE1ODk1NjA4NDEsImNyZWF0ZWQiOjE1ODg5NTYwNDEzMzV9.dhsKgybZy5xbrF8MxRgHcT-Eyw11ADKuide_rN0sarJIZOF_jaS5_Xg76VMk0LQ3lz_URROys0ThxmKHLvQUQQ
     */

    private String avatarPic;
    private String role;
    private String token;

    public String getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(String avatarPic) {
        this.avatarPic = avatarPic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
