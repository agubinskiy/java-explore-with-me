package ru.practicum.controller.priv;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.comment.CommentByUserDto;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.service.CommentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/comments")
public class CommentPrivateController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentByUserDto> getAllCommentsByUser(@PathVariable Long userId,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрашивается список всех комментариев пользователя id={}, с параметрами from={}, size={}",
                userId, from, size);
        return commentService.getAllCommentsByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long userId,
                                 @RequestParam Long eventId,
                                 @RequestBody @Valid NewCommentDto request) {
        log.info("Добавляется новый комментарий {} пользователем id={} к событию id={}", request, userId, eventId);
        return commentService.addComment(userId, eventId, request);
    }

    @GetMapping("/{commentId}")
    public CommentDto findCommentById(@PathVariable Long userId,
                                      @PathVariable Long commentId) {
        log.info("Запрашивается информация о комментарии id={} пользователем id={}", commentId, userId);
        return commentService.findCommentById(userId, commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateEvent(@PathVariable Long userId,
                                  @PathVariable Long commentId,
                                  @RequestBody @Valid NewCommentDto request) {
        log.info("Обновляется комментарий id={} пользователем id={}. Новые данные: {}", commentId, userId, request);
        return commentService.updateComment(userId, commentId, request);
    }
}
