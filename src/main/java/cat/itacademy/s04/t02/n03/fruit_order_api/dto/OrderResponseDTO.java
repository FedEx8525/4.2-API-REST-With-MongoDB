package cat.itacademy.s04.t02.n03.fruit_order_api.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDTO(
        String id,
        String clientName,
        LocalDate deliveryDate,
        List<OrderItemDTO> items) {
}
