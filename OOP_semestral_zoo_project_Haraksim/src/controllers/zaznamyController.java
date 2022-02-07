package controllers;

import entities.Animal;
import entities.Caretaker;
import entities.SuperVisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class zaznamyController extends homepageController implements Initializable {

    @FXML
    Button backBtn;
    @FXML
    TableView<Animal.Zaznam> recordTable;
    @FXML
    TableColumn<Animal.Zaznam, String> dateRecordColumn;
    @FXML
    TableColumn<Animal.Zaznam, String> authorRecordColumn;
    @FXML
    TableColumn<Animal.Zaznam, String> recordColumn;
    @FXML
    Button addRecordBtn;
    @FXML
    Button delRecordBtn;


    public void backButton() throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../views/homepage.fxml"));

        Stage window = (Stage) backBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private ObservableList<Animal.Zaznam> recordsList = FXCollections.observableArrayList();

    public void fillRecordsTable(){
        int size=0;
        System.out.println(indexAnimal);
        System.out.println(indexSS);

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();
        String sqlQuery = "SELECT * FROM zaznamy INNER JOIN clovek ON zaznamy.id_clovek = clovek.id_clovek WHERE id_zviera = ?";
        String countQuery = "SELECT Count(*) FROM zaznamy WHERE id_zviera = ?";

        for ( int i = 0; i<recordTable.getItems().size(); i++) {
            recordTable.getItems().clear();
        }

        PreparedStatement preparedQuery, preparedCountStatement;
        recordsList = FXCollections.observableArrayList();
        try {
            preparedCountStatement = connectDB.prepareStatement(countQuery);
            preparedCountStatement.setInt(1, id_zviera);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if(resultSetSize.next())
                size = resultSetSize.getInt(1);

            preparedQuery = connectDB.prepareStatement(sqlQuery);
            preparedQuery.setInt(1, id_zviera);
            ResultSet data = preparedQuery.executeQuery();
            data.next();

            for (int i = 0; i < size; i++) {

                recordsList.add(new Animal.Zaznam(data.getString(3),data.getString(2),data.getString(7),data.getString(8),data.getInt(1)));
                data.next();
            }

            insertIntoRecordsTable("");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoRecordsTable(String filter){
        ObservableList<Animal.Zaznam> recordsFilteredList = FXCollections.observableArrayList();
        recordsFilteredList.addAll(0, recordsList);


        dateRecordColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        authorRecordColumn.setCellValueFactory(new PropertyValueFactory<>("odosielatel"));
        recordColumn.setCellValueFactory(new PropertyValueFactory<>("sprava"));



        if (recordsFilteredList.size() == 0) {
            recordsFilteredList.add(null);
        }
        recordTable.setItems(recordsFilteredList);
        recordTable.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void submitRecord() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/addRecord.fxml"));

        Stage window = (Stage) addRecordBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    };

    public void deleteRecord(){
        Animal.Zaznam selectedItem;
        selectedItem = recordTable.getSelectionModel().getSelectedItem();

        if(pozicia == 1){
            Caretaker.deleteRecords(selectedItem, recordTable);
        }
        else if(pozicia == 2){
            SuperVisor.deleteRecords(selectedItem, recordTable);
        }
    }

}
