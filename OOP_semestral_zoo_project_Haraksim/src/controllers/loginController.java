package controllers;

import entities.Caretaker;
import entities.Employee;
import entities.SuperVisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginController extends Main{

    protected static Caretaker caretaker;
    protected static Employee employee;
    protected static SuperVisor superVisor;

    public static String meno;
    private String heslo;
    protected static int pozicia;

    @FXML
    Button loginButton;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label wrongPass;

    public loginController() {

    }


    public void initialize() {
    }

    @FXML
    public void textAction(KeyEvent e){
        passwordField.setOnKeyPressed( event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    pressButton();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }




    private boolean correctPass(String meno, String heslo){
        boolean pass = false;
        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT * FROM clovek WHERE login = ? AND heslo = ?";

        try{
            PreparedStatement statement = connectDB.prepareStatement(connectQuery);
            statement.setString(1, meno);
            statement.setString(2, heslo);
            ResultSet queryOutput = statement.executeQuery();

            while (queryOutput.next()){
                pozicia = queryOutput.getInt(8);
                switch(pozicia){
                    case 2: superVisor = new SuperVisor(queryOutput.getString(2),queryOutput.getString(3),queryOutput.getString(4),queryOutput.getString(5),queryOutput.getInt(1));
                            break;
                    case 1: caretaker = new Caretaker(queryOutput.getString(2),queryOutput.getString(3),queryOutput.getString(4),queryOutput.getString(5),queryOutput.getInt(1));
                            break;
                    case 0: employee = new Employee(queryOutput.getString(2),queryOutput.getString(3),queryOutput.getString(4),queryOutput.getString(5),queryOutput.getInt(1));
                            break;
                }
                pass = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return pass;
    }




    public void pressButton() throws Exception{
        meno = usernameField.getText();
        heslo = passwordField.getText();

        if(correctPass(meno, heslo)) {
            if(pozicia == 2) {
                Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

                Stage window = (Stage) loginButton.getScene().getWindow();
                window.setScene(new Scene(root));
            }
            else if(pozicia == 1){
                Parent root = FXMLLoader.load(getClass().getResource("../views/homepageCaretaker.fxml"));

                Stage window = (Stage) loginButton.getScene().getWindow();
                window.setScene(new Scene(root));
            }
            else{
                Parent root = FXMLLoader.load(getClass().getResource("../views/homepageEmployee.fxml"));

                Stage window = (Stage) loginButton.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
        else wrongPass.setText("Wrong username/password, try again.");
    }

}
