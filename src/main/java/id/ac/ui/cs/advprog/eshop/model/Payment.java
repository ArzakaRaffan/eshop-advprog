package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "PENDING";
        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }

    public void setStatus(String status){
        if(PaymentStatus.contains(status)){
            this.status = status;
        }else{
            throw new IllegalArgumentException();
        }

    }
}