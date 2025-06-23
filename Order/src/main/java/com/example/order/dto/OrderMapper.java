package com.example.order.dto;

import com.example.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO dto);
    List<OrderDTO> toDtoList(List<Order> orders);
}

