package cat.itacademy.s04.t02.n03.fruit_order_api.mapper;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.Order;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.OrderItem;

import java.util.List;

public class OrderMapper {
    public static OrderResponseDTO mapToDTO(Order order){
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getFruitName(), item.getQuantityInKilos()))
                .toList();
        return new OrderResponseDTO(
                order.getId(),
                order.getClientName(),
                order.getDeliveryDate(),
                itemDTOs);
    }

    public static Order mapToEntity(OrderRequestDTO dto) {
        List<OrderItem> items = dto.items().stream()
                .map(item -> new OrderItem(item.fruitName(), item.quantityInKilos()))
                .toList();
        return new Order(dto.clientName(), dto.deliveryDate(), items);
    }
}

