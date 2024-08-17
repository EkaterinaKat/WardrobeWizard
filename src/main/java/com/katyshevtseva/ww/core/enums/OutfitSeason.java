package com.katyshevtseva.ww.core.enums;

public enum OutfitSeason {
    W("Зима"), S("Лето"), DS("Демисезон");

    private final String title;

    OutfitSeason(String title) {
        this.title = title;
    }

    public static OutfitSeason getByTitleOnNull(String title) {
        for (OutfitSeason season : OutfitSeason.values()) {
            if (season.title.equals(title))
                return season;
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
