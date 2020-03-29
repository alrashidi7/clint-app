package com.example.myhamada;


public class Orders {
    private String order;
    private String status;
    private String order_id;


    public Orders() {
    }

    public Orders(String order, String status, String order_id) {
        this.order = order;
        this.status = status;
        this.order_id = order_id;
    }

    public String getOrder() {
        return order;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder_id() {
        return order_id;
    }
}