package com.example.demo.context;

public class ServiceContext {
    private String sid;

    public ServiceContext(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
