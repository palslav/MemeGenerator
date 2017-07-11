package com.example.pals.memegenerator;

/**
 * Created by pals on 01-06-2017.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class BottomFragment extends Fragment{

    private static TextView topMemeText;
    private static TextView bottomMemeText;
    //private static ImageView imgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment, container, false);
        topMemeText = (TextView) view.findViewById(R.id.topText);
        bottomMemeText = (TextView) view.findViewById(R.id.bottomText);
        //imgView = (ImageView) view.findViewById(R.id.imgView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap newPhoto = getBitmapFromView(view);
                //MediaStore.Image.Media.insertImage(getContentResolver(), newPhoto, "title", "description");
                boolean isSaved = false;
                Context context = view.getContext();
                isSaved = saveImageToInternalStorage(newPhoto, context);
                if(isSaved){
                    Toast.makeText(getActivity(), "Image saved!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Image not saved!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void setMemeText(String top, String bottom){
        topMemeText.setText(top);
        bottomMemeText.setText(bottom);
    }

    public void setMemeText(){
        
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static boolean saveImageToInternalStorage(Bitmap image, Context context) {

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = context.openFileOutput("desiredFilename.jpeg", Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            //Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }
}
