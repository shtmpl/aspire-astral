package aspire.domain;

import java.util.Arrays;

public enum Origin {

    LOCAL,
    REMOTE,
    UNKNOWN;

    public static Origin fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Arrays
                .stream(values())
                .filter((Origin it) -> value.equalsIgnoreCase(it.toString()))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
