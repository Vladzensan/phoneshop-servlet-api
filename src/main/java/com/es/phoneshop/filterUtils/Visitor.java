package com.es.phoneshop.filterUtils;

public class Visitor {
    private String remoteAddress;

    public Visitor(String remoteAddress){
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}
