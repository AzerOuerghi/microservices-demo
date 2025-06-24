package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.dto.OrderMapper;
import com.example.order.feign.ProductClient;
import com.example.order.feign.ProductDTO;
import com.example.order.kafka.OrderProducer;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository repo, OrderMapper mapper, ProductClient productClient, OrderProducer orderProducer) {
        this.repo = repo;
        this.mapper = mapper;
        this.productClient = productClient;
        this.orderProducer = orderProducer;
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
        boolean isNew = (dto.getId() == null);
        OrderDTO savedOrder = mapper.toDto(repo.save(mapper.toEntity(dto)));
        if (isNew) {
            try {
                String orderJson = objectMapper.writeValueAsString(savedOrder);
                orderProducer.sendOrderEvent(orderJson);
                logger.info("Order event sent to Kafka for new order: {}", savedOrder.getId());
            } catch (Exception e) {
                logger.error("Failed to send order event to Kafka", e);
            }
        }
        return savedOrder;
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public ProductDTO getProductInfo(String productId) {
        return productClient.getProductById(productId);
    }
}