package org.first.myalbum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddimageActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextDescription;
    private ImageView imageViewAdd;
    private Button addbtn;
    private Bitmap selectedImage;
    private  Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimage);
        getSupportActionBar().setTitle("Add Image");
        editTextTitle =findViewById(R.id.edittextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewAdd = findViewById(R.id.imageViewSelect);
        addbtn = findViewById(R.id.add);
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AddimageActivity.this ,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddimageActivity.this
                       ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}  , 1 );
                }else {
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent,2);
                }

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedImage == null){
                    Toast.makeText(AddimageActivity.this , "Please Select Image " , Toast.LENGTH_SHORT);
                }else {
                    String title = editTextTitle.getText().toString();
                    String description = editTextDescription.getText().toString();
                    ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
                    scaledImage =makeSmall(selectedImage , 300);
                    scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                    byte [] image = outputStream.toByteArray();

                    Intent intent = new Intent();
                    intent.putExtra("title" , title);
                    intent.putExtra("description" , description);
                    intent.putExtra("image" , image);
                    setResult(RESULT_OK,intent);
                    finish();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==1 && grantResults.length>0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else {
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent,2);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode == RESULT_OK && data!= null){
            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver() , data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                }else{
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver() , data.getData());
                }
                imageViewAdd.setImageBitmap(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Bitmap makeSmall (Bitmap image , int maxSize){
        int width = image.getWidth();
        int hight = image.getHeight();
        float ratio = (float) width / hight;

        if(ratio > 1){
            width = maxSize;
            hight = (int)(width / ratio);
        }else {
            hight = maxSize;
            width = (int)(hight * ratio);
        }
        return Bitmap.createScaledBitmap(image , width , hight ,true);
    }
}