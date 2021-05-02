package com.abhishek.notesapplication;

public class ItemNotes {
    String title, data;

    public ItemNotes() {
    }

    public ItemNotes(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }
}
