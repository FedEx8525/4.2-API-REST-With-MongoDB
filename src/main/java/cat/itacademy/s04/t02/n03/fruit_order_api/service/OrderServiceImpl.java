package cat.itacademy.s04.t02.n03.fruit_order_api.service;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderUpdateDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.exception.OrderNotFoundException;
import cat.itacademy.s04.t02.n03.fruit_order_api.mapper.OrderMapper;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.Order;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.OrderItem;
import cat.itacademy.s04.t02.n03.fruit_order_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static cat.itacademy.s04.t02.n03.fruit_order_api.mapper.OrderMapper.mapToDTO;
import static cat.itacademy.s04.t02.n03.fruit_order_api.mapper.OrderMapper.mapToEntity;

@Service
public class OrderServiceImpl implements OrderService {

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
        return orderRepository.findAll().stream()
                .map(OrderMapper::mapToDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return mapToDTO(order);
    }

    @Override
    public OrderResponseDTO updateOrder(String id, OrderUpdateDTO orderUpdateDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (orderUpdateDTO.clientName() != null && !orderUpdateDTO.clientName().isBlank()) {
            order.setClientName(orderUpdateDTO.clientName());
        }
        if (orderUpdateDTO.deliveryDate() != null) {
            order.setDeliveryDate(orderUpdateDTO.deliveryDate());
        }
        if (orderUpdateDTO.items() != null && !orderUpdateDTO.items().isEmpty()) {
            List<OrderItem> newItems = orderUpdateDTO.items().stream()
                    .map(item -> new OrderItem(item.fruitName(), item.quantityInKilos()))
                    .toList();

            order.setItems(newItems);
        }
        Order savedOrder = orderRepository.save(order);

        return mapToDTO(savedOrder);
    }

    @Override
    public void deleteOrder(String id) {

    }
}
