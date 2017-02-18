package com.example.curtis.memorymaker;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Ian on 2/17/2017.
 */

@Database(name = MemDatabase.NAME, version = MemDatabase.VERSION)
public class MemDatabase {
    public static final String NAME = "MemDatabase";
    public static final int VERSION = 1;
}