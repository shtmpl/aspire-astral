package aspire.domain;

import java.util.Optional;

public enum Employment {

    FULL_TIME,
    PART_TIME,
    OTHER;

    public static Employment fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        try {
            return valueOf(name);
        } catch (IllegalArgumentException exception) {
            return OTHER;
        }
    }

}
