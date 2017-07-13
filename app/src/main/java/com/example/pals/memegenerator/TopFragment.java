package com.example.pals.memegenerator;

/**
 * Created by pals on 01-06-2017.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TopFragment extends Fragment{

    private static EditText topTextInput;
    private static EditText bottomTextInput;
    Bitmap myImg;
    //private static ImageView imgView;

    TopSectionListener activityCommander;

    public interface TopSectionListener{
        public void createMeme(String top, String bottom);
        public void createMemeImg(Bitmap img);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommander = (TopSectionListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);

        topTextInput = (EditText) view.findViewById(R.id.topTextEdit);
        bottomTextInput = (EditText) view.findViewById(R.id.bottomTextEdit);
        final Button button = (Button) view.findViewById(R.id.button);
        final Button upbutton = (Button) view.findViewById(R.id.upbutton);
        //imgView = (ImageView) view.findViewById(R.id.imgView);

        button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        buttonClicked(v);
                    }
                }
        );

        upbutton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        upbuttonClicked(v);
                    }
                }
        );

        return view;
    }

    public void buttonClicked(View view){
        activityCommander.createMeme(topTextInput.getText().toString(),bottomTextInput.getText().toString());
    }

    //private static int RESULT_LOAD_IMG = 1;

    public void upbuttonClicked(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        activityCommander.createMemeImg(myImg);
    }

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && data!=null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                myImg = BitmapFactory.decodeFile(imgDecodableString);
                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(getActivity(), "Some thing went Wrong!!", Toast.LENGTH_LONG).show();
        }
    }
}
