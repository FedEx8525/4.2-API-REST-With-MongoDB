package cat.itacademy.s04.t02.n03.fruit_order_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderItemDTO(
        @NotBlank(message = "The fruit name cannot be empty") String fruitName,
        @Positive(message = "The quantity must be greater than zero") int quantityInKilos) {
}
