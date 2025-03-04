package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;
    List<Product> products;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
         products = List.of(new Product());

        order = new Order("O123", products, 1708570000L, "user123");
    }

    @Test
    void testAddPaymentWithValidVoucherCode(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithInValidVoucherCode(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID123457");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", payment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithValidCod(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("Jalan Jilin 3 No. 122", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "COD", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithInvalidCOD_InvalidAddress(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "COD", paymentData);
        assertNotNull(payment);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", payment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithInvalidCOD_NullAddress(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(null, "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment resultPayment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", resultPayment.getStatus());
        assertEquals("FAILED", resultPayment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithInvalidCOD_InvalidFee(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("Jalan Jilin 3 No. 122", "");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment resultPayment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", resultPayment.getStatus());
        assertEquals("FAILED", resultPayment.getOrder().getStatus());
    }

    @Test
    void testAddPaymentWithInvalidCOD_NullFee(){
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("Jalan Jilin 3 No. 122", null);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment resultPayment = paymentService.addPayment(order, "COD", paymentData);

        assertEquals("REJECTED", resultPayment.getStatus());
        assertEquals("FAILED", resultPayment.getOrder().getStatus());
    }

    @Test
    void testSetStatus_Success() {
        Payment payment = new Payment("7hdk5sf-58fg-913h-abed-cajoled691n2u9", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        payment = paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatus_Rejected() {
        Payment payment = new Payment("7hdk5sf-58fg-913h-abed-cajoled691n2u9", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        payment = paymentService.setStatus(payment, "REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetInvalidStatus(){
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("7hdk5sf-58fg-913h-abed-cajoled691n2u9", order, "VOUCHER", new HashMap<>());
            payment = paymentService.setStatus(payment, "MEOW");
        });
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("p1", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.findById("p1")).thenReturn(payment);

        Payment retrievedPayment = paymentService.getPayment("p1");
        assertEquals(payment, retrievedPayment);
    }

    @Test
    void testGetAllPayment(){
        List<Payment> payments = new ArrayList<>();

        Payment payment1 = new Payment("7hdk5sf-58fg-913h-abed-cajoled691n2u9", order, "VOUCHER", new HashMap<>());
        Payment payment2 = new Payment("10287-a9ke90-001k-b5y6-542k203k5j", order, "COD", new HashMap<>());
        payments.add(payment1);
        payments.add(payment2);

        when(paymentRepository.save(payment1)).thenReturn(payment1);
        when(paymentRepository.save(payment2)).thenReturn(payment2);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        when(paymentRepository.findAll()).thenReturn(payments.iterator());

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        assertEquals("7hdk5sf-58fg-913h-abed-cajoled691n2u9", result.get(0).getId());
        assertEquals("10287-a9ke90-001k-b5y6-542k203k5j", result.get(1).getId());

        verify(paymentRepository, times(1)).save(payment1);
        verify(paymentRepository, times(1)).save(payment2);
        verify(paymentRepository, times(1)).findAll();
    }

}
