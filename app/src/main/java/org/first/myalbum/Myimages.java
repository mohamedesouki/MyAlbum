package org.first.myalbum;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_image")
public class Myimages {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String image_Title;
    public String image_description;
    public byte[] image;

    public Myimages(String image_Title, String image_description, byte[] image) {
        this.image_Title = image_Title;
        this.image_description = image_description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getImage_Title() {
        return image_Title;
    }

    public String getImage_description() {
        return image_description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
