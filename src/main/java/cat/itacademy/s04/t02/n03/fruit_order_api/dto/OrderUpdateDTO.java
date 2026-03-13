package cat.itacademy.s04.t02.n03.fruit_order_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;
import java.util.List;

public record OrderUpdateDTO(
        String clientName,
        @Future(message = "The delivery date must be at least tomorrow" ) LocalDate deliveryDate,
        @Valid List<OrderItemDTO> items) {
}
