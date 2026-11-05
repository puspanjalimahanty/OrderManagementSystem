package com.example.order_management;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;



@Service
public class OrderService {
	
	@Value("${order.default-status}")
	private String defaultStatus;

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }
    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        logger.info("Creating order for customerId: {}, productId: {}",
                dto.getCustomerId(), dto.getProductId());

        Customer customer = customerRepository
                .findById(dto.getCustomerId())
                .orElseThrow(() -> {
                    logger.warn("Customer not found: {}", dto.getCustomerId());
                    return new ResourceNotFoundException("Customer not found");
                });

        Product product = productRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> {
                    logger.warn("Product not found: {}", dto.getProductId());
                    return new ResourceNotFoundException("Product not found");
                });

        Order order = new Order();

        order.setCustomerId(customer.getId());
        order.setProductId(product.getId());
        order.setQuantity(dto.getQuantity());

        double totalAmount = product.getPrice() * dto.getQuantity();

        order.setTotalAmount(totalAmount);
        order.setStatus(defaultStatus);
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        logger.info("Order created successfully with id: {}",
                savedOrder.getId());

        return convertToResponse(savedOrder);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private OrderResponseDTO convertToResponse(Order order) {

        OrderResponseDTO response = new OrderResponseDTO();

        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setProductId(order.getProductId());
        response.setQuantity(order.getQuantity());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setOrderDate(order.getOrderDate());

        return response;
    }
}