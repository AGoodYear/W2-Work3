package dao;

/**
 * 异常
 *
 * @author Aurora
 */
public class ObjectNotFoundException extends Exception{

    public void showError() {
        System.out.println("系统中没有此对象！");
    }

}
