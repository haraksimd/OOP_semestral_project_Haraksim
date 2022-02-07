package controllers;

import entities.Animal;
import entities.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class homepageEmployeeController extends homepageController {

    //animal table
    @FXML
    Tab animalListTab;
    @FXML
    TableView<Animal> animalListTable;
    @FXML
    TableColumn<Animal, String> animalNameColumn;
    @FXML
    TableColumn<Animal, String> animalSpeciesColumn;
    @FXML
    TableColumn<Animal, String> animalSectionColumn;
    //employee table
    @FXML
    Tab employeesTab;
    @FXML
    TableColumn<Employee, String> nameColumn;
    @FXML
    TableColumn<Employee, String> surnameColumn;
    @FXML
    TableColumn<Employee, String> departmentColumn;
    @FXML
    TableColumn<Employee, String> phoneColumn;
    @FXML
    TableView<Employee> employeesTable;
    //deleteEmployee
    @FXML
    Button deleteEmployee;
    //recordsAnimals
    @FXML
    Button recordsButton;
    //todoList
    @FXML
    TableView<Employee.ToDoList> toDoTable;
    @FXML
    TableColumn<Employee.ToDoList, String> tasksColumn;
    @FXML
    TableColumn<Employee.ToDoList, String> dateTasksColumn;
    @FXML
    TableColumn<Employee.ToDoList, String> finishedColumn;
    @FXML
    TableColumn<Employee.ToDoList, String> authorTasksColumn;
    @FXML
    Button addTaskBtn;
    @FXML
    Button addMessageBtn;
    @FXML
    ChoiceBox<String> messageList;
    @FXML
    TableView<Employee.Messages> messageTable;
    @FXML
    TableColumn<Employee, String> messageTextColumn;
    @FXML
    TableColumn<Employee, String> messageTimeColumn;
    @FXML
    TableColumn<Employee, String> messageFromColumn;


    //Employees Table
    private ObservableList<Employee> employeesList = FXCollections.observableArrayList();
    private int size;

    public void fillEmployeesTable(){

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();
        String sqlQuery = "SELECT * FROM clovek";
        String countQuery = "SELECT Count(*) FROM clovek";



        PreparedStatement preparedQuery, preparedCountStatement;
        employeesList = FXCollections.observableArrayList();
        try {
            preparedCountStatement = connectDB.prepareStatement(countQuery);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if(resultSetSize.next())
                size = resultSetSize.getInt(1);
            preparedQuery = connectDB.prepareStatement(sqlQuery);
            ResultSet data = preparedQuery.executeQuery();
            data.next();
            for (int i = 0; i < size; i++) {
                employeesList.add(new Employee(data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getInt(1)));
                data.next();
            }
            insertIntoEmployeesTable("");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoEmployeesTable(String filter) {
        ObservableList<Employee> employeesFilteredList = FXCollections.observableArrayList();
        employeesFilteredList.addAll(0, employeesList);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("meno"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("oddelenie"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("cislo"));


        if (employeesFilteredList.size() == 0) {
            employeesFilteredList.add(null);
        }
        employeesTable.setItems(employeesFilteredList);
        employeesTable.refresh();
    }

    //Animals table
    private ObservableList<Animal> animalsList = FXCollections.observableArrayList();

    public void fillAnimalsTable(){
        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();
        String sqlQuery = "SELECT * FROM zviera";
        String countQuery = "SELECT Count(*) FROM zviera";

        for ( int i = 0; i<animalListTable.getItems().size(); i++) {
            animalListTable.getItems().clear();
        }

        PreparedStatement preparedQuery, preparedCountStatement;
        animalsList = FXCollections.observableArrayList();
        try {
            preparedCountStatement = connectDB.prepareStatement(countQuery);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if(resultSetSize.next())
                size = resultSetSize.getInt(1);
            preparedQuery = connectDB.prepareStatement(sqlQuery);
            ResultSet data = preparedQuery.executeQuery();
            data.next();
            for (int i = 0; i < size; i++) {
                animalsList.add(new Animal(data.getInt(1),data.getString(2),data.getString(3),data.getString(4)));
                data.next();
            }
            insertIntoAnimalsTable("");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoAnimalsTable(String filter){
        ObservableList<Animal> animalsFilteredList = FXCollections.observableArrayList();
        animalsFilteredList.addAll(0, animalsList);


        animalNameColumn.setCellValueFactory(new PropertyValueFactory<>("meno"));
        animalSpeciesColumn.setCellValueFactory(new PropertyValueFactory<>("druh"));
        animalSectionColumn.setCellValueFactory(new PropertyValueFactory<>("sektor"));



        if (animalsFilteredList.size() == 0) {
            animalsFilteredList.add(null);
        }
        animalListTable.setItems(animalsFilteredList);
        animalListTable.refresh();
    }

    //Delete employee
    public void deleteEmployee(){
        String selectedName;
        Employee selectedItem;
        selectedName = employeesTable.getSelectionModel().getSelectedItem().getMeno();
        selectedItem = employeesTable.getSelectionModel().getSelectedItem();
        superVisor.delete(selectedItem, selectedName, employeesTable);
    }

    //Delete animal
    public void deleteAnimal(){
        String selectedName;
        Animal selectedItem;


        selectedName = animalListTable.getSelectionModel().getSelectedItem().getMeno();
        selectedItem = animalListTable.getSelectionModel().getSelectedItem();

        if(pozicia == 2) {
            superVisor.delete(selectedItem, selectedName, animalListTable);
        }
        if(pozicia == 1) {
            caretaker.delete(selectedItem, selectedName, animalListTable);
        }
    }

    //Open Animal records
    protected static  int id_zviera = 0;

    public void openRecords(){
        String selectedName = animalListTable.getSelectionModel().getSelectedItem().getMeno();;
        Animal selectedItem = animalListTable.getSelectionModel().getSelectedItem();;

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT id_zviera FROM zviera WHERE meno = ?";


        try{
            PreparedStatement statement = connectDB.prepareStatement(connectQuery);
            statement.setString(1, selectedName);
            ResultSet queryOutput = statement.executeQuery();
            while (queryOutput.next()){
                id_zviera = queryOutput.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/zaznamy.fxml"));

            Stage window = (Stage) recordsButton.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //To Do list
    protected static int id_clovek = 0;
    private ObservableList<Employee.ToDoList> toDoList = FXCollections.observableArrayList();

    public void fillToDoTable(){
        int size=0;

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String sqlQuery = "SELECT * FROM todolist INNER JOIN clovek ON todolist.id_clovekautor = clovek.id_clovek WHERE id_clovekkomu = ?";
        String countQuery = "SELECT Count(*) FROM todolist WHERE id_clovekkomu = ?";
        String connectQuery = "SELECT id_clovek FROM clovek WHERE login = ?";

        for ( int i = 0; i<toDoTable.getItems().size(); i++) {
            toDoTable.getItems().clear();
        }

        PreparedStatement preparedQuery, preparedCountStatement;
        toDoList = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = connectDB.prepareStatement(connectQuery);
            statement.setString(1, meno);
            ResultSet queryOutput = statement.executeQuery();
            while (queryOutput.next()){
                id_clovek = queryOutput.getInt(1);
            }


            preparedCountStatement = connectDB.prepareStatement(countQuery);
            preparedCountStatement.setInt(1, id_clovek);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if(resultSetSize.next())
                size = resultSetSize.getInt(1);

            preparedQuery = connectDB.prepareStatement(sqlQuery);
            preparedQuery.setInt(1, id_clovek);
            ResultSet data = preparedQuery.executeQuery();
            data.next();

            for (int i = 0; i < size; i++) {

                toDoList.add(new Employee.ToDoList(data.getString(2),data.getString(3),data.getString(4),data.getString(8),data.getString(9)));
                data.next();
            }

            insertIntoToDoTable("");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoToDoTable(String filter){
        ObservableList<Employee.ToDoList> toDoFilteredList = FXCollections.observableArrayList();
        toDoFilteredList.addAll(0, toDoList);


        tasksColumn.setCellValueFactory(new PropertyValueFactory<>("sprava"));
        authorTasksColumn.setCellValueFactory(new PropertyValueFactory<>("odosielatel"));
        dateTasksColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        finishedColumn.setCellValueFactory(new PropertyValueFactory<>("finished"));



        if (toDoFilteredList.size() == 0) {
            toDoFilteredList.add(null);
        }
        toDoTable.setItems(toDoFilteredList);
        toDoTable.refresh();
    }

    public void addTaskBtn() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/tasks.fxml"));

        Stage window = (Stage) addTaskBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void addMessage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/messages.fxml"));

        Stage window = (Stage) addMessageBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    protected static int indexSS=0;
    private ObservableList<Employee.Messages> messagesList = FXCollections.observableArrayList();

    public void fillMessageTable() {
        int size = 0;

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String sqlQuery = "SELECT * FROM spravy INNER JOIN clovek ON spravy.id_cloveko = clovek.id_clovek WHERE id_clovekp = ?";
        String countQuery = "SELECT Count(*) FROM spravy WHERE id_clovekp = ?";
        String connectQuery = "SELECT id_clovek FROM clovek WHERE login = ?";


        for (int i = 0; i < messageTable.getItems().size(); i++) {
            messageTable.getItems().clear();
        }

        PreparedStatement preparedQuery, preparedCountStatement;
        messagesList = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = connectDB.prepareStatement(connectQuery);
            statement.setString(1, meno);
            ResultSet queryOutput = statement.executeQuery();
            while (queryOutput.next()){
                id_clovek = queryOutput.getInt(1);
            }
            System.out.println(id_clovek);

            preparedCountStatement = connectDB.prepareStatement(countQuery);
            preparedCountStatement.setInt(1, id_clovek);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if (resultSetSize.next())
                size = resultSetSize.getInt(1);

            preparedQuery = connectDB.prepareStatement(sqlQuery);
            preparedQuery.setInt(1, id_clovek);
            ResultSet data = preparedQuery.executeQuery();
            data.next();

            for (int i = 0; i < size; i++) {

                messagesList.add(new Employee.Messages(data.getString(3), data.getString(2), data.getString(7), data.getString(8)));
                data.next();
            }

            insertIntoMessageTable("");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoMessageTable(String filter){
        ObservableList<Employee.Messages> messagesFilteredList = FXCollections.observableArrayList();
        messagesFilteredList.addAll(0, messagesList);


        messageTextColumn.setCellValueFactory(new PropertyValueFactory<>("sprava"));
        messageFromColumn.setCellValueFactory(new PropertyValueFactory<>("odosielatel"));
        messageTimeColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));



        if (messagesFilteredList.size() == 0) {
            messagesFilteredList.add(null);
        }
        messageTable.setItems(messagesFilteredList);
        messageTable.refresh();
    }

    public void fillMessageList(){
        fillMessageTable();

        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();
        String sqlQuery = "SELECT * FROM clovek";
        String countQuery = "SELECT Count(*) FROM clovek";

        for ( int i = 0; i<messageList.getItems().size(); i++) {
            messageList.getItems().clear();
        }

        PreparedStatement preparedQuery, preparedCountStatement;
        try {
            preparedCountStatement = connectDB.prepareStatement(countQuery);
            ResultSet resultSetSize = preparedCountStatement.executeQuery();
            if(resultSetSize.next())
                size = resultSetSize.getInt(1);
            Employee[] list=new Employee[size];

            preparedQuery = connectDB.prepareStatement(sqlQuery);
            ResultSet data = preparedQuery.executeQuery();
            data.next();
            for (int i = 0; i < size; i++) {
                list[i] = new Employee(data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getInt(1));
                data.next();
            }
            for (int i = 0; i < size; i++) {
                messageList.getItems().add(list[i].getMeno());
            }
            messageList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    if((Integer)number2 >= 0) {
                        indexSS = list[(Integer) number2].getID();
                        fillMessageTable();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

