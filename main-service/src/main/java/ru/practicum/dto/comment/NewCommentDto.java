package ru.practicum.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentDto {
    @NotBlank
    @Size(max = 1024)
    private String text;
}
