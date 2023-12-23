package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBCUtils用于处理数据库的连接与释放
 *
 * @author AGoodYear
 * @date 2023/12/19
 */
public class JDBCUtils {
    /**
     * 数据库连接地址
     */
    private static String url;
    /**
     * 数据库用户名
     */
    private static String username;
    /**
     * 数据库密码
     */
    private static String password;

    /**
     * 读取配置文件
     */
    static{
        try{
            InputStream input = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection对象
     * @throws SQLException 如果连接过程出现错误
     */
    public static Connection getConnection() throws SQLException{
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 释放数据库连接
     *
     * @param con 要释放的Connection对象
     * @param st 要释放的PreparedStatement对象
     * @param rs 要释放的ResultSet对象
     */
    public static void release(Connection con, PreparedStatement st, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (st != null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


