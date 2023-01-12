package com.example.demo.context;

public class CmpData {
    private String host;
    private String role;

    public CmpData() {
    }

    public CmpData(String host, String role) {
        this.host = host;
        this.role = role;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
