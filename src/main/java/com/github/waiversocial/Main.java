package com.github.waiversocial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import social.nickrest.http.HTTPBuilder;

public class Main {

    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        HTTPBuilder.create()
                .listen(8080, (server) -> {
                    logger.info("Server began listening at http://localhost:{}", server.getAddress().getPort());
                });
    }

}