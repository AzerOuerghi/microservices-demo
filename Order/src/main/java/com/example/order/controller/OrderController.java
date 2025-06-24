package com.example.order.controller;

import com.example.order.dto.OrderDTO;
import com.example.order.feign.ProductClient;
import com.example.order.feign.ProductDTO;
import com.example.order.service.IOrderService;
import com.example.order.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ProductClient productClient;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public OrderController(IOrderService orderService, ProductClient productClient, OrderServiceImpl orderServiceImpl) {
        this.orderService = orderService;
        this.productClient = productClient;
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return orderService.save(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderDTO.setId(id);
        return orderService.save(orderDTO);
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProductInfo(@PathVariable String productId) {
        return orderServiceImpl.getProductInfo(productId);
    }
}

