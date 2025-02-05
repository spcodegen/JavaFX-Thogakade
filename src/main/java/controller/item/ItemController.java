package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService {
    @Override
    public boolean addItem() {
        return false;
    }

    @Override
    public boolean updateItem() {
        return false;
    }

    @Override
    public boolean deleteItem() {
        return false;
    }

    @Override
    public List<Item> getAll() {
        List<Item> itemList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM item");
            while (resultSet.next()) {
                itemList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String code) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM item WHERE code=" + "'" + code + "'");
            resultSet.next();
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getItemCodes() {
        ObservableList<String> itemObservableList = FXCollections.observableArrayList();
        List<Item> itemList = getAll();
        itemList.forEach(item -> {
            itemObservableList.add(item.getCode());
        });
        return itemObservableList;
    }


    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isUpdateStock = updateStock(orderDetail);
            if (!isUpdateStock) {
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDetail orderDetail) {
        String SQL = "UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, orderDetail.getQty());
            psTm.setObject(2, orderDetail.getItemCode());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
