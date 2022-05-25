package com.example.pushkinapp.model;

import java.util.Comparator;

/**
 * The type Museum catalog item.
 */
public class MuseumCatalogItem {
    private Integer id;
    private String title;
    private String secondary;
    private Integer imgid;
    private Integer logoid;
    private String infoText;
    private Integer price;

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets secondary.
     *
     * @return the secondary
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * Gets imgid.
     *
     * @return the imgid
     */
    public Integer getImgid() {
        return imgid;
    }

    /**
     * Gets logoid.
     *
     * @return the logoid
     */
    public Integer getLogoid() {
        return logoid;
    }

    /**
     * Gets info text.
     *
     * @return the info text
     */
    public String getInfoText() {
        return infoText;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Instantiates a new Museum catalog item.
     *
     * @param id        the id
     * @param price     the price
     * @param title     the title
     * @param imgid     the imgid
     * @param secondary the secondary
     * @param logoid    the logoid
     * @param infoText  the info text
     */
    public MuseumCatalogItem(Integer id, Integer price, String title, Integer imgid, String secondary, Integer logoid, String infoText) {
        this.title = title;
        this.secondary = secondary;
        this.imgid = imgid;
        this.logoid = logoid;
        this.infoText = infoText;
        this.id = id;
        this.price = price;
    }

    /**
     * The type By title.
     */
    public static class byTitle implements Comparator<MuseumCatalogItem> {
        @Override
        public int compare(MuseumCatalogItem item1, MuseumCatalogItem item2) {
            return item1.title.compareToIgnoreCase(item2.title);
        }
    }
}
