package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/detail")
    public String paymentDetailPage() {
        return "Payment/paymentDetail";
    }

    @GetMapping("/detail/{paymentId}")
    public String paymentDetailById(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "Payment/paymentDetailPage";
    }

    @GetMapping("/admin/list")
    public String paymentAdminList(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "Payment/paymentList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String paymentAdminDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "Payment/paymentAdminDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String setPaymentStatus(@PathVariable String paymentId,
                                   @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}
