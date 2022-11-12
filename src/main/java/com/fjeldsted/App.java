package com.fjeldsted;

import eu.lucaventuri.common.SystemUtils;
import eu.lucaventuri.fibry.ActorSystem;
import eu.lucaventuri.fibry.ActorUtils;
import eu.lucaventuri.fibry.Stereotypes;
import eu.lucaventuri.fibry.Stereotypes.HttpWorker;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import io.ipfs.api.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.fjeldsted.Http.Routes.*;
import com.fjeldsted.util.*;

/**
 * Hello world!
 *
 */
public class App {
    public void App() {
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        int port = 18000;
        server.Listen(18000);

        /*
         * new HttpStringWorker("/cnt", ex -> "Hello world - " +
         * numCalls.incrementAndGet()),
         * new HttpStringWorker("/test", ex -> {
         * return "Waited 1s";
         * }),
         * new HttpStringWorker("/wait10", ex -> {
         * SystemUtils.sleep(10_000);
         * return "Waited 10s";
         * }),
         * new HttpStringWorker("/wait60", ex -> {
         * SystemUtils.sleep(60_000);
         * return "Waited 60s";
         * }));
         */
        System.out.println("Waiting on http://localhost:" + port);
        System.out.println("Fibers available: " + ActorUtils.areFibersAvailable());
    }
}
