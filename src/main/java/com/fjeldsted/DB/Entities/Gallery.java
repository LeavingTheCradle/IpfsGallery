package com.fjeldsted.DB.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(length = 255, unique = false)
    public String cid;

    public String title;
    public String description;
    public String artwork_cid;

    // @OneToMany(mappedBy = "gallery")
    // public GalleryPhoto[] photos;

    public Gallery() {
    }
}
