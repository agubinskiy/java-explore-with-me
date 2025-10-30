package ru.practicum.service;

import ru.practicum.dto.comment.CommentByEventDto;
import ru.practicum.dto.comment.CommentByUserDto;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentByUserDto> getAllCommentsByUser(Long userId, Integer from, Integer size);

    CommentDto addComment(Long userId, Long eventId, NewCommentDto request);

    CommentDto findCommentById(Long userId, Long commentId);

    CommentDto updateComment(Long userId, Long commentId, NewCommentDto request);

    List<CommentByEventDto> getAllCommentsByEvent(Long eventId, Integer from, Integer size);

    void deleteComment(Long commentId);
}
