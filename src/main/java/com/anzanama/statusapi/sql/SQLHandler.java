package com.anzanama.statusapi.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHandler {
    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public SQLHandler(String hostname, String user, String password) {
    }
}
