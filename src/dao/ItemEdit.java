package dao;

import pojo.Item;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ItemEdit类用于存放对订单进行增删改查的函数
 *
 * @author AGoodYear
 * @date 2023/12/19
 */
public class ItemEdit {
    /**
     * 创建商品
     *
     * @param name 商品名称
     * @param price 商品价格
     */
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

    /**
     * 删除商品
     *
     * @param name 要删除的商品名称
     */
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
            } else {
                System.out.println("没有找到该商品");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /**
     * 更新商品价格
     *
     * @param name 要更新的商品名字
     * @param price 更新的价格
     */
    public static void updateItem(String name, double price){
        Connection con = null;
        PreparedStatement st = null;
        try {
            if (price < 0) {
                throw new ObjectNotFoundException();
            }
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
            } else {
                throw new ObjectNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ObjectNotFoundException e) {
            System.out.println("参数不合法！");
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /**
     * 根据商品名称查询商品
     *
     * @param name 要查询的商品名字
     * @return 商品对象
     */
    public static Item retrieveItem(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        double itemPrice = 0;
        int itemId = 0;
        Item item;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id, name, price FROM iteminfo WHERE name=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                itemPrice = rs.getDouble("price");
                itemId = rs.getInt("id");
                item = new Item(name, itemPrice);
                item.setId(itemId);
            } else {
                throw new ObjectNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ObjectNotFoundException e) {
            e.showError();
            item = new Item("", 0);
            item.setId(-1);
        } finally {
            JDBCUtils.release(con, st, rs);
        }

        return item;
    }

    /**
     * 根据商品编号查询商品信息
     *
     * @param id 要查询的商品编号
     * @return 商品对象
     */
    public static Item retrieveItem(int id){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String itemName = "";
        int itemId = 0;
        double itemPrice = 0;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT id, name, price FROM iteminfo WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                itemName = rs.getString("name");
                itemId = rs.getInt("id");
                itemPrice = rs.getDouble("price");
            }else {
                throw new ObjectNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ObjectNotFoundException e) {
            e.showError();
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        Item item = new Item(itemName, itemPrice);
        item.setId(itemId);
        return item;
    }

    /**
     * 列出当前所有商品
     */
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
            } else {
                System.out.println("系统中还没有商品");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }
}
