package com.eisenguide2.model;

public enum TaskCategory {
    IMPORTANTE_E_URGENTE("Importante e urgente"),
    IMPORTANTE_MAS_NAO_URGENTE("Importante, mas não urgente"),
    URGENTE_MAS_NAO_IMPORTANTE("Urgente, mas não importante"),
    NAO_URGENTE_NAO_IMPORTANTE("Não urgente e não importante");

    private final String displayName;

    TaskCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}