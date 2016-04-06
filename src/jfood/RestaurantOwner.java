/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;

/**
 *
 * @author Mazhar
 */
public class RestaurantOwner {
    private String loginId;
    private String password;
    private String role;
    private String name;
    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;
    private String email;
    private String phone;

    public RestaurantOwner(String loginId, String password, String role, String name, String streetAddress, String city, String province, String postalCode, String email, String phone) {
        this.loginId = loginId;
        this.password = password;
        this.role = role;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.email = email;
        this.phone = phone;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
