package org.first.myalbum;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MuimageAdapter extends RecyclerView.Adapter<MuimageAdapter.MyimageHolder>{
    private List<Myimages> imagesList = new ArrayList<>();
    private onImageClickListner listner;

    public void setListner(onImageClickListner listner) {
        this.listner = listner;
    }

    public void setImagesList(List<Myimages> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }
    public interface onImageClickListner{
        void onImageClick(Myimages myimages);
    }

    @NonNull
    @Override
    public MyimageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card , parent ,false);
        return new MyimageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyimageHolder holder, int position) {
            Myimages myimages = imagesList.get(position);
            holder.textViewTitle.setText(myimages.getImage_Title());
            holder.textViewDescription.setText(myimages.getImage_description());
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(myimages.getImage() ,
                    0 , myimages.getImage().length));
    }
    public Myimages getPosition(int position){
        return  imagesList.get(position);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class MyimageHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textViewTitle , textViewDescription;

        public MyimageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listner != null && position!= RecyclerView.NO_POSITION){
                        listner.onImageClick(imagesList.get(position));
                    }
                }
            });
        }
    }

}
