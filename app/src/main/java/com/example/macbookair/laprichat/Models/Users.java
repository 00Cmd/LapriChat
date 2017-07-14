package com.example.macbookair.laprichat.Models;

/**
 * Created by macbookair on 14/07/2017.
 */

public class Users {
    private String Name,Status,Image,Thumb_img;

    public Users() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getThumb_img() {
        return Thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        Thumb_img = thumb_img;
    }
}
