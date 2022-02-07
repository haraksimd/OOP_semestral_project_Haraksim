package entities;

import javafx.scene.control.*;
import controllers.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SuperVisor extends Caretaker {

    public SuperVisor(String meno, String priezvisko, String oddelenie, String cislo, int ID) {
        super(meno,priezvisko,oddelenie,cislo,ID);
    }

    public SuperVisor(){

    }

    public void insert(TextField nameEmployeeText, TextField surnameEmployeeText, TextField sectorEmployeeText, TextField phoneEmployeeText, TextField userEmployeeText, TextField passEmployeeText, TextField posEmployeeText, Label WrongPos) throws WrongPosInput {
        //funkcia na pridavanie ludi
        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String name = nameEmployeeText.getText();
        String surname = surnameEmployeeText.getText();
        String sector = sectorEmployeeText.getText();
        String phoneNumber = phoneEmployeeText.getText();
        String username = userEmployeeText.getText();
        String password = passEmployeeText.getText();
        int position = Integer.parseInt(posEmployeeText.getText());
        if (position > 2 || position < 0) {
            throw new WrongPosInput("Wrong position number");
        }

        try{
            String insertQuery = "INSERT INTO clovek(meno, priezvisko, oddelenie, cislo, login, heslo, pozicia) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement insert = connectDB.prepareStatement(insertQuery);
            insert.setString(1,name);
            insert.setString(2,surname);
            insert.setString(3,sector);
            insert.setString(4,phoneNumber);
            insert.setString(5,username);
            insert.setString(6,password);
            insert.setInt(7,position);
            insert.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Employee selectedItem, String selectedName, TableView<Employee> employeesTable){ //vymazanie cloveka

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        try {

            String deleteQuery = "DELETE FROM clovek WHERE meno = ?";
            PreparedStatement preparedStatementToDelete = connectDB.prepareStatement(deleteQuery);
            preparedStatementToDelete.setString(1, selectedName);
            preparedStatementToDelete.executeUpdate();

            employeesTable.getItems().remove(selectedItem);
            employeesTable.refresh();
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void submitNewTask(TextField dateTaskTextField, TextArea taskTextArea, int indexSS){ //pridavanie uloh zamestnancom
        String task=taskTextArea.getText();
        String date=dateTaskTextField.getText();



        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String insertQuery = "INSERT INTO todolist(uloha,datum,finished,id_clovekautor,id_clovekkomu) VALUES(?,?,'no',?,?)";

        try{
            PreparedStatement insertStatemenet = connectDB.prepareStatement(insertQuery);
            insertStatemenet.setString(1,task);
            insertStatemenet.setString(2,date);
            insertStatemenet.setInt(3,this.getID());
            insertStatemenet.setInt(4,indexSS);
            insertStatemenet.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


}
