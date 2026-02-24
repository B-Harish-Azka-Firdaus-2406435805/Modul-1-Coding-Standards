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
    void setUp() {
        // Repository akan di-inject otomatis oleh Mockito
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
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
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
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

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateWithNullIdGeneratesUUID() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Sampo Cap Bango");
        product.setProductQuantity(10);

        Product created = productRepository.create(product);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }

    @Test
    void testCreateWithEmptyIdGeneratesUUID() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Sampo Cap Bango");
        product.setProductQuantity(10);

        Product created = productRepository.create(product);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }

    @Test
    void testFindByIdFound() {
        Product product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product found = productRepository.findById("eb5589ef-1c39-460e-8860-71af6af63bd6");

        assertNotNull(found);
        assertEquals("eb5589ef-1c39-460e-8860-71af6af63bd6", found.getProductId());
        assertEquals("Sampo Cap Bambang", found.getProductName());
    }

    @Test
    void testFindByIdNotFoundEmptyRepo() {
        Product found = productRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testFindByIdNotFoundNonEmptyRepo() {
        Product product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product found = productRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        updated.setProductName("Sampo Cap Usep");
        updated.setProductQuantity(200);

        Product result = productRepository.edit(updated);

        assertNotNull(result);
        assertEquals("Sampo Cap Usep", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        Product fromRepo = productRepository.findById("eb5589ef-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Usep", fromRepo.getProductName());
    }

    @Test
    void testEditProductNotFoundEmptyRepo() {
        Product updated = new Product();
        updated.setProductId("non-existent-id");
        updated.setProductName("Ghost Product");
        updated.setProductQuantity(1);

        Product result = productRepository.edit(updated);

        assertNull(result);
    }

    @Test
    void testEditProductNotFoundNonEmptyRepo() {
        Product existing = new Product();
        existing.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        existing.setProductName("Sampo Cap Bambang");
        existing.setProductQuantity(100);
        productRepository.create(existing);

        Product updated = new Product();
        updated.setProductId("non-existent-id");
        updated.setProductName("Ghost Product");
        updated.setProductQuantity(1);

        Product result = productRepository.edit(updated);

        assertNull(result);
    }

    @Test
    void testDeleteExistingProduct() {
        Product product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete("eb5589ef-1c39-460e-8860-71af6af63bd6");

        Product found = productRepository.findById("eb5589ef-1c39-460e-8860-71af6af63bd6");
        assertNull(found);
    }

    @Test
    void testDeleteNonExistingProduct() {
        // Should not throw any exception
        assertDoesNotThrow(() -> productRepository.delete("non-existent-id"));
    }
}
