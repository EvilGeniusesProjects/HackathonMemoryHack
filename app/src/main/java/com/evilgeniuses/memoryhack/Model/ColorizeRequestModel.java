package com.evilgeniuses.memoryhack.Model;

public class ColorizeRequestModel {
    private String image;

    public ColorizeRequestModel() {
    }

    public ColorizeRequestModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
