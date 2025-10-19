package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.model.Hit;

public class HitMapper {
    public static HitDto mapToDto(Hit hit) {
        return new HitDto(
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }

    public static Hit mapFromDto(HitDto dto) {
        return new Hit(
                null,
                dto.getApp(),
                dto.getUri(),
                dto.getIp(),
                dto.getTimestamp()
        );
    }
}
