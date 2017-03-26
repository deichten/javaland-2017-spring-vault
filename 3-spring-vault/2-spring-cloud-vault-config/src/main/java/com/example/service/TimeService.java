package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TimeService {

    private static final Logger LOG = LoggerFactory.getLogger(TimeService.class);

    private DataSource datasource;

    @Autowired
    public TimeService(DataSource datasource) throws SQLException {
        this.datasource = datasource;
    }

    public Optional<LocalDateTime> getTimeStamp() {
        LocalDateTime timestamp = null;
        try {
            Statement stmt = datasource.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT NOW();");
            resultSet.next();
            timestamp = LocalDateTime.parse(resultSet.getString(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            LOG.error("Error while retrieving time from MySQL", e);
        }
        return Optional.of(timestamp);
    }
}
