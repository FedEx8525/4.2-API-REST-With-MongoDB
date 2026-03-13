package cat.itacademy.s04.t02.n03.fruit_order_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record OrderRequestDTO(
        @NotBlank(message = "The client name cannot be empty") String clientName,
        @NotNull(message = "The delivery date cannot be null")
        @Future(message = "The delivery date must be at least tomorrow") LocalDate deliveryDate,
        @NotEmpty(message = "The order must contain at least one item")
        @Valid List<OrderItemDTO> items) {
}
