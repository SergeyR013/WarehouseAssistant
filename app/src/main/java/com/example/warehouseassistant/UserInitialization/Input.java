package com.example.warehouseassistant.UserInitialization;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Input {
    private String password;
    private String username;
    private String role;
    private String fullName;
    private String department;

    public void SetData(String password, String username, String role, String fullname, String department) throws NoSuchAlgorithmException {

        setPassword(password);
        setUsername(username);
        setRole(role);
        setFullName(fullname);
        setDepartment(department);
    }

    private void setDepartment(String department) {
        this.department = department;
    }

    private void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String pwd  = new BigInteger(1, hashedPassword).toString(16);
        this.password = pwd;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
