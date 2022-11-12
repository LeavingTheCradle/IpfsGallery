package com.fjeldsted.DB.Entities;

import jakarta.persistence.*;

@Entity(name = "Photo")
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255, unique = false)
    public String cid;

    public String filename;
    public String mimetype;
    public String hash;

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return this.mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    // Timestamps for first added.
    // This will let you do cool shit like find image in to list all places its been
    // posted. by cid.

    public Photo() {
    }

}
