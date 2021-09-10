package model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String useName;
    private String password;
    private String fullName;
    private String email;
    private String numberPhone;
    private String status;
    private String sex;
    private String address;
    private String role;
    private List<Cart> carts;
    private List<Bill> bills;

    public User() {
    }

    public User(String useName, String password) {
        this.useName = useName;
        this.password = password;
    }

    public User(String useName, String password, String fullName, String email, String address, String numberPhone, String sex) {
        this.useName = useName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.numberPhone = numberPhone;
        this.sex = sex;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}


