package com.example.curtis.memorymaker.business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ian on 2/17/2017.
 */

public class Helper {

    public static byte[] getImageAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmap(byte[] rawData)
    {
        //untested
        return BitmapFactory.decodeByteArray(rawData, 0, rawData.length);
    }
}
