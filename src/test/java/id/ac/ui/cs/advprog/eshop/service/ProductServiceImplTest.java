package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb5589ef-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        Iterator<Product> mockIterator = Arrays.asList(product, product2).iterator();
        when(productRepository.findAll()).thenReturn(mockIterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(product.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        Iterator<Product> emptyIterator = Arrays.<Product>asList().iterator();
        when(productRepository.findAll()).thenReturn(emptyIterator);

        List<Product> result = productService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById() {
        when(productRepository.findById("eb5589ef-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        Product result = productService.findById("eb5589ef-1c39-460e-8860-71af6af63bd6");

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findById("eb5589ef-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById("non-existent-id")).thenReturn(null);

        Product result = productService.findById("non-existent-id");

        assertNull(result);
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product)).thenReturn(product);

        Product result = productService.edit(product);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testEditNotFound() {
        when(productRepository.edit(product)).thenReturn(null);

        Product result = productService.edit(product);

        assertNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete("eb5589ef-1c39-460e-8860-71af6af63bd6");

        productService.delete("eb5589ef-1c39-460e-8860-71af6af63bd6");

        verify(productRepository, times(1)).delete("eb5589ef-1c39-460e-8860-71af6af63bd6");
    }
}
