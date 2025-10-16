package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final Exception e) {
        log.debug("Ошибка валидации. {}", e.getMessage());
        if (e.getClass() == HttpMessageNotReadableException.class) {
            return new ErrorResponse("Некорректный запрос", "Тело запроса отсутствует");
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
