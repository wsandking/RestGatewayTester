package com.genband.util.dummy.gateway.controller.model;

public class RequestModel {

    private String serviceName;
    private String message;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestModel [serviceName=" + serviceName + ", message=" + message + "]";
    }

}
