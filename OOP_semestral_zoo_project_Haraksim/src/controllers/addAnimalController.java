package controllers;

import entities.Caretaker;
import entities.SuperVisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class addAnimalController extends loginController{
    @FXML
    Button addAnimalBtn;
    @FXML
    TextField nameAnimalText;
    @FXML
    TextField speciesAnimalText;
    @FXML
    TextField sectionAnimalText;
    @FXML
    Button backAnimalBtn;

    public void backBtn() throws Exception {
        if(pozicia==2) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

            Stage window = (Stage) backAnimalBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        else if(pozicia==1){
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageCaretaker.fxml"));

            Stage window = (Stage) backAnimalBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }

    public void addAnimal() throws Exception{
        if(pozicia==2){
            SuperVisor.insert(nameAnimalText,speciesAnimalText,sectionAnimalText);
        }
        else if(pozicia==1){
            Caretaker.insert(nameAnimalText,speciesAnimalText,sectionAnimalText);
        }
    }
}
