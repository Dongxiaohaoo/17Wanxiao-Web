package top.dongxiaohao.entity;

public class UserInfo {
    private Integer id;
    private String phone;
    private String pwd;
    private String dev;
    private String email;
    private String temp;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", dev='" + dev + '\'' +
                ", email='" + email + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }

    public UserInfo() {
    }

    public UserInfo(Integer id, String phone, String pwd, String dev, String email, String temp) {
        this.id = id;
        this.phone = phone;
        this.pwd = pwd;
        this.dev = dev;
        this.email = email;
        this.temp = temp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
