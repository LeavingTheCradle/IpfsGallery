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

public class MediaRoutes extends RouteUtils {
    Database db;
    IpFiles ipFiles;
    Tika tika;
    public String path;

    public MediaRoutes(Database db, IpFiles ipFiles) {
        this.db = db;
        this.ipFiles = ipFiles;
        this.tika = new Tika();
        this.path = "/media/";
    }

    private String mimeExtension(String mimetype) {
        return MimeTypes.getDefaultExt(mimetype);
    }

    public void thumbnail(HttpExchange ex) {
        try {
            URI url = ex.getRequestURI();
            String[] segments = url.getPath().split("/");
            String cid = segments[segments.length - 1];
            Logger.info(segments[0]);
            Logger.info(segments[1]);

            String filetype;
            File filebuffer = new File("thumb/" + cid);
            if (!filebuffer.exists()) {
                Session session = this.db.getSession().openSession();
                session.beginTransaction();
                String hql = "FROM Photo P WHERE P.cid = '" + cid + "'";// bad
                Query query = session.createQuery(hql);
                List<Photo> results = query.list();
                session.getTransaction().commit();
                session.close();

                Logger.info(results.size());
                if (results.size() != 1) {
                    _sendJson(ex, 200, _getError("411", "File not found", "XXXXX"));// Not a true error
                    return;
                }
                Photo photo = results.get(0);
                Multihash filePointer = Multihash.fromBase58(photo.cid);
                byte[] photoFile = this.ipFiles.getIPFS().cat(filePointer);
                Logger.info("length:" + photoFile.length);

                InputStream is = new ByteArrayInputStream(photoFile);
                String file_mimetype = tika.detect(is);
                is.close();

                Logger.info(file_mimetype);
                int kbs = photoFile.length / 1000;
                if (kbs <= 300) {
                    filebuffer = new File("thumb/" + cid);

                    filebuffer.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(filebuffer);
                    outputStream.write(photoFile);
                    outputStream.close();
                    _sendImage(ex, photoFile, file_mimetype);
                    return;
                }

                is = new ByteArrayInputStream(photoFile);
                BufferedImage image = ImageIO.read(is);
                is.close();
                Logger.info("length2:" + image.getHeight());
                filebuffer.createNewFile();

                Thumbnails.Builder<BufferedImage> thumbnail = Thumbnails.of(image).outputFormat("JPEG")
                        .size(512, 512); // height controlled aspect
                // thumbnail.toFile(filebuffer);

                ByteArrayOutputStream thumb_os = new ByteArrayOutputStream();
                thumbnail.toOutputStream(thumb_os);
                FileOutputStream outputStream = new FileOutputStream(filebuffer);
                thumbnail.toOutputStream(outputStream);
                outputStream.close();
                byte[] thmb_file = thumb_os.toByteArray();
                _sendImage(ex, thmb_file, file_mimetype);

                return;
            }
            filetype = tika.detect(filebuffer);
            byte[] bFile = new byte[(int) filebuffer.length()];
            FileInputStream fileInputStream = new FileInputStream(filebuffer);
            fileInputStream.read(bFile);
            fileInputStream.close();
            _sendImage(ex, bFile, filetype); // In something like nginx or varnish do if(!file) routing
        } catch (Exception e) {
            Logger.error(e);
        }

    }

    public HttpWorker[] build() {
        return new HttpWorker[] {
                new Stereotypes.HttpWorker(this.path + "thumb", ex -> {
                    thumbnail(ex);
                })
        };
    }
}
