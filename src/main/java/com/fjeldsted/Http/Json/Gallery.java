package com.fjeldsted.Http.Json;

public class Gallery {
    private String id;
    private String title;
    private String description;
    private String artwork_cid;
    private GalleryItem[] photos;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtwork_cid() {
        return this.artwork_cid;
    }

    public void setArtwork_cid(String artwork_cid) {
        this.artwork_cid = artwork_cid;
    }

    public GalleryItem[] getPhotos() {
        return this.photos;
    }

    public void setPhotos(GalleryItem[] photos) {
        this.photos = photos;
    }

}
