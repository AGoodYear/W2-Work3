package dao;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemEdit {
    public static void createItem(String name, double price){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "INSERT INTO iteminfo (name, price) VALUES (?,?)";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setDouble(2, price);
            int i = st.executeUpdate();
            if (i>0) {
                con.commit();
                System.out.println("创建商品成功！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    public static void deleteItem(String name) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "DELETE FROM iteminfo WHERE name=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            int i = st.executeUpdate();
            if (i>0) {
                con.commit();
                System.out.println("删除商品成功！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    public static void updateItem(String name, double price){
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE iteminfo SET price=? WHERE name=?";
            st = con.prepareStatement(sql);
            st.setDouble(1, price);
            st.setString(2, name);
            int i = st.executeUpdate();
            if (i>0) {
                con.commit();
                System.out.println("修改价格成功！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    public static double retrieveItem(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        double itemPrice = 0;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT name, price FROM iteminfo WHERE name=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs!=null) {
                con.commit();
                rs.next();
                itemPrice = rs.getDouble("price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return itemPrice;
    }

    public static String retrieveItemName(int id){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String itemName = "";
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT name FROM iteminfo WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs!=null) {
                con.commit();
                rs.next();
                itemName = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return itemName;
    }

    public static int retrieveItemId(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int itemId = 0;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id FROM iteminfo WHERE name=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs!=null) {
                con.commit();
                rs.next();
                itemId = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return itemId;
    }

    public static void listItem(){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT name, price FROM iteminfo";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs!=null) {
                con.commit();
                System.out.println("商品清单如下：");
                String itemName = "";
                double itemPrice = 0;
                while (rs.next()) {
                    itemName = rs.getString("name");
                    itemPrice = rs.getDouble("price");
                    System.out.println(itemName + ":" + itemPrice+"元");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }
}
