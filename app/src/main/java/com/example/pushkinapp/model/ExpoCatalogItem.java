package com.example.pushkinapp.model;

public class ExpoCatalogItem {
    private Integer id;
    private String title;
    private Integer imgid;
    private String infoText;
    private int sound;

    public ExpoCatalogItem(Integer id, int sound, String title, Integer imgid, String infoText) {
        this.id = id;
        this.sound = sound;
        this.title = title;
        this.imgid = imgid;
        this.infoText = infoText;
    }

    public String getTitle() {
        return title;
    }

    public Integer getImgid() {
        return imgid;
    }

    public String getInfoText() {
        return infoText;
    }

    public Integer getId() {
        return id;
    }

    public int getSound() {
        return sound;
    }
}
