package com.appb.app.appb.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;

import com.appb.app.appb.BaseApp;
import com.appb.app.appb.data.DvachError;

import java.io.File;
import java.io.FileOutputStream;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Logvinov.sv on 15.11.2017.
 */

public class Utils {

    public static String getPicturesDirectory(String name) {
        if (isExternalStorageWritable() && isExternalStorageReadable()) {
            File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!picsDir.exists()) {
                picsDir.mkdirs();
            }
            File dvachDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + name);
            if (!dvachDir.exists()) {
                dvachDir.mkdirs();
            }
            return dvachDir.getAbsolutePath();
        } else {
            return BaseApp.getInstance().getFilesDir().getAbsolutePath();
        }
    }


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public static void savePhotoToStorage(byte[] data, File file) {
        if (data == null) return;
        FileOutputStream fileOutputStream = null;
        try {
            //saving image to get exif info
            fileOutputStream = new FileOutputStream(file);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getErrorMessage(DvachError dvachError) {
        return dvachError.getCode() + " " + dvachError.getError();
    }


}
