package ru.practicum.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.dto.event.EventShortDto;

import java.util.Set;

@Data
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private Set<EventShortDto> events;
    private String title;
    private Boolean pinned;
}
