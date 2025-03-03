package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Arrays;
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
        if(order == null){
            throw new IllegalArgumentException();
        }else{
            this.order = order;
        }
    }

    public void setStatus(String status){
        String[] statusList = {"SUCCESS", "REJECTED"};
        if(Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        }else{
            this.status = status;
            if(status.equals("SUCCESS")){
                this.order.setStatus("SUCCESS");
            }else if(status.equals("REJECTED")){
                this.order.setStatus("FAILED");
            }
        }
    }
}
