package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class messagesController extends homepageEmployeeController{
    @FXML
    Button sendMessage;
    @FXML
    TextArea messageText;
    @FXML
    TextField messageDate;
    @FXML
    Button messageBackBtn;

    public void messageBack() throws Exception{
        if (pozicia == 2) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

            Stage window = (Stage) messageBackBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        } else if (pozicia == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageCaretaker.fxml"));

            Stage window = (Stage) messageBackBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        } else if (pozicia == 0) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageEmployee.fxml"));

            Stage window = (Stage) messageBackBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }

    public void sendMessage() throws Exception{
        if(pozicia==2){
            superVisor.sendMessage(messageDate,messageText,indexSS);
        }
        else if(pozicia==1){
            caretaker.sendMessage(messageDate,messageText,indexSS);
        }
        else if(pozicia==0){
            employee.sendMessage(messageDate,messageText,indexSS);
        }
    }
}
