package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.Location;

@UtilityClass
public class LocationMapper {
    public Location mapFromDto(LocationDto dto) {
        return new Location(
                null,
                dto.getLat(),
                dto.getLon()
        );
    }

    public LocationDto mapToDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }
}
