package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String orderHomePage(){
        return "OrderHomePage";
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("order", new Order(null, List.of(), System.currentTimeMillis(), "")); // Empty order for form binding
        return "CreateOrder";
    }

    @RequestMapping("/create")
    public String createOrder(@ModelAttribute Order order){
        orderService.createOrder(order);
        return "redirect:order";
    }
}
