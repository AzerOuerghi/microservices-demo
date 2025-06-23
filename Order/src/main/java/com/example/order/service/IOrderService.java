package com.example.order.service;

import com.example.order.dto.OrderDTO;
import java.util.List;

public interface IOrderService {
    List<OrderDTO> getAll();
    OrderDTO getById(Long id);
    OrderDTO save(OrderDTO dto);
    void delete(Long id);
}
