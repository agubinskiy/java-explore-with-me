package ru.practicum.model.enums;

import java.util.Optional;

public enum AdminEventStateAction {
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static Optional<AdminEventStateAction> from(String stringState) {
        for (AdminEventStateAction state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
