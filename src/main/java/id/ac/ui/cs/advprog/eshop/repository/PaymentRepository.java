package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    public Payment save(Payment payment){
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getId().equals(payment.getId())) {
                payments.set(i, payment);
                return payment;
            }
        }
        payments.add(payment);
        return payment;
    }

    public Payment findById(String paymentId){
        for(Payment savedPayment: payments){
            if(savedPayment.getId().equals(paymentId)){
                return savedPayment;
            }
        }
        return null;
    }

    public Iterator<Payment> findAll(){
        return payments.iterator();
    }
}
