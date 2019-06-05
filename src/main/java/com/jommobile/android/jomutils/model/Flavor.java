package com.jommobile.android.jomutils.model;

public enum Flavor {
    DEBUG("debug"),
    RELEASE("release"),
    PREMIUM("premium");

    private final String oldName;

    Flavor(String oldName) {
        this.oldName = oldName;
    }

    @Override
    public String toString() {
        return oldName;
    }

    public static Flavor of(String name) {
        if (name != null && !name.isEmpty())
            switch (name) {
                case "debug":
                    return DEBUG;
                case "release":
                    return RELEASE;
                case "premium":
                    return PREMIUM;
                default:
                    throw new IllegalArgumentException("flavor name " + name + " is not valid.");
            }
        else
            throw new IllegalArgumentException("flavor name must not null or empty.");
    }
}
