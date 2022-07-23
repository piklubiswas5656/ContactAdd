package basic.contactadd.Model;

import android.graphics.Bitmap;

import basic.contactadd.Dbmanager;

public class CDel {
    private int id;
    private String name;
    private String number;
    private Bitmap image;

    public CDel(int id, String name, String number, Bitmap image) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
