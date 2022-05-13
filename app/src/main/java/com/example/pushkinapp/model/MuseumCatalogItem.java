package com.example.pushkinapp.model;

import java.util.Comparator;

public class MuseumCatalogItem {
    private Integer id;
    private String title;
    private String secondary;
    private Integer imgid;
    private Integer logoid;
    private String infoText;
    private Integer price;

    public String getTitle() {
        return title;
    }

    public String getSecondary() {
        return secondary;
    }

    public Integer getImgid() {
        return imgid;
    }

    public Integer getLogoid() {
        return logoid;
    }

    public String getInfoText() {
        return infoText;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public MuseumCatalogItem(Integer id, Integer price, String title, Integer imgid, String secondary, Integer logoid, String infoText) {
        this.title = title;
        this.secondary = secondary;
        this.imgid = imgid;
        this.logoid = logoid;
        this.infoText = infoText;
        this.id = id;
        this.price = price;
    }

    public static class byTitle implements Comparator<MuseumCatalogItem> {
        @Override
        public int compare(MuseumCatalogItem item1, MuseumCatalogItem item2) {
            return item1.title.compareToIgnoreCase(item2.title);
        }
    }

}
