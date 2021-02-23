package com.clean.architecture.tuto.cli.enums;

public enum MainMenuEnum {
    DISPLAY_ALL_PERSONS("1"),
    DISPLAY_DETAILS_PERSON("2"),
    DISPLAY_ALL_TEAMS("3"),
    DISPLAY_DETAILS_TEAM("4"),
    CREATE_PERSON("5"),
    CREATE_TEAM("6"),
    QUIT("7");

    private String value;

    MainMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MainMenuEnum getFromString(String code) {
        for(MainMenuEnum e : MainMenuEnum.values()) {
            if(e.getValue().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}
