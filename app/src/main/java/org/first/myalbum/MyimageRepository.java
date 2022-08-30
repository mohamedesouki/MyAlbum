package org.first.myalbum;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyimageRepository {
    private Myimages_DAO myimages_dao;
    private LiveData<List<Myimages>> imageslist;

    public MyimageRepository(Application application){
        MyimagesDatabase database = MyimagesDatabase.getInstance(application);
        myimages_dao = database.myimages_dao();
        imageslist = myimages_dao.getAllimages();

    }

    public void insert(Myimages myimages){
        new InsertImageAsyncTask(myimages_dao).execute(myimages);
    }
    public void update(Myimages myimages){
        new UpdateImageAsyncTask(myimages_dao).execute(myimages);
    }

    public void delete(Myimages myimages){
        new DeleteImageAsyncTask(myimages_dao).execute(myimages);
    }
    LiveData<List<Myimages>> getAllimages(){
        return imageslist;
    }
    public static class InsertImageAsyncTask extends AsyncTask<Myimages , Void , Void>{
        Myimages_DAO myimages_dao;

        public InsertImageAsyncTask(Myimages_DAO myimages_dao) {
            this.myimages_dao = myimages_dao;
        }

        @Override
        protected Void doInBackground(Myimages... myimages) {
            myimages_dao.insert(myimages[0]);
            return null;
        }
    }
    public static class UpdateImageAsyncTask extends AsyncTask<Myimages , Void , Void>{
        Myimages_DAO myimages_dao;

        public UpdateImageAsyncTask(Myimages_DAO myimages_dao) {
            this.myimages_dao = myimages_dao;
        }

        @Override
        protected Void doInBackground(Myimages... myimages) {
            myimages_dao.update(myimages[0]);
            return null;
        }
    }
    public static class DeleteImageAsyncTask extends AsyncTask<Myimages , Void , Void>{
        Myimages_DAO myimages_dao;

        public DeleteImageAsyncTask(Myimages_DAO myimages_dao) {
            this.myimages_dao = myimages_dao;
        }

        @Override
        protected Void doInBackground(Myimages... myimages) {
            myimages_dao.delete(myimages[0]);
            return null;
        }
    }
}
