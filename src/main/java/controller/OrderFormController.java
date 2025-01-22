package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
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
import javafx.util.Duration;
import model.Customer;


import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTimeAndTime();
        loadCustomerIds();
    }

    @FXML
    void btnAddToCardOnAction(ActionEvent event) {

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
                    lblTime.setText(now.getHour()+":"+ now.getMinute()+":"+ now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void loadCustomerIds(){
        ObservableList<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerId.setItems(customerIds);
    }



}
