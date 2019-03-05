package aspire.astral.domain;

import java.util.Arrays;

public enum Employment {

    FULL_TIME,
    PART_TIME,
    OTHER;

    public static Employment fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(values())
                .filter((Employment it) -> name.equalsIgnoreCase(it.toString()))
                .findFirst()
                .orElse(OTHER);
    }
}
