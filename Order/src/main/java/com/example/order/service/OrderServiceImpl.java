package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.dto.OrderMapper;
import com.example.order.feign.ProductClient;
import com.example.order.feign.ProductDTO;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final ProductClient productClient;

    public OrderServiceImpl(OrderRepository repo, OrderMapper mapper, ProductClient productClient) {
        this.repo = repo;
        this.mapper = mapper;
        this.productClient = productClient;
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

    public ProductDTO getProductInfo(String productId) {
        return productClient.getProductById(productId);
    }
}