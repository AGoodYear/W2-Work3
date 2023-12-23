import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.ItemEdit;
import dao.OrderEdit;
import pojo.Item;
import service.OrderSystem;

public class Test {
    public static void main(String[] args) {
        ItemEdit.listItem();
        ItemEdit.createItem("测试1", 10);
        ItemEdit.createItem("测试1", 10);
        ItemEdit.retrieveItem("测试1");
        ItemEdit.updateItem("测试1", 100);
        ItemEdit.deleteItem("测试1");
        ItemEdit.listItem();

        OrderEdit.createOrder("米诺");
        OrderEdit.updateOrder("米诺");
        OrderEdit.retrieveOrder("米诺");
        OrderEdit.deleteOrder("米诺");
        OrderEdit.retrieveOrderByDate("米诺");
        OrderEdit.retrieveOrderByPrice("米诺");
    }
}
