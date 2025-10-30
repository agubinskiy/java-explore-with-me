package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.comment.CommentByEventDto;
import ru.practicum.dto.comment.CommentByUserDto;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.mappers.CommentMapper;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentByUserDto> getAllCommentsByUser(Long userId, Integer from, Integer size) {
        checkUser(userId);
        List<Comment> result = commentRepository.findAllByAuthorId(userId);
        return result.stream().map(CommentMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto request) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        Comment comment = new Comment(
                null,
                request.getText(),
                user,
                event,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        comment = commentRepository.save(comment);
        log.debug("Добавлен новый коммент {}", comment);
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto findCommentById(Long userId, Long commentId) {
        checkUser(userId);
        Comment comment = checkComment(commentId);
        return CommentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long userId, Long commentId, NewCommentDto request) {
        User user = checkUser(userId);
        Comment comment = checkComment(commentId);
        if (!user.getId().equals(comment.getAuthor().getId())) {
            throw new ForbiddenException("Обновление комментария доступно только его владельцу");
        }

        comment.setText(request.getText());
        comment.setLastUpdated(LocalDateTime.now());
        commentRepository.save(comment);

        return CommentMapper.mapToDto(comment);
    }

    @Override
    public List<CommentByEventDto> getAllCommentsByEvent(Long eventId, Integer from, Integer size) {
        checkUser(eventId);
        List<Comment> result = commentRepository.findAllByEventId(eventId);
        return result.stream().map(CommentMapper::mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        checkComment(commentId);
        commentRepository.deleteById(commentId);
        log.debug("Комментарий {} удален", commentId);
    }

    private Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("События с id = " + eventId + " не существует"));
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователя с id = " + userId + " не существует"));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Комментария с id = " + commentId + " не существует"));
    }
}
