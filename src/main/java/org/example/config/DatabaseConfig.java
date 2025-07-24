package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            Dotenv dotenv = Dotenv.load();
            String host = dotenv.get("DB_HOST");
            String dbName = dotenv.get("DB_SCHEMA");
            String jdbcUrl = String.format("jdbc:mysql://%s:3306/%s", host, dbName);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(dotenv.get("DB_USER"));
            config.setPassword(dotenv.get("DB_PASS"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
