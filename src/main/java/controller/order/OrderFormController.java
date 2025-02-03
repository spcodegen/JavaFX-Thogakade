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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;


import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

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
    private JFXTextField lblOrderId;

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

    ObservableList<CartTM> cardObservableList = FXCollections.observableArrayList();

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

    private void loadItemCodes()  {
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

        cardObservableList.add(new CartTM(code, description, qtyOnHand, unitPrice, total));

        tblCart.setItems(cardObservableList);
        calcNetTotal();
    }

    private void calcNetTotal() {
        Double netTotal = 0.0;

        for (CartTM tm : cardObservableList) {
            netTotal += tm.getTotal();
        }

        lblNetTotal.setText(netTotal.toString());
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) { 

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
