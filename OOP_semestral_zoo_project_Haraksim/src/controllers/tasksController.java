package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class tasksController extends homepageController{
    @FXML
    Button submitTaskBtn;
    @FXML
    Button backTaskBtn;
    @FXML
    TextArea taskTextArea;
    @FXML
    TextField dateTaskTextField;

    protected static int id_clovekautor=0;

    public void backTaskButton() throws Exception{
        if(pozicia==2) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

            Stage window = (Stage) backTaskBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        else if(pozicia==1){
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageCaretaker.fxml"));

            Stage window = (Stage) backTaskBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        else if(pozicia==0){
            Parent root = FXMLLoader.load(getClass().getResource("../views/homepageSuperVisor.fxml"));

            Stage window = (Stage) backTaskBtn.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }

    public void submitNewTask(){


        if(pozicia==0) {
            employee.submitNewTask(dateTaskTextField, taskTextArea, employee.getID());
        }
        else if(pozicia==1){
            caretaker.submitNewTask(dateTaskTextField, taskTextArea, caretaker.getID());
        }
        else superVisor.submitNewTask(dateTaskTextField, taskTextArea, indexSS);
    }
}
