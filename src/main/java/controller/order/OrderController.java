package controller.order;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order){
        String SQL ="INSERT INTO orders VALUES(?,?,?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,order.getId());
            psTm.setObject(2,order.getDate());
            psTm.setObject(1,order.getCustomerId());
            boolean isOrderAdd = psTm.executeUpdate() > 0;

            if (isOrderAdd){
                boolean addOrderDetail = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                System.out.println(addOrderDetail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
