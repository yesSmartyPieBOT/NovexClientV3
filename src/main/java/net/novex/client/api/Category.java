package net.novex.client.api;

public enum Category {
    HUD("HUD Elements"),
    VISUAL("Visual & Graphics"),
    PERFORMANCE("Performance"),
    GAMEPLAY("Gameplay & Utility");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
