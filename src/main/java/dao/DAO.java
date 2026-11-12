package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lop DAO co so: giu ket noi JDBC ({@code con}) cho cac lop DAO con ke thua.
 * <p>
 * Ket noi duoc mo lazy va tu mo lai neu bi dong/het han, vi cac DAO duoc giu
 * nhu thuoc tinh cua Servlet (dung chung giua nhieu request).
 */
public class DAO {

    /** Ket noi hien tai (so do lop ghi la -con; de protected de lop con dung duoc). */
    protected Connection con;

    private final String url;
    private final String user;
    private final String password;

    public DAO() {
        Properties p = new Properties();
        try (InputStream in = DAO.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            throw new DataAccessException("Không đọc được db.properties", e);
        }
        url = env("CINEMAN_DB_URL", p.getProperty("db.url"));
        user = env("CINEMAN_DB_USER", p.getProperty("db.user"));
        password = env("CINEMAN_DB_PASSWORD", p.getProperty("db.password", ""));
        String driver = p.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DataAccessException("Không tìm thấy JDBC driver " + driver, e);
        }
    }

    private static String env(String name, String fallback) {
        String v = System.getenv(name);
        return (v != null && !v.isEmpty()) ? v : fallback;
    }

    /** Tra ve ket noi con song; mo lai neu can. */
    protected synchronized Connection getConnection() {
        try {
            if (con == null || con.isClosed() || !con.isValid(2)) {
                con = DriverManager.getConnection(url, user, password);
            }
            return con;
        } catch (SQLException e) {
            throw new DataAccessException("Không kết nối được CSDL", e);
        }
    }
}
