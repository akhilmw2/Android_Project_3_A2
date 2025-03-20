package com.example.android_project_3_a2;

public class Item {
    private final String name;
    private final String url;

    public Item(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
}
