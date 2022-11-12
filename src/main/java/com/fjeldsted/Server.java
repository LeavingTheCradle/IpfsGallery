package com.fjeldsted;

import java.io.IOException;

import eu.lucaventuri.fibry.Stereotypes;

import com.fjeldsted.DB.Database;
import com.fjeldsted.Http.Router;
import com.fjeldsted.IpFiles.IpFiles;

public class Server {
    private Router router;
    private Database db;
    private IpFiles ipFiles;

    public Server() {
        this.db = new Database();
        this.ipFiles = new IpFiles();

        this.router = new Router(this.db, this.ipFiles);
    }

    public void Listen(int port) {
        try {
            Stereotypes.auto().embeddedHttpServer(port, this.router.getWorkers());
        } catch (IOException e) {
            // Log?
        }

    }
}
