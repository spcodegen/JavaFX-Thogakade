package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private TextField txtCPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String SQL = "INSERT INTO users (username,email,password) VALUES(?,?,?)";
        String key= "#7535San";

        if (txtPassword.getText().equals(txtCPassword.getText())) {

            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'");

            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(key);

            if (!resultSet.next()) {
                User user = new User(txtUserName.getText(), txtEmail.getText(), txtPassword.getText());

                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, user.getUserName());
                psTm.setString(2, user.getUserEmail());
                psTm.setString(3, basicTextEncryptor.encrypt(user.getUserPassword()));

                psTm.executeUpdate();

            }else {
                new Alert(Alert.AlertType.ERROR, "User found!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Check your Password!!!").show();
        }
    }

}
