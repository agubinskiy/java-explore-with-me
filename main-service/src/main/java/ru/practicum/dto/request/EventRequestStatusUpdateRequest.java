package ru.practicum.dto.request;

import lombok.Data;
import ru.practicum.model.enums.RequestStatus;

import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private RequestStatus status;
}
