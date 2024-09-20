package ru.netology.banklogin.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }
    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes order by created DESC limit 1;";
        var conn = getConn();
        return QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "delete from auth_codes");
        QUERY_RUNNER.execute(connection, "delete from card_transactions");
        QUERY_RUNNER.execute(connection, "delete from cards");
        QUERY_RUNNER.execute(connection, "delete from users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "delete from auth_codes");
    }

}
