package com.github.waiversocial;

import com.github.waiversocial.database.Database;
import com.github.waiversocial.http.UserEndPoints;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import social.nickrest.http.HTTPBuilder;

import java.sql.*;

public class Main {

    public static Logger logger = LogManager.getLogger(Main.class);
    @Getter public static final Database database = new Database("waiversocial", (byte) 30);

    public static void main(String[] args) throws SQLException {
        HTTPBuilder.create()
                .endpoint(new UserEndPoints())
                .listen(8080, (j) -> logger.info("Server started listening on port 8080"));
    }

}