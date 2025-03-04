package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentRepositoryTest {
    @InjectMocks
    PaymentRepository paymentRepository;

    List<Payment> payments = new ArrayList<>();

    @BeforeEach
    void setUp(){
        paymentRepository = new PaymentRepository();
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb5589fE-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order(
                "13625556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
        Payment payment1 = new Payment(
                "10287-a9ke90-001k-b5y6-542k203k5j",
                order1,
                "Voucher Code",
                Map.of("ESHOP1234ABC5678", "SUCCESS")
        );
        payments.add(payment1);

        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        );
        Payment payment2 = new Payment(
                "7hdk5sf-58fg-913h-abed-cajoled691n2u9",
                order2,
                "Cash on Delivery",
                Map.of("ESHOP1234ABC5678", "SUCCESS")
        );
        payments.add(payment2);
    }

    @Test
    void testSaveAndFindPayment(){
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(result.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
    }

    @Test
    void testSaveUpdate(){
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        Payment newPayment = new Payment(payment.getId(), payment.getOrder(), "Cash on Delivery", payment.getPaymentData());
        Payment result = paymentRepository.save(newPayment);
        Payment findResult = paymentRepository.findById(result.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals("Cash on Delivery", findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
    }

    @Test
    void testFindByIdIfIdFound(){
        for(Payment payment: payments){
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getOrder(), findResult.getOrder());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound(){
        for(Payment payment: payments){
            paymentRepository.save(payment);
        }
        Payment findResult = paymentRepository.findById("Posyandu Jeruk");
        assertNull(findResult);
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Payment> paymentIterator = paymentRepository.findAll();
        assertFalse(paymentIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOnePayment(){
        for(Payment payment: payments){
            paymentRepository.save(payment);
        }
        Iterator<Payment> paymentIterator = paymentRepository.findAll();
        assertTrue(paymentIterator.hasNext());
        Payment firstPayment = paymentIterator.next();
        assertEquals(firstPayment.getId(), payments.get(0).getId());
        Payment secondPayment = paymentIterator.next();
        assertEquals(secondPayment.getId(), payments.get(1).getId());
    }
}
