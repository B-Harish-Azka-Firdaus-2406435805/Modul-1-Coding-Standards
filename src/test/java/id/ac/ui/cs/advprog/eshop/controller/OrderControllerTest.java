package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderController orderController;

    private Order order;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        orders = new ArrayList<>();
        orders.add(order);
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/createOrder"));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/orderHistory"));
    }

    @Test
    void testOrderHistoryPost() throws Exception {
        when(orderService.findAllByAuthor("Safira Sudrajat")).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/orderHistoryList"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"));

        verify(orderService, times(1)).findAllByAuthor("Safira Sudrajat");
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/order/pay/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/payOrder"))
                .andExpect(model().attributeExists("order"));

        verify(orderService, times(1)).findById(order.getId());
    }

    @Test
    void testPayOrderPostVoucher() throws Exception {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", paymentData, order);

        when(orderService.findById(order.getId())).thenReturn(order);
        when(paymentService.addPayment(eq(order), eq("VOUCHER_CODE"), anyMap())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/" + order.getId())
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/paymentSuccess"))
                .andExpect(model().attributeExists("payment"));

        verify(paymentService, times(1)).addPayment(eq(order), eq("VOUCHER_CODE"), anyMap());
    }

    @Test
    void testPayOrderPostCashOnDelivery() throws Exception {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 1");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-2", "CASH_ON_DELIVERY", paymentData, order);

        when(orderService.findById(order.getId())).thenReturn(order);
        when(paymentService.addPayment(eq(order), eq("CASH_ON_DELIVERY"), anyMap())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/" + order.getId())
                        .param("method", "CASH_ON_DELIVERY")
                        .param("address", "Jl. Raya No. 1")
                        .param("deliveryFee", "10000"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/paymentSuccess"))
                .andExpect(model().attributeExists("payment"));

        verify(paymentService, times(1)).addPayment(eq(order), eq("CASH_ON_DELIVERY"), anyMap());
    }

    @Test
    void testPayOrderPostUnknownMethod() throws Exception {
        Map<String, String> emptyData = new HashMap<>();
        Payment payment = new Payment("pay-3", "UNKNOWN", emptyData, order);

        when(orderService.findById(order.getId())).thenReturn(order);
        when(paymentService.addPayment(eq(order), eq("UNKNOWN"), anyMap())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/" + order.getId())
                        .param("method", "UNKNOWN"))
                .andExpect(status().isOk())
                .andExpect(view().name("Order/paymentSuccess"))
                .andExpect(model().attributeExists("payment"));

        verify(paymentService, times(1)).addPayment(eq(order), eq("UNKNOWN"), anyMap());
    }
}
