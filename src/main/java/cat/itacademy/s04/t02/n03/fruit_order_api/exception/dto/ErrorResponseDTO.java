package cat.itacademy.s04.t02.n03.fruit_order_api.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message) {

    public static ErrorResponseDTO of(int status, String error, String message) {
        return new ErrorResponseDTO(LocalDateTime.now(), status, error, message);
    }

}
