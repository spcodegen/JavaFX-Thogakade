package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{

    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {

                customerArrayList.add(new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerArrayList;
    }

    public ObservableList<String> getCustomerIds(){
        List<Customer> customerList = getAll();
        ObservableList<String> custoObservableList = FXCollections.observableArrayList();

        customerList.forEach(customer -> {
            custoObservableList.add(customer.getId());
        });
        return custoObservableList;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }
}
