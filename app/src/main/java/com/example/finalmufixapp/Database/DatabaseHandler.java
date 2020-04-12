package com.example.finalmufixapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";
    private static final String TABLE_Images = "imageTb";
//    private static final String ImageName = "ImageName";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists imageTb (ImageName varchar(30) , Image blob )");

//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "imageTb" + "("
//                + "ImageName" + " varchar(30)," + "Image" + " blob )";
//        db.execSQL(CREATE_CONTACTS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS imageTb");

        // Create tables again
        onCreate(db);
    }


    public boolean Insert_Image(String ImageName, Bitmap bitmap) {

        SQLiteDatabase db = this.getWritableDatabase();


        boolean flag = false;
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        ContentValues values = new ContentValues();
        values.put("ImageName", ImageName);
        values.put("Image", imageBytes);
        db.insert("imageTb", null, values);

        Cursor c = db.rawQuery("select Image from imageTb where ImageName ='" + ImageName + "'", null);
        if (c.getCount() != 0) {
            flag = true;
            c.close();
            db.close();

        } else {
            flag = false;
            c.close();
            db.close();

        }

        return flag;

    }

    public Bitmap getImage(final String Image_Name) {

        SQLiteDatabase db = this.getReadableDatabase();


        Bitmap BM_image = null;

        Cursor c = db.rawQuery("select Image from imageTb where ImageName ='" + Image_Name + "'", null);

        if (c.getCount() != 0) {
            if (c.moveToNext()) {
                byte[] image = c.getBlob(0);
                BM_image = BitmapFactory.decodeByteArray(image, 0, image.length);

                c.close();
                db.close();
            }
        } else {
            c.close();
            db.close();

        }
        return BM_image;

    }

    public boolean DeletData() {
        boolean flag = false;
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();


        db2.delete("imageTb",null,null);
        Cursor c = db.rawQuery("select * from imageTb", null);

        if (c.getCount() != 0) {

            c.close();
            db.close();
            db2.close();

            flag = false;

        } else {
            c.close();
            db.close();
            db2.close();

            flag = true;

        }


        return flag;
    }


}
