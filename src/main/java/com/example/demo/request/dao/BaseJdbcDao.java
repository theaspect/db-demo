package com.example.demo.request.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public abstract class BaseJdbcDao<T> {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        // Initialize once and for all JdbcDao classes
        try (InputStream is = BaseJdbcDao.class.getClassLoader()
                .getResourceAsStream("/application.properties")) {
            Properties prop = new Properties();
            prop.load(is);

            driver = prop.getProperty("app.datasource.driver-class-name");
            url = prop.getProperty("app.datasource.url");
            username = prop.getProperty("app.datasource.username");
            password = prop.getProperty("app.datasource.password");
        } catch (IOException e) {
            throw new RuntimeException("Can't load DB params from application.properties", e);
        }
    }

    public abstract void save(T entity) throws Exception;
    public abstract boolean update(T entity) throws Exception;
    public abstract boolean delete(Long id) throws Exception;

    // FIXME This is for demo
    //public abstract String getById(Long id) throws Exception;
    // FIXME This is for demo
    public abstract String getAll() throws Exception;

    protected Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
