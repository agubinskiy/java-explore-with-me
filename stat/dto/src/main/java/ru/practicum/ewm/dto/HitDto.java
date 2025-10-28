package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HitDto {
    private Long id;

    @NotBlank(message = "Идентификатор сервиса не может быть пустым")
    @Size(max = 255)
    private String app;

    @NotBlank(message = "URI не может быть пустым")
    @Size(max = 64)
    private String uri;

    @NotBlank(message = "IP не может быть пустым")
    @Size(max = 16)
    private String ip;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
