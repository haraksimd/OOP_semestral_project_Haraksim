package entities;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import controllers.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Caretaker extends Employee{

    public Caretaker(){

    };

    public Caretaker(String meno, String priezvisko, String oddelenie, String cislo, int ID){
        super(meno, priezvisko,oddelenie,cislo,ID);
    }

    public void delete(Animal selectedItem, String selectedName, TableView<Animal> animalListTable){  //zmazanie zvierata
        DbConnection connectNow = new DbConnection();       //pripojenie na db
        Connection connectDB = connectNow.getConnection();

        try {   // vymazavanie z db cez sql
            String deleteQuery1 = "DELETE FROM zaznamy WHERE id_zviera = ?";
            PreparedStatement preparedStatementToDelete1 = connectDB.prepareStatement(deleteQuery1);
            preparedStatementToDelete1.setInt(1, selectedItem.getID());
            preparedStatementToDelete1.executeUpdate();


            String deleteQuery = "DELETE FROM zviera WHERE meno = ?";
            PreparedStatement preparedStatementToDelete = connectDB.prepareStatement(deleteQuery);
            preparedStatementToDelete.setString(1, selectedName);
            preparedStatementToDelete.executeUpdate();

            animalListTable.getItems().remove(selectedItem);
            animalListTable.refresh();
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(TextField nameAnimalText, TextField speciesAnimalText, TextField sectionAnimalText){ //pridanie zvierata
        String name = nameAnimalText.getText();                //ziskavanie textu z textovych poli
        String species = speciesAnimalText.getText();
        String section = sectionAnimalText.getText();

        DbConnection connectNow = new DbConnection();           //pripojenie na db
        Connection connectDB = connectNow.getConnection();


        try{                //sql prikaz na vkladanie do db
            String insertQuery = "INSERT INTO zviera(meno, druh, sekcia) VALUES(?,?,?)";
            PreparedStatement insert = connectDB.prepareStatement(insertQuery);
            insert.setString(1,name);
            insert.setString(2,species);
            insert.setString(3,section);
            insert.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteRecords(Animal.Zaznam selectedItem, TableView<Animal.Zaznam> recordsTable){  //vymazanie zaznamov
        DbConnection connectNow = new DbConnection();       //pripojenie na db
        Connection connectDB = connectNow.getConnection();

        try{                //vymazanie z db cez sql
            String deleteQuery = "DELETE FROM zaznamy WHERE id_zaznam = ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, selectedItem.getID());
            preparedStatement.executeUpdate();

            recordsTable.getItems().remove(selectedItem);
            recordsTable.refresh();
            connectDB.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void insertRecords(TextField recordsDate, TextArea recordsMessage, int indexSS, int indexAnimal){ //pridavanie zaznamov
        DbConnection connectNow = new DbConnection();                   //pripojenie do db
        Connection connectDB = connectNow.getConnection();

        String date = recordsDate.getText();                            //vyberanie textu z textovych poli
        String record = recordsMessage.getText();

        try{                //sql insert prikaz na pridanie do db
            String insertQuery = "INSERT INTO zaznamy(datum,sprava,id_clovek,id_zviera) VALUES (?,?,?,?)";
            PreparedStatement insert = connectDB.prepareStatement(insertQuery);
            insert.setString(1, date);
            insert.setString(2, record);
            insert.setInt(3, indexSS);
            insert.setInt(4, indexAnimal);
            insert.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    };



}
