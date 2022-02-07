package entities;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import controllers.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee implements User {

    public static class ToDoList {    //kazdy zamestnanec ma svoj vlastny todolist

        public ToDoList() {
        }

        public ToDoList(String sprava, String datum, String finished, String menoOdosielatel, String priezviskoOdosielatel){
            this.sprava = sprava;
            this.datum = datum;
            this.finished = finished;
            this.menoOdosielatel = menoOdosielatel;
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        public String getSprava() {
            return sprava;
        }

        public void setSprava(String sprava) {
            this.sprava = sprava;
        }

        public String getDatum() {
            return datum;
        }

        public void setDatum(String datum) {
            this.datum = datum;
        }

        public String getFinished() {
            return finished;
        }

        public void setFinished(String finished) {
            this.finished = finished;
        }

        public String getOdosielatel(){
            return menoOdosielatel + " " + priezviskoOdosielatel;
        }

        public String getMenoOdosielatel() {
            return menoOdosielatel;
        }

        public void setMenoOdosielatel(String menoOdosielatel) {
            this.menoOdosielatel = menoOdosielatel;
        }

        public String getPriezviskoOdosielatel() {
            return priezviskoOdosielatel;
        }

        public void setPriezviskoOdosielatel(String priezviskoOdosielatel) {
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        String sprava;
        String datum;
        String finished;
        String menoOdosielatel;
        String priezviskoOdosielatel;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String meno;
    private String priezvisko;
    private String oddelenie;
    private String cislo;

    public Employee(String meno, String priezvisko, String oddelenie, String cislo, int ID) {
        this.ID = ID;
        this.meno = new String(meno);
        this.priezvisko = new String(priezvisko);
        this.oddelenie = new String(oddelenie);
        this.cislo= new String(cislo);
    }

    public Employee(){

    };

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public String getOddelenie() {
        return oddelenie;
    }

    public void setOddelenie(String oddelenie) {
        this.oddelenie = oddelenie;
    }

    public String getCislo() {
        return cislo;
    }

    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    protected static int id_clovekautor= 0;

    public void submitNewTask(TextField dateTaskTextField, TextArea taskTextArea, int indexSS){     //pridavanie do todolistu
        String task=taskTextArea.getText();             //ziskavanie textu z textovych poli
        String date=dateTaskTextField.getText();


        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String insertQuery = "INSERT INTO todolist(uloha,datum,finished,id_clovekautor,id_clovekkomu) VALUES(?,?,'no',?,?)";


        try{   //sql prikaz na pridavanie uloh do db
            PreparedStatement insertStatemenet = connectDB.prepareStatement(insertQuery);
            insertStatemenet.setString(1,task);
            insertStatemenet.setString(2,date);
            insertStatemenet.setInt(3, indexSS);
            insertStatemenet.setInt(4, indexSS);
            insertStatemenet.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(TextField messageDate, TextArea messageText, int indexSS){      //posielanie sprav
        String message=messageText.getText();
        String date=messageDate.getText();

        System.out.println(this.getID());
        System.out.println(indexSS);



        DbConnection connectNow = new DbConnection();
        Connection connectDB = connectNow.getConnection();

        String insertQuery = "INSERT INTO spravy(datum,sprava,id_cloveko,id_clovekp) VALUES(?,?,?,?)";

        try{    //posielanie sprav cez db
            PreparedStatement insertStatemenet = connectDB.prepareStatement(insertQuery);
            insertStatemenet.setString(1,date);
            insertStatemenet.setString(2,message);
            insertStatemenet.setInt(3,this.getID());
            insertStatemenet.setInt(4,indexSS);
            insertStatemenet.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static class Messages{

        public Messages() {
        }

        public Messages(String sprava, String datum, String menoOdosielatel, String priezviskoOdosielatel){
            this.sprava = sprava;
            this.datum = datum;
            this.menoOdosielatel = menoOdosielatel;
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        public String getSprava() {
            return sprava;
        }

        public void setSprava(String sprava) {
            this.sprava = sprava;
        }

        public String getDatum() {
            return datum;
        }

        public void setDatum(String datum) {
            this.datum = datum;
        }

        public String getOdosielatel(){
            return menoOdosielatel + " " + priezviskoOdosielatel;
        }

        public String getMenoOdosielatel() {
            return menoOdosielatel;
        }

        public void setMenoOdosielatel(String menoOdosielatel) {
            this.menoOdosielatel = menoOdosielatel;
        }

        public String getPriezviskoOdosielatel() {
            return priezviskoOdosielatel;
        }

        public void setPriezviskoOdosielatel(String priezviskoOdosielatel) {
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        String sprava;
        String datum;
        String menoOdosielatel;
        String priezviskoOdosielatel;

    }

}
