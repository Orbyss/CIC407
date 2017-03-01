package com.example.curtis.memorymaker.models;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.curtis.memorymaker.MemDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by Ian on 2/17/2017.
 */

@Table(database = MemDatabase.class)
public class Memory extends BaseModel implements Serializable {
    @Column
    @PrimaryKey
    private String id;
    @Column
    private String description;
    @Column
    private transient Blob image;
    @Column
    private String location;

    public Memory(String id, Blob image, String description, String location) {
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

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
