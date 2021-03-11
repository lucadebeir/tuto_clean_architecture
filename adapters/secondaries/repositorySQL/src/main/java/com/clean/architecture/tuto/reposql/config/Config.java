package com.clean.architecture.tuto.reposql.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {

    public static class SingletonSQL {
        private static SingletonSQL instance;
        private Connection connection;
        private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        private String username = "root";
        private String password = "root";

        private SingletonSQL() throws SQLException {
            this.connection = DriverManager.getConnection(url, username, password);
        }

        public Connection getConnection() {
            return connection;
        }

        public static SingletonSQL getInstance() throws SQLException {
            if (instance == null) {
                instance = new SingletonSQL();
            } else if (instance.getConnection().isClosed()) {
                instance = new SingletonSQL();
            }

            return instance;
        }
    }
}
