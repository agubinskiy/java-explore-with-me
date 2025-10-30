package ru.practicum.model.enums;

import java.util.Optional;

public enum UserEventStateAction {
    SEND_TO_REVIEW,
    CANCEL_REVIEW;

    public static Optional<UserEventStateAction> from(String stringState) {
        for (UserEventStateAction state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
