package com.fjeldsted.Http.Routes;

import io.ipfs.api.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import com.fjeldsted.DB.Database;
import com.fjeldsted.Http.RouteUtils;
import com.fjeldsted.Http.Json.*;
import com.fjeldsted.Http.Json.Error;
import com.fjeldsted.IpFiles.IpFiles;
import com.fjeldsted.DB.Entities.Photo;

import com.fjeldsted.util.MultipartParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import eu.lucaventuri.fibry.Stereotypes.HttpWorker;
import eu.lucaventuri.common.SystemUtils;
import eu.lucaventuri.fibry.ActorSystem;
import eu.lucaventuri.fibry.ActorUtils;
import eu.lucaventuri.fibry.Stereotypes;
import eu.lucaventuri.fibry.Stereotypes.HttpWorker;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tika.Tika;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.tinylog.Logger;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.coobird.thumbnailator.*;
import com.fjeldsted.util.*;

public class GalleryRoutes extends RouteUtils {
    public String path;
    Database db;

    public GalleryRoutes(Database db) {
        this.path = "/gallery/";
        this.db = db;
    }

    public void index(HttpExchange ex) {

    }

    public void getGalleryById(HttpExchange ex, String id) {

    }

    public void handleGalleryRoute(HttpExchange ex) {
        URI url = ex.getRequestURI();
        String[] segments = url.getPath().split("/");
        // 0=gallery, 1 = ID;
        if (segments.length < 2) {
            index(ex);
        } else if (segments.length > 2) {
            _sendJson(ex, 200, _getError("error", "error", "error"));
        }
        getGalleryById(ex, segments[1]);
    }

    public HttpWorker[] build() {
        return new HttpWorker[] {
                new Stereotypes.HttpWorker(this.path + "", this::handleGalleryRoute),// :: ref

        };
    }
}
