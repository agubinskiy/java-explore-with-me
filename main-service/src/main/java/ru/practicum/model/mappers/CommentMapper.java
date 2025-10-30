package ru.practicum.model.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.comment.CommentByEventDto;
import ru.practicum.dto.comment.CommentByUserDto;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.Comment;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getCreated(),
                comment.getLastUpdated()
        );
    }

    public CommentByUserDto mapToUserDto(Comment comment) {
        return new CommentByUserDto(
                comment.getId(),
                comment.getText(),
                comment.getEvent().getId(),
                comment.getCreated(),
                comment.getLastUpdated()
        );
    }

    public CommentByEventDto mapToEventDto(Comment comment) {
        return new CommentByEventDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getCreated(),
                comment.getLastUpdated()
        );
    }

    public Comment mapFromNewRequest(NewCommentDto dto) {
        return new Comment(
                null,
                dto.getText(),
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
