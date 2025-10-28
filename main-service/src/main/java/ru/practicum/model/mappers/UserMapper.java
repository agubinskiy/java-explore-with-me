package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.User;

@UtilityClass
public class UserMapper {
    public User mapFromDto(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail()
        );
    }

    public UserDto mapToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User mapFromNewRequest(NewUserRequest request) {
        return new User(
                null,
                request.getName(),
                request.getEmail()
        );
    }

    public UserShortDto mapToShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }
}
