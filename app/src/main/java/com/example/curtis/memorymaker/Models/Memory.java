package com.example.curtis.memorymaker.Models;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;

/**
 * Created by Ian on 2/17/2017.
 */

public class Memory implements Serializable {

    private String id;
    private String description;
    private transient Bitmap image;
    private Location location;

    public Memory(String id, Bitmap image, String description, Location location) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.location = location;
    }

    public Memory() {}

    public Memory(Memory memory) {
        this(memory.getId(), memory.getImage(), memory.getDescription(), memory.getLocation());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
