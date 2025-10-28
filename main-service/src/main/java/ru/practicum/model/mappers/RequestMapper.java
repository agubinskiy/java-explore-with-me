package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.model.Request;

@UtilityClass
public class RequestMapper {
    public Request mapFromDto(ParticipationRequestDto dto) {
        return new Request(
                dto.getId(),
                dto.getCreated(),
                null,
                null,
                dto.getStatus()
        );
    }

    public ParticipationRequestDto mapToDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
