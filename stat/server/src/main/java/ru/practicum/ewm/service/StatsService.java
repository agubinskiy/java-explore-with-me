package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    HitDto saveHit(HitDto hit);
}
