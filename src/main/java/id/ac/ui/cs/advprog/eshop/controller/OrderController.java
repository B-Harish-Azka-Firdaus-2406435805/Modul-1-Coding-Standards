package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "Order/createOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "Order/orderHistory";
    }

    @PostMapping("/history")
    public String orderHistoryPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "Order/orderHistoryList";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "Order/payOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId,
                               @RequestParam String method,
                               @RequestParam(required = false) String voucherCode,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String deliveryFee,
                               Model model) {
        Order order = orderService.findById(orderId);
        Map<String, String> paymentData = new HashMap<>();
        if ("VOUCHER_CODE".equals(method)) {
            paymentData.put("voucherCode", voucherCode);
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            paymentData.put("address", address);
            paymentData.put("deliveryFee", deliveryFee);
        }
        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("payment", payment);
        return "Order/paymentSuccess";
    }
}
