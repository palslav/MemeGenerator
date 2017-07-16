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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class BottomFragment extends Fragment{

    private TextView topMemeText;
    private TextView bottomMemeText;
    View bottomView;
    //private ImageView imgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment, container, false);
        topMemeText = (TextView) view.findViewById(R.id.topText);
        bottomMemeText = (TextView) view.findViewById(R.id.bottomText);
        //imgView = (ImageView) view.findViewById(R.id.imageView2);
        bottomView = view;

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Bitmap newPhoto = getBitmapFromView(view);
                //imgView.setImageBitmap(newPhoto);
                //MediaStore.Image.Media.insertImage(getContentResolver(), newPhoto, "title", "description");
                boolean isSaved = false;
                Context context = view.getContext();
                isSaved = saveImageToInternalStorage(newPhoto, context);
                if(isSaved){
                    Toast.makeText(getActivity(), "Image saved!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Image not saved!!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        return view;
    }

    public void setMemeText(String top, String bottom){
        topMemeText.setText(top);
        bottomMemeText.setText(bottom);
    }

    public void setMemeImage(ImageView Img){
        //imgView.setImageDrawable(Img.getDrawable());
        if(Img!=null) {
            if (bottomView != null)
                bottomView.setBackground(Img.getDrawable());
            else
                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "NULL DATA!!", Toast.LENGTH_SHORT).show();
        }
    }

    /** Getting view as Bitmap Image */
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

    /** Saving Image to Internal Storage */
    public static boolean saveImageToInternalStorage(Bitmap image, Context context) {
        File pictureFile = getOutputMediaFile(context);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");// e.getMessage());
            return false;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(pictureFile));
            context.sendBroadcast(intent);

            fos.flush();
            fos.close();

            return true;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return false;
    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(Context context){
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        if (! myDir.exists()){
            if (! myDir.mkdirs()){
                return null;
            }
        }
        Log.d(TAG, "mediastoragepath: " + myDir.toString());
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MG_"+ timeStamp +".jpg";
        mediaFile = new File(myDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
