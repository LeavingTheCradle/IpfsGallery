package com.fjeldsted.Http.Routes;

import io.ipfs.api.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
import org.json.JSONObject;
import org.tinylog.Logger;

public class IpfsRoutes extends RouteUtils { // extends RouteUtil
    Tika tika;
    Database db;
    IpFiles ipFiles;
    public String path;

    public IpfsRoutes(Database db, IpFiles ipFiles) {// OldSchool DI. Just need to overload SessionFactory for testing
        this.ipFiles = ipFiles;
        this.tika = new Tika();
        this.db = db;
        Session session = this.db.getSession().openSession();
        session.beginTransaction();

        Photo pic = session.get(Photo.class, 1);
        Logger.info(pic);

        session.getTransaction().commit();
        session.close();
        this.path = "/ipfs/";
    }

    public void upload(HttpExchange ex) {

        byte[] body;
        try {
            if ("GET".equals(ex.getRequestMethod())) {
                this._sendJson(ex, 200, this._getError("400", "Error", "501101"));
                return;
            }
            body = ex.getRequestBody().readAllBytes();
            Headers reqheaders = ex.getRequestHeaders();
            // Check if content-type matches. Check if body empty.
            Map<String, List<FileItem>> files = MultipartParser.parseRequest(body, reqheaders.getFirst("Content-Type"));
            // Check if files empty. Null checks.
            // For map to mass upload up to config.limit.

            FileItem item = files.get("file").get(0);
            // foreach item.
            FileItem field = files.get("otherField").get(0);
            Logger.info(item.getName());
            byte[] file_bytes = item.getInputStream().readAllBytes();
            NamedStreamable.ByteArrayWrapper file_stream = new NamedStreamable.ByteArrayWrapper(file_bytes);
            MerkleNode addResult = this.ipFiles.getIPFS().add(file_stream).get(0);
            String cid = addResult.hash.toString();

            Session session = this.db.getSession().openSession();
            session.beginTransaction();

            Photo pic = session.get(Photo.class, 1);
            Logger.info(pic);
            if (pic == null) {

                pic = new Photo();
                String file_mimetype = tika.detect(file_bytes);

                pic.cid = cid;
                pic.mimetype = file_mimetype;
                pic.filename = item.getName();// Empty without headers directly.
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(file_bytes);
                    pic.hash = DatatypeConverter.printHexBinary(hash); // make it printable
                } catch (NoSuchAlgorithmException e) {
                    Logger.error("Fail hash");
                }
                session.persist(pic);

            }
            // Get Photo where cid
            // If (Photo)
            // Return
            // If(!Photo)
            // Hash = file_bytes.hash
            // mimetype = tika.detect
            // Photo = new. hash,mimetype,filename.

            session.getTransaction().commit();
            session.close();

            Logger.info(field.getFieldName());
            Logger.info(field.getString());
            Logger.info(item.getInputStream());

            ex.sendResponseHeaders(200, cid.getBytes().length);// response code and length
            OutputStream os = ex.getResponseBody();
            os.write(cid.getBytes());
            os.close();

        } catch (Exception e) {
            // Session.close etc.
            Logger.error(e);
        }
    }

    public void index(HttpExchange ex) {
        try {
            //
            String response = "Hi there!";

            ex.sendResponseHeaders(200, response.getBytes().length);// response code and length
            OutputStream os = ex.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public HttpWorker[] build() {
        return new HttpWorker[] {
                new Stereotypes.HttpWorker(this.path + "upload", ex -> {
                    upload(ex);
                }),
                new Stereotypes.HttpWorker(this.path + "", ex -> {
                    index(ex);
                }),

        };
    }
}
