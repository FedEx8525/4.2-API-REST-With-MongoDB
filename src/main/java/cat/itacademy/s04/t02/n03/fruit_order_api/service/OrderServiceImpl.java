package cat.itacademy.s04.t02.n03.fruit_order_api.service;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderUpdateDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.Order;
import cat.itacademy.s04.t02.n03.fruit_order_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static cat.itacademy.s04.t02.n03.fruit_order_api.mapper.OrderMapper.mapToDTO;
import static cat.itacademy.s04.t02.n03.fruit_order_api.mapper.OrderMapper.mapToEntity;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = mapToEntity(orderRequestDTO);
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> listOrders() {
        return List.of();
    }

    @Override
    public OrderResponseDTO getOrderById(String id) {
        return null;
    }

    @Override
    public OrderResponseDTO updateOrder(String id, OrderUpdateDTO orderUpdateDTO) {
        return null;
    }

    @Override
    public void deleteOrder(String id) {

    }
}
