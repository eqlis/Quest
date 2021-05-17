package com.example.qu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Quest {
    long id;
    String title;
    String location;
    Date created;

    public Quest(String title) {
        this(-1, title, "None", new Date(java.lang.System.currentTimeMillis()));
    }

    public Quest(long id, String title, String location, Date created) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.created = created;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        String dateString = sdf.format(created);
        return title + " " + location + " " + dateString;
    }
}
