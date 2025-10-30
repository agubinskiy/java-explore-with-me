package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.model.Compilation;

import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public CompilationDto mapToDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getEvents().stream()
                        .map(EventMapper::mapToShortDto)
                        .collect(Collectors.toSet()),
                compilation.getTitle(),
                compilation.getPinned()
        );
    }

    public Compilation mapFromNewRequest(NewCompilationDto dto) {
        return new Compilation(
                null,
                null,
                dto.getPinned(),
                dto.getTitle()
        );
    }
}
