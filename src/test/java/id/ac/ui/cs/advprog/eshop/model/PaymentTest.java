package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, order);
        assertEquals("p1", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherRejectedNot16Chars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC");

        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNotStartWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCDE1234ABC5678");

        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNot8Numerics() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 1");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("p2", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("p2", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("p2", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedEmptyFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 1");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment("p2", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedNullFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 1");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment("p2", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNullCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentUnknownMethodRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("someKey", "someValue");

        Payment payment = new Payment("p3", "UNKNOWN_METHOD", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }
}
