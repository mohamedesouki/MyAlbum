package org.first.myalbum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     private MyimagesViewModel myimagesViewModel;
     private RecyclerView rv;
     private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab);



        MuimageAdapter myimageAdapter = new MuimageAdapter();
        rv.setAdapter(myimageAdapter);
        myimagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MyimagesViewModel.class);
        myimagesViewModel.getImagesList().observe(MainActivity.this, new Observer<List<Myimages>>() {
            @Override
            public void onChanged(List<Myimages> myimages) {
                myimageAdapter.setImagesList(myimages);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddimageActivity.class);
                startActivityForResult(intent , 3);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                myimagesViewModel.delete(myimageAdapter.getPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(rv);
        myimageAdapter.setListner(new MuimageAdapter.onImageClickListner(){

            @Override
            public void onImageClick(Myimages myimages) {
                Intent intent = new Intent(MainActivity.this , UpdateimageActivity.class);
                intent.putExtra("id" , myimages.getId());
                intent.putExtra("title" , myimages.getImage_Title());
                intent.putExtra("description" , myimages.getImage_description());
                intent.putExtra("image" , myimages.getImage());
                startActivityForResult(intent , 4);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 &&resultCode == RESULT_OK && data !=null){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            byte [] image = data.getByteArrayExtra("image");
            Myimages myimages = new Myimages(title , description , image);
            myimagesViewModel.insert(myimages);
        }
        if(requestCode == 4 &&resultCode == RESULT_OK && data !=null){
            String title = data.getStringExtra("updatetitle");
            String description = data.getStringExtra("updatedescription");
            byte [] image = data.getByteArrayExtra("image");
            int id = data.getIntExtra("id" , -1);
            Myimages myimages = new Myimages(title , description , image);
            myimages.setId(id);
            myimagesViewModel.update(myimages);
        }
    }
}