package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    Order order;
    String method;
    Map<String, String> paymentData;
    String status = "PENDING";

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.method = method;
        this.paymentData = paymentData;
        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }
}