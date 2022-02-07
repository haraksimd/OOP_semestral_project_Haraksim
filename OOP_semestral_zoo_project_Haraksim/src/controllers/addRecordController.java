package controllers;

import entities.Caretaker;
import entities.SuperVisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class addRecordController extends zaznamyController{

    @FXML
    Button addRecordBtn;
    @FXML
    Button backRecordBtn;
    @FXML
    TextField recordsDate;
    @FXML
    TextArea recordsMessage;

    public void addRecord(){
        if(pozicia==2){
            SuperVisor.insertRecords(recordsDate, recordsMessage, indexSS,indexAnimal);
        }
        else if(pozicia==1){
            Caretaker.insertRecords(recordsDate, recordsMessage, indexSS,indexAnimal);
        }
    }

    public void backRecord() throws IOException {
        if(pozicia==2) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

            Stage window = (Stage) backRecordBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        else if(pozicia==1){
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageCaretaker.fxml"));

            Stage window = (Stage) backRecordBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }
}
