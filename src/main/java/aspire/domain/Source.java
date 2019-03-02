package aspire.domain;

import java.util.Arrays;

public enum Source {

    LOCAL,
    REMOTE,
    UNKNOWN;

    public static Source fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Arrays
                .stream(values())
                .filter((Source it) -> value.equalsIgnoreCase(it.toString()))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
