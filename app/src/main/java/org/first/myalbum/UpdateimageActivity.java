package org.first.myalbum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateimageActivity extends AppCompatActivity {
    private EditText editTextUpddateTitle, editTextUpdateDescription;
    private ImageView imageViewUpdate;
    private Button updatebtn;
    private String title,description;
    private byte [] image;
    private int id;
   private Bitmap selectedImage;
    private  Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateimage);
        getSupportActionBar().setTitle("Update Image");
        editTextUpddateTitle =findViewById(R.id.edittextUpdateTitle);
        editTextUpdateDescription = findViewById(R.id.editTextUpdateDescription);
        imageViewUpdate = findViewById(R.id.imageViewUpdate);
        updatebtn = findViewById(R.id.updatebtn);

        id = getIntent().getIntExtra("id" , -1);
        title = getIntent().getStringExtra("title" );
        description = getIntent().getStringExtra("description");
        image = getIntent().getByteArrayExtra("image");

        editTextUpddateTitle.setText(title);
        editTextUpdateDescription.setText(description);
        imageViewUpdate.setImageBitmap(BitmapFactory.decodeByteArray(image , 0,image.length));

        imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent,5);
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    updateData();
            }
        });
    }
    public void updateData (){
        if(id == -1)
        {
            Toast.makeText(UpdateimageActivity.this , "There is a problem " , Toast.LENGTH_SHORT).show();
        }
        else
            {
            String Updatetitle = editTextUpddateTitle.getText().toString();
            String Updatedescription = editTextUpdateDescription.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("id" , id);
            intent.putExtra("updatetitle" , Updatetitle);
            intent.putExtra("updatedescription" , Updatedescription);


        if(selectedImage == null){
            intent.putExtra("image" , image);
        }else {

            ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
            scaledImage =makeSmall(selectedImage , 300);
            scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            byte [] image = outputStream.toByteArray();
            intent.putExtra("image" , image);

        }
        setResult(RESULT_OK , intent);
        finish();
    }}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==5 && resultCode == RESULT_OK && data!= null){
            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver() , data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                }else{
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver() , data.getData());
                }
                imageViewUpdate.setImageBitmap(selectedImage);

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