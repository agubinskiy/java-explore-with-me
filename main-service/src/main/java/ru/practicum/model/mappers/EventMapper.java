package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.model.Event;

@UtilityClass
public class EventMapper {
    public Event mapFromNewRequest(NewEventDto dto) {
        return new Event(
                null,
                dto.getAnnotation(),
                null,
                null,
                dto.getDescription(),
                dto.getEventDate(),
                null,
                LocationMapper.mapFromDto(dto.getLocation()),
                dto.isPaid(),
                dto.getParticipantLimit(),
                null,
                dto.isRequestModeration(),
                null,
                dto.getTitle()
        );
    }

    public EventFullDto mapToFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.mapToDto(event.getCategory()),
                null,
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                UserMapper.mapToShortDto(event.getInitiator()),
                LocationMapper.mapToDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                null
        );
    }

    public EventShortDto mapToShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.mapToDto(event.getCategory()),
                null,
                event.getEventDate(),
                UserMapper.mapToShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                null
        );
    }
}
