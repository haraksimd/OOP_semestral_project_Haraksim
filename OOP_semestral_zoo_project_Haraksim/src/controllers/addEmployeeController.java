package controllers;

import entities.WrongPosInput;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addEmployeeController extends loginController{

    @FXML
    Button addEmployeeBtn;
    @FXML
    TextField nameEmployeeText;
    @FXML
    TextField surnameEmployeeText;
    @FXML
    TextField sectorEmployeeText;
    @FXML
    TextField phoneEmployeeText;
    @FXML
    TextField posEmployeeText;
    @FXML
    TextField userEmployeeText;
    @FXML
    TextField passEmployeeText;
    @FXML
    Button backEmployeeBtn;
    @FXML
    Label WrongPos;

    public void addNewEmployee() throws WrongPosInput {
        superVisor.insert(nameEmployeeText,surnameEmployeeText,sectorEmployeeText,phoneEmployeeText,userEmployeeText,passEmployeeText,posEmployeeText,WrongPos);
    }

    public void backEmployeeBtn() throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

            Stage window = (Stage) backEmployeeBtn.getScene().getWindow();
            window.setScene(new Scene(root));
    }

}
