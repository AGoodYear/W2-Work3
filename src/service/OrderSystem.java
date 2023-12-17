package service;

import dao.ItemEdit;
import dao.OrderEdit;

import java.util.Scanner;

public class OrderSystem {
    public static void selectFunc() {
        System.out.println("商品相关请按1， 订单相关请按2");
        Scanner scanner = new Scanner(System.in);
        int selected = scanner.nextInt();
        if (selected == 1) {
            manageItem();
        } else if (selected == 2) {
            manageOrder();
        }
    }

    public static void manageItem() {
        System.out.println("请选择要使用的功能：\n1.增加商品\n2.查询商品\n3.修改商品\n4.退出");
        Scanner scanner = new Scanner(System.in);
        int selected = scanner.nextInt();
        if (selected == 1) {
            System.out.println("输入商品名称和价格");
            String itemName;
            double itemPrice;
            itemName = scanner.next();
            itemPrice = scanner.nextInt();
            ItemEdit.createItem(itemName, itemPrice);
            manageItem();
        } else if (selected == 2) {
            System.out.println("输入要查询的商品名称：");
            String itemName = scanner.next();
            double itemPrice = ItemEdit.retrieveItem(itemName);
            int id = ItemEdit.retrieveItemId(itemName);
            System.out.println("查询成功！\n商品编号：" + id + "\n商品名称：" + itemName + "\n商品价格：" + itemPrice);
            manageItem();
        } else if (selected == 3) {
            ItemEdit.listItem();
            System.out.println("输入要修改的商品名和价格");
            String itemName;
            double itemPrice;
            itemName = scanner.next();
            itemPrice = scanner.nextInt();
            ItemEdit.updateItem(itemName, itemPrice);
            manageItem();
        }
    }

    public static void manageOrder() {
        System.out.println("请选择要使用的功能：\n1.创建订单\n2.查询订单\n3.修改订单\n4.退出");
        Scanner scanner = new Scanner(System.in);
        int selected = scanner.nextInt();
        if (selected == 1) {
            String name;
            System.out.println("输入创建者的名字");
            name = scanner.next();
            OrderEdit.createOrder(name);
        } else if (selected == 2) {
            String name;
            System.out.println("输入创建者的名字");
            name = scanner.next();
            System.out.println("输入订单排序方式：\n1.默认\n2.按价格排序\n3.按创建时间排序");
            int sortBy = scanner.nextInt();
            if (sortBy == 1) {
                OrderEdit.retrieveOrder(name);
            } else if (sortBy == 2) {
                OrderEdit.retrieveOrderByPrice(name);
            } else if (sortBy == 3) {
                OrderEdit.retrieveOrderByDate(name);
            }
        } else if (selected == 3) {
            String name;
            System.out.println("输入创建者的名字");
            name = scanner.next();
            OrderEdit.updateOrder(name);
        }
    }
}
