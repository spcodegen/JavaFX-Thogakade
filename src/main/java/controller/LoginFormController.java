package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginFormController {

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String SQL = "SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'";
        String key = "#7535San";

        //DBConnection Object
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);

        //Java Simplified Encryption Object
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        if (resultSet.next()) {
            User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));


            if (basicTextEncryptor.decrypt(user.getUserPassword()).equals(txtPassword.getText())) {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid password").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Found!!!").show();
        }
    }

    @FXML
    void hyperRegisterOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/register_form.fxml"))));
        stage.show();
    }

}
