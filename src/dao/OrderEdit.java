package dao;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OrderEdit {
    public static void createOrder(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        double totalPrice = 0;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "INSERT INTO orderinfo (totalprice, name) VALUES (?,?)";
            st = con.prepareStatement(sql);
            st.setDouble(1, 1);
            st.setString(2, name);
            st.executeUpdate();
            sql = "SELECT MAX(id) FROM orderinfo";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            String itemName = "";
            int amount = 0;
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                itemName = scanner.next();
                if ("q".equals(itemName)) {
                    break;
                }
                amount = scanner.nextInt();
                if (amount <= 0) {
                    System.out.println("输入非法！请重新输入");
                    continue;
                }
                int itemId = ItemEdit.retrieveItemId(itemName);
                sql = "INSERT INTO orderitem (orderid, itemid, amount) VALUES (?,?,?)";
                st = con.prepareStatement(sql);
                st.setInt(1, id);
                st.setInt(2, itemId);
                st.setInt(3, amount);
                st.executeUpdate();
                double itemPrice = ItemEdit.retrieveItem(itemName);
                totalPrice += itemPrice * amount;
            }
            sql = "UPDATE orderinfo SET totalprice = ? WHERE id = ?";
            st = con.prepareStatement(sql);
            st.setDouble(1, totalPrice);
            st.setInt(2, id);
            st.executeUpdate();
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void retrieveOrder(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id, totalprice, orderdate FROM orderinfo WHERE name = ?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            System.out.println("查询到以下订单信息：");
            while (rs.next()) {
                System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                String date = "";
                date = rs.getString("orderdate");
                System.out.println("下单时间" + date);
                System.out.println("--------------------");
            }
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void retrieveOrderByPrice(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id, totalprice, orderdate FROM orderinfo WHERE name = ? ORDER BY totalprice DESC";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            System.out.println("查询到以下订单信息：");
            while (rs.next()) {
                System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                String date = "";
                date = rs.getString("orderdate");
                System.out.println("下单时间" + date);
                System.out.println("--------------------");
            }
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void retrieveOrderByDate(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id, totalprice, orderdate FROM orderinfo WHERE name = ? ORDER BY orderdate DESC";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            System.out.println("查询到以下订单信息：");
            while (rs.next()) {
                System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                String date = "";
                date = rs.getString("orderdate");
                System.out.println("下单时间" + date);
                System.out.println("--------------------");
            }
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void retrieveOrder(int id){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT itemid, amount FROM orderitem WHERE orderid = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            System.out.println("编号为" + id + "的订单信息：");
            while (rs.next()) {
                int itemId = rs.getInt("itemid");
                String itemName = ItemEdit.retrieveItemName(itemId);
                System.out.println("商品名称：" + itemName + '\n' + "购买数量：" + rs.getInt("amount"));
            }
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void deleteOrder(String name){
        retrieveOrder(name);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        System.out.println("输入要删除的订单编号：");
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "DELETE FROM orderinfo WHERE id = ?";
            st = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();
            st.setInt(1, id);
            st.executeUpdate();
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    public static void updateOrder(String name){
        retrieveOrder(name);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        System.out.println("输入要更新的订单编号：");
        Scanner scanner = new Scanner(System.in);
        int orderId = scanner.nextInt();
        retrieveOrder(orderId);
        System.out.println("输入要修改的商品名称，按q退出");
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql;
            while (true) {
                String itemName = scanner.next();
                if ("q".equals(itemName)) {
                    break;
                }
                int itemId = ItemEdit.retrieveItemId(itemName);
                double itemPrice = ItemEdit.retrieveItem(itemName);
                sql = "SELECT amount FROM orderitem WHERE orderid=? AND itemid=?";
                st = con.prepareStatement(sql);
                st.setInt(1, orderId);
                st.setInt(2, itemId);
                rs = st.executeQuery();
                rs.next();
                int originalAmount = rs.getInt("amount");
                System.out.println("输入要修改的数量，0则删除该商品");
                int amount = scanner.nextInt();
                if (amount == 0) {
                    sql = "DELETE FROM orderitem WHERE orderid=? AND itemid=?";
                    st = con.prepareStatement(sql);
                    st.setInt(1, orderId);
                    st.setInt(2, itemId);
                } else {
                    sql = "UPDATE orderitem SET amount=? WHERE orderid=? AND itemid=?";
                    st = con.prepareStatement(sql);
                    st.setInt(1, amount);
                    st.setInt(2, orderId);
                    st.setInt(3, itemId);
                }
                st.executeUpdate();
                double changed = (amount - originalAmount) * itemPrice;
                sql = "UPDATE orderinfo SET totalprice = totalprice + ? WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setDouble(1,changed);
                st.setInt(2,orderId);
                st.executeUpdate();
                System.out.println("订单信息更新成功！");
            }
            sql = "SELECT itemid FROM orderitem WHERE orderid = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, orderId);
            rs = st.executeQuery();
            if (!rs.next()) {
                System.out.println("订单中已无商品，自动删除订单");
                sql = "DELETE FROM orderinfo WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setInt(1, orderId);
                st.executeUpdate();
            }
            con.commit();//test
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }
}
