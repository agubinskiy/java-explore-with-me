package ru.practicum.ewm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.StatsDto;
import ru.practicum.ewm.mapper.HitMapper;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                   List<String> uris, Boolean unique) {
        List<Object[]> results;

        if (Boolean.TRUE.equals(unique)) {
            results = statsRepository.findUniqueStats(start, end, uris);
        } else {
            results = statsRepository.findStats(start, end, uris);
        }

        // Преобразование Object[] в StatsDto
        return results.stream()
                .map(result -> new StatsDto(
                        (String) result[0],        // app
                        (String) result[1],        // uri
                        ((Number) result[2]).longValue()  // hits
                ))
                .collect(Collectors.toList());
    }

    @Override
    public HitDto saveHit(HitDto dto) {
        Hit hit = HitMapper.mapFromDto(dto);
        Hit saved = statsRepository.save(hit);
        return HitMapper.mapToDto(saved);
    }
}
