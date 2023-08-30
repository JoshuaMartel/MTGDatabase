package org.pheonix;

public enum ConfigStore {
    HOST ("host"),
    DATABASE("database"),
    USER("user"),
    PASSWORD("password"),
    TIME_ZONE("time_zone"),
    USE_SSL("use_ssl"),
    ALLOW_PUBLIC_KEY_RETRIEVAL("allow_public_key_retrieval"),
    IMAGE_FOLDER_URL("image_folder_url"),
    IMAGE_WIDTH("image_width"),
    IMAGE_HEIGHT("image_height");

    private final String value;
    ConfigStore(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
