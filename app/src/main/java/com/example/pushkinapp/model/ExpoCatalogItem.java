package com.example.pushkinapp.model;

/**
 * The type Expo catalog item.
 */
public class ExpoCatalogItem {
    private Integer id;
    private String title;
    private Integer imgid;
    private String infoText;
    private int sound;

    /**
     * Instantiates a new Expo catalog item.
     *
     * @param id       the id
     * @param sound    the sound
     * @param title    the title
     * @param imgid    the imgid
     * @param infoText the info text
     */
    public ExpoCatalogItem(Integer id, int sound, String title, Integer imgid, String infoText) {
        this.id = id;
        this.sound = sound;
        this.title = title;
        this.imgid = imgid;
        this.infoText = infoText;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
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
     * Gets sound.
     *
     * @return the sound
     */
    public int getSound() {
        return sound;
    }
}
