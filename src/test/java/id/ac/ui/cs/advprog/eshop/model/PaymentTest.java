package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class PaymentTest {
    private List<Product> products;
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products, 1708560000L, "Safira Sudrajat");

        this.paymentData = new HashMap<>();
        this.paymentData.put("transactionId", "ESHOP1234ABC5678");
        this.paymentData.put("status", "SUCCESS");
    }

    @Test
    void testCreatePaymentWithDefaultStatus() {
        Payment payment = new Payment(this.order, "Cash on Delivery", this.paymentData);

        assertEquals(this.order, payment.getOrder());
        assertEquals("Cash on Delivery", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("WAITING_PAYMENT", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentSuccessStatusAndOrderSuccess(){
        Payment payment = new Payment(this.order, "Cash on Delivery", this.paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentRejectedStatusAndOrderFailed(){
        Payment payment = new Payment(this.order, "Cash on Delivery", this.paymentData);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusInvalid(){
        Payment payment = new Payment(this.order, "Cash on Delivery", this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }


}
