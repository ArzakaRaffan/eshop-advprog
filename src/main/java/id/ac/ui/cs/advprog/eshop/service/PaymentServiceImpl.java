package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData){
        String id = UUID.randomUUID().toString();
        Payment payment = new Payment(id, order, method, paymentData);

        if(method.equals("VOUCHER")){
            String voucherCode = paymentData.get("voucherCode");
            if(voucherCode != null && voucherCode.length() == 16 &&
                    voucherCode.startsWith("ESHOP") &&
                    voucherCode.replaceAll("[^0-9]", "").length() == 8){
                setStatus(payment, "SUCCESS");
            }else{
                setStatus(payment, "REJECTED");
            }
        }else if(method.equals("COD")){
            String address = null;
            String fee = null;
            for (Map.Entry<String, String> entry : paymentData.entrySet()) {
                address = entry.getKey();
                fee = entry.getValue();
            }
            if(address == null || address.isEmpty() || fee == null || fee.isEmpty()){
                setStatus(payment, "REJECTED");
            }else{
                setStatus(payment, "SUCCESS");
            }
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status){
        try {
            payment.setStatus(status);
            if(status.equals("SUCCESS")) {
                payment.getOrder().setStatus("SUCCESS");
            } else if (status.equals("REJECTED")) {
                payment.getOrder().setStatus("FAILED");
            }
            return paymentRepository.save(payment);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments(){
        Iterator<Payment> paymentIterator = paymentRepository.findAll();
        List<Payment> allPayments = new ArrayList<>();
        paymentIterator.forEachRemaining(allPayments::add);
        return allPayments;
    }
}
