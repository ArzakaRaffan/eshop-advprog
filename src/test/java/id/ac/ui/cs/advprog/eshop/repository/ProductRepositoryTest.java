package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp(){
    }
    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8868-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        productRepository.create(product1);
        Product product2= new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse (productIterator.hasNext());
    }

    @Test
    void testEditProduct(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8868-71af6af63bd6a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        product.setProductName("Sampo Cap Bangor");
        product.setProductQuantity(150);
        productRepository.edit(product);

        Product editedProduct = productRepository.findById("eb558e9f-1c39-460e-8868-71af6af63bd6a");

        assertEquals(product.getProductId(), editedProduct.getProductId());
        assertEquals(product.getProductName(), editedProduct.getProductName());
        assertEquals(product.getProductQuantity(), editedProduct.getProductQuantity());
    }

    // Null assertion if product doesn't exist while being edited
    @Test
    void testEditIfProductNotFound(){
        Product product = new Product();

        Product productNotExist = productRepository.edit(product);
        assertNull(productNotExist);
    }

    // This delete test re-assure if the product is already null
    @Test
    void testDeleteProduct(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertNotNull(productRepository.findById(product.getProductId()));
        productRepository.delete(product.getProductId());
        assertNull(productRepository.findById(product.getProductId()));
    }

    @Test
    void testCreateProductWithZeroQuantity(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Invalid Product");
        product.setProductQuantity(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.create(product);
        });

        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    void testCreateProductWithNullName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName(null);
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.create(product);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateProductWithNoName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("");
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.create(product);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testEditProductWithZeroQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        product.setProductQuantity(0); // Invalid update

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.edit(product);
        });

        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    void testEditProductWithNullName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        product.setProductName(null); // Invalid update

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.edit(product);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testEditProductWithNoName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        product.setProductName(""); // Invalid update

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.edit(product);
        });

        assertEquals("Product name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testfindById(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product findByIdProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6a", findByIdProduct.getProductId());
        assertEquals("Valid Product", findByIdProduct.getProductName());
        assertEquals(10, findByIdProduct.getProductQuantity());
    }

    @Test
    void testFindByIdIfIdNotExists(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6a");
        product.setProductName("Invalid product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product findByIdProduct = productRepository.findById("Invalid ID");
        assertNull(findByIdProduct);
    }

}