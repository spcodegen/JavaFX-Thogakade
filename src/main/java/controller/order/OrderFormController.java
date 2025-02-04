package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.Item;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;
import model.Order;
import model.OrderDetail;


import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public JFXTextField txtOrderId;
    @FXML
    private JFXComboBox cmbCustomerId;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQtyOnHand;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView tblCart;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtStock;

    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        getTimeAndTime();
        loadCustomerIds();
        loadItemCodes();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchCustomerData(newValue.toString());
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchItemData(newValue.toString());
            }
        });
    }

    private void searchItemData(String itemCode) {
        Item item = new ItemController().searchItem(itemCode);
        txtDescription.setText(item.getDescription());
        txtStock.setText(item.getStock().toString());
        txtPrice.setText(item.getUnitPrice().toString());
    }

    private void loadItemCodes() {
        cmbItemCode.setItems(new ItemController().getItemCodes());
    }

    private void searchCustomerData(String customerId) {
        Customer customer = new CustomerController().searchCustomer(customerId);
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
    }

    @FXML
    void btnAddToCardOnAction(ActionEvent event) {

        String code = cmbItemCode.getValue().toString();
        String description = txtDescription.getText();
        Double unitPrice = Double.parseDouble(txtPrice.getText());
        Integer qtyOnHand = Integer.parseInt(txtQty.getText());
        Double total = unitPrice * qtyOnHand;

        cartTMS.add(new CartTM(code, description, qtyOnHand, unitPrice, total));

        tblCart.setItems(cartTMS);
        calcNetTotal();
    }

    private void calcNetTotal() {
        Double netTotal = 0.0;

        for (CartTM tm : cartTMS) {
            netTotal += tm.getTotal();
        }

        lblNetTotal.setText(netTotal.toString());
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText();
        String date = lblDate.getText();
        String customerId = cmbCustomerId.getValue().toString();

        List<OrderDetail> orderDetails = new ArrayList<>();

        cartTMS.forEach(cartTM -> {
            orderDetails.add(new OrderDetail(
                    orderId,
                    cartTM.getItemCode(),
                    cartTM.getQtyOnHand(),
                    cartTM.getUnitPrice()
            ));
        });

        Order order = new Order(orderId, date, customerId, orderDetails);

        if (new OrderController().placeOrder(order)){
            new Alert(Alert.AlertType.INFORMATION,"Order Placed!!!");
        }else {
            new Alert(Alert.AlertType.ERROR,"Order Not Placed!!!");
        }

    }

    private void getTimeAndTime() {

//-------------------------DATE---------------------------------------
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String format = dateFormat.format(date);
        lblDate.setText(format);

//--------------------------TIME----------------------------------------

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    lblTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void loadCustomerIds() {
        ObservableList<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerId.setItems(customerIds);
    }
}
