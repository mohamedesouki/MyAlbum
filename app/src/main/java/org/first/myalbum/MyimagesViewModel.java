package org.first.myalbum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyimagesViewModel extends AndroidViewModel {
        private MyimageRepository repository;
        private LiveData<List<Myimages>> imagesList;

    public MyimagesViewModel(@NonNull Application application) {
        super(application);
        repository = new MyimageRepository(application);
        imagesList = repository.getAllimages();
    }
    public void insert (Myimages myimages){
        repository.insert(myimages);
    }
    public void update (Myimages myimages){
        repository.update(myimages);
    }
    public void delete (Myimages myimages){
        repository.delete(myimages);
    }
    public LiveData<List<Myimages>> getImagesList(){
        return imagesList;
    }
}
