package com.example.order.controller;

import com.example.order.dto.OrderDTO;
import com.example.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}

