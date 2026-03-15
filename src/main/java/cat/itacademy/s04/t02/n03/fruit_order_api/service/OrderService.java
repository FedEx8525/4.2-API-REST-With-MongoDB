package cat.itacademy.s04.t02.n03.fruit_order_api.service;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> listOrders();
    OrderResponseDTO getOrderById(String id);
    OrderResponseDTO updateOrder(String id);
    void deleteOrder(String id);
}
