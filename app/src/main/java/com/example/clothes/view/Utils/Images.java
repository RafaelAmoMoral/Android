package com.example.clothes.view.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Images {

    public static void resizeImage(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int newWidth = 100;
        int newHeight = 100;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        bmp = Bitmap.createBitmap(bmp, 0, 0,
                width, height, matrix, true);
    }

    public static String fromImageViewToBase64(ImageView iv){
        String finalImage=null;
        try {
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                finalImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
        }catch(ClassCastException cce){
            //Si la imagen es un vector, por ejemplo si es la imagen por defecto.
        }
        return finalImage;
    }


}
