package dao;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * OrderEdit类用于存放对订单进行增删改查的函数
 *
 * @author AGoodYear
 * @date 2023/12/19
 */
public class OrderEdit {
    public static void createOrder(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        double totalPrice = 0;
        try {
            //支持事务管理
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);

            //先插入空订单
            String sql = "INSERT INTO orderinfo (totalprice, name) VALUES (?,?)";
            st = con.prepareStatement(sql);
            st.setDouble(1, 1);
            st.setString(2, name);
            st.executeUpdate();
            //然后获取当前的订单编号
            sql = "SELECT MAX(id) FROM orderinfo";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            //获取用户输入商品名和购买数量
            System.out.println("输入商品名和购买数量，输入q退出");
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
                int itemId = ItemEdit.retrieveItem(itemName).getId();
                if (itemId == -1) {
                    throw new ObjectNotFoundException();
                }
                sql = "INSERT INTO orderitem (orderid, itemid, amount) VALUES (?,?,?)";
                st = con.prepareStatement(sql);
                st.setInt(1, id);
                st.setInt(2, itemId);
                st.setInt(3, amount);
                st.executeUpdate();
                // 计算当前订单金额之和
                double itemPrice = ItemEdit.retrieveItem(itemName).getPrice();
                totalPrice += itemPrice * amount;
            }
            sql = "UPDATE orderinfo SET totalprice = ? WHERE id = ?";
            st = con.prepareStatement(sql);
            st.setDouble(1, totalPrice);
            st.setInt(2, id);
            st.executeUpdate();
            con.commit();//test
            System.out.println("订单创建成功！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ObjectNotFoundException e) {

        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    /**
     * 查询同一购买人的所有订单
     *
     * @param name 要查询的订单名字
     */
    public static int retrieveOrder(String name){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            //从数据库中取出信息并输出
            String sql = "SELECT id, totalprice, orderdate FROM orderinfo WHERE name = ?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("查询到以下订单信息：");
                System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                String date = "";
                date = rs.getString("orderdate");
                System.out.println("下单时间" + date);
                System.out.println("--------------------");
                while (rs.next()) {
                    System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                    date = rs.getString("orderdate");
                    System.out.println("下单时间" + date);
                    System.out.println("--------------------");
                }
            } else {
                System.out.println("系统中没有该人的名字！");
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return 0;
    }

    /**
     * 按订单金额大小排序输出订单信息
     *
     * @param name
     */
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
            if (rs != null) {
                System.out.println("查询到以下订单信息：");
                while (rs.next()) {
                    System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                    String date = "";
                    date = rs.getString("orderdate");
                    System.out.println("下单时间" + date);
                    System.out.println("--------------------");
                }
            } else {
                System.out.println("系统中没有该人的名字！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    /**
     * 按下单时间排序输出订单信息
     *
     * @param name 要查询的名字
     */
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
            if (rs != null) {
                System.out.println("查询到以下订单信息：");
                while (rs.next()) {
                    System.out.println("订单编号：" + rs.getInt("id") + '\n' + "订单金额：" + rs.getDouble("totalprice"));
                    String date = "";
                    date = rs.getString("orderdate");
                    System.out.println("下单时间" + date);
                    System.out.println("--------------------");
                }
            } else {
                System.out.println("系统中没有该人的名字！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param id 要查询的订单信息
     */
    public static int retrieveOrder(int id){
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
            if (rs.next()) {
                System.out.println("编号为" + id + "的订单信息：");
                int itemId = rs.getInt("itemid");
                String itemName = ItemEdit.retrieveItem(itemId).getName();
                System.out.println("商品名称：" + itemName + '\n' + "购买数量：" + rs.getInt("amount"));
            } else {
                throw new ObjectNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.showError();
            return 1;
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return 0;
    }

    /**
     * 删除订单
     *
     * @param name 要查询的名字
     */
    public static void deleteOrder(String name){
        retrieveOrder(name);
        Connection con = null;
        PreparedStatement st = null;
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
            con.commit();
            System.out.println("删除成功");
        } catch (SQLException e) {
            System.out.println("系统中没有该人的名字！");
            e.printStackTrace();
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /**
     * 更新订单信息
     *
     * @param name 下单人的名字
     */
    public static void updateOrder(String name){

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            int status = retrieveOrder(name);
            if (status == 1) {
                throw new ObjectNotFoundException();
            }
            //输入要修改的订单编号并获取订单的详细信息
            System.out.println("输入要更新的订单编号：");
            Scanner scanner = new Scanner(System.in);
            int orderId = scanner.nextInt();
            status = retrieveOrder(orderId);
            if (status == 1) {
                throw new ObjectNotFoundException();
            }
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql;
            while (true) {
                System.out.println("输入要修改的商品名称，按q退出");
                String itemName = scanner.next();
                if ("q".equals(itemName)) {
                    break;
                }
                int itemId = ItemEdit.retrieveItem(itemName).getId();
                double itemPrice = ItemEdit.retrieveItem(itemName).getPrice();
                double changed = 0;
                sql = "SELECT amount FROM orderitem WHERE orderid=? AND itemid=?";
                st = con.prepareStatement(sql);
                st.setInt(1, orderId);
                st.setInt(2, itemId);
                rs = st.executeQuery();
                // 如果存在则修改
                if (rs.next()) {
                    int originalAmount = rs.getInt("amount");
                    System.out.println("输入要修改的数量，0则删除该商品");
                    int amount = scanner.nextInt();
                    // 校验输入的数量是否合法
                    while (amount < 0) {
                        System.out.println("数量不合法！请重新输入");
                        amount = scanner.nextInt();
                    }
                    // 0则删除商品
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
                    changed = (amount - originalAmount) * itemPrice;
                } else {
                    // 不存在则添加
                    System.out.println("输入要购买的数量");
                    int amount = scanner.nextInt();
                    // 校验输入的数量是否合法
                    while (amount <= 0) {
                        System.out.println("数量不合法！请重新输入");
                        amount = scanner.nextInt();
                    }
                    sql = "INSERT INTO orderitem (orderid, itemid, amount) VALUES (?,?,?)";
                    st = con.prepareStatement(sql);
                    st.setInt(1, orderId);
                    st.setInt(2, itemId);
                    st.setInt(3, amount);
                    st.executeUpdate();
                    changed = amount * itemPrice;
                }
                // 计算变化的价格，更新订单信息
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
            // 检测商品中是否有商品，若无则删除该订单
            if (!rs.next()) {
                System.out.println("订单中已无商品，自动删除订单");
                sql = "DELETE FROM orderinfo WHERE id = ?";
                st = con.prepareStatement(sql);
                st.setInt(1, orderId);
                st.executeUpdate();
            }
            con.commit();//test
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.showError();
        } finally {
            JDBCUtils.release(con, st, rs);
        }
    }
}
