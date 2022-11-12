package com.fjeldsted.DB.Entities;

import jakarta.persistence.*;

@Entity
public class GalleryPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "gallery_id", nullable = false)
    public Gallery gallery;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    public Photo photo;

    public int orderId; // 1+ determines order gallery is loaded in.

    // Timestamps for current upload
}
