package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.dto.OrderMapper;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository repo;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repo, OrderMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDTO> getAll() {
        return mapper.toDtoList(repo.findAll());
    }
    @Override
    public OrderDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDto).orElse(null);
    }
    @Override
    public OrderDTO save(OrderDTO dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}