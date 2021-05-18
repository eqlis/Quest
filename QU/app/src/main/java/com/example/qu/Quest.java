package com.example.qu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Quest {
    public String title;
    String location;
    Date created;
    Task[] tasks;

    public Quest(String title) {
        this(title, "None", new Date(java.lang.System.currentTimeMillis()), new Task[] {});
    }

    public Quest(String title, String location, Task[] tasks) {
        this(title, location, new Date(java.lang.System.currentTimeMillis()), tasks);
    }

    public Quest(String title, String location, Date created, Task[] tasks) {
        this.title = title;
        this.location = location;
        this.created = created;
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        String dateString = sdf.format(created);
        return title + ", " + location;
    }
}
