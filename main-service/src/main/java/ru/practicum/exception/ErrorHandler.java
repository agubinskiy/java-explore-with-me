package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e) {
        log.warn("Ошибка, объект не найден. {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка, объект не найден",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(ForbiddenException e) {
        log.warn("Ошибка, недостаточно прав. {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка, недостаточно прав",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicate(final ConflictException e) {
        log.warn("Ошибка, дубликат. {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка, дубликат",
                e.getMessage()
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class, ValidationException.class,
            MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final Exception e) {
        log.debug("Ошибка валидации. {}", e.getMessage());
        if (e.getClass() == ValidationException.class) {
            return new ErrorResponse(
                    "Некорректное значение параметра " + ((ValidationException) e).getParameter(),
                    ((ValidationException) e).getReason()
            );
        } else if (e.getClass() == HttpMessageNotReadableException.class) {
            return new ErrorResponse("Некорректный запрос", "Тело запроса отсутствует");
        } else if (e.getClass() == MissingServletRequestParameterException.class) {
            return new ErrorResponse("Некорректный запрос", "Отсутствует обязательный параметр");
        } else {
            return new ErrorResponse(
                    "Некорректное значение параметра " + ((MethodArgumentNotValidException) e).getParameter(),
                    e.getMessage()
            );
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final Throwable e) {
        return new ErrorResponse(
                "Ошибка",
                e.getMessage()
        );
    }
}