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

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        this.products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentWithNullOrder(){
        this.order = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment( "10287-a9ke90-001k-b5y6-542k203k5j",this.order, "Cash on Delivery", Map.of("ESHOP1234ABC5678", "SUCCESS"));
        });
    }

    @Test
    void testCreatePaymentWithExistsOrder() {
        Payment payment = new Payment("10287-a9ke90-001k-b5y6-542k203k5j",this.order, "Cash on Delivery", Map.of("ESHOP1234ABC5678", "SUCCESS"));
        assertEquals(this.order, payment.getOrder());
        assertEquals("Cash on Delivery", payment.getMethod());
        assertEquals(Map.of("ESHOP1234ABC5678", "SUCCESS"), payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithDefaultStatus() {
        Payment payment = new Payment("10287-a9ke90-001k-b5y6-542k203k5j",this.order, "Cash on Delivery", Map.of("ESHOP1234ABC5678", "SUCCESS"));
        assertEquals("PENDING", payment.getStatus());
    }


}
