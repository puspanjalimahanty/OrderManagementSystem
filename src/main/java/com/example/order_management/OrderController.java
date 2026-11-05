package com.example.order_management;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponseDTO createOrder(
            @Valid @RequestBody OrderRequestDTO dto) {

        return service.createOrder(dto);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }
}