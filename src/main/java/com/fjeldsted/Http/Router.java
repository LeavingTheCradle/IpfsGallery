package com.fjeldsted.Http;

import eu.lucaventuri.fibry.Stereotypes.HttpWorker;

import org.tinylog.Logger;

import com.fjeldsted.DB.Database;
import com.fjeldsted.Http.Routes.*;
import com.fjeldsted.IpFiles.IpFiles;

import eu.lucaventuri.common.SystemUtils;
import eu.lucaventuri.fibry.ActorSystem;
import eu.lucaventuri.fibry.ActorUtils;
import eu.lucaventuri.fibry.Stereotypes;
import eu.lucaventuri.fibry.Stereotypes.HttpWorker;

public class Router {
    private IpfsRoutes ipfsRoutes;
    private MediaRoutes mediaRoutes;

    private HttpWorker[] workers;

    public Router(Database db, IpFiles ipFiles) {

        this.ipfsRoutes = new IpfsRoutes(db, ipFiles);
        this.mediaRoutes = new MediaRoutes(db, ipFiles);
        this.workers = new HttpWorker[] {};

        _build(this.ipfsRoutes.build());
        _build(this.mediaRoutes.build());

    }

    private void _build(HttpWorker[] routeworkers) {
        HttpWorker[] work = new HttpWorker[this.workers.length + routeworkers.length];
        System.arraycopy(this.workers, 0, work, 0, this.workers.length);
        System.arraycopy(routeworkers, 0, work, this.workers.length, routeworkers.length);
        this.workers = work;
    }

    public HttpWorker[] getWorkers() {

        return this.workers;
    }
}
