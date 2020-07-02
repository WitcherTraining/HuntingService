package com.epam.huntingService.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Integer.parseInt;

public final class ConnectionPool {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private String url;
    private String user;
    private String password;
    private String driverName;
    private ResourceBundle properties = ResourceBundle.getBundle("ConnectionPool");
    private final int POOL_SIZE = parseInt(properties.getString("db.poolSize"));
    private static volatile ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        init();
    }

    private void init() {
        setDataForConnection();
        loadDrivers();
        initPoolData();
    }

    private void setDataForConnection() {
        this.driverName = properties.getString("db.driver");
        this.url = properties.getString("db.url");
        this.user = properties.getString("db.user");
        this.password = properties.getString("db.password");
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverName).newInstance();
        } catch (InstantiationException | ClassNotFoundException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.warn(e);
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    private void initPoolData() {
        Connection connection;

        while (connectionQueue.size() < POOL_SIZE) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                connectionQueue.put(connection);
            } catch (InterruptedException | SQLException e) {
                LOGGER.warn(e);
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        if ((connection != null) && (connectionQueue.size() <= POOL_SIZE)) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
    }
}
