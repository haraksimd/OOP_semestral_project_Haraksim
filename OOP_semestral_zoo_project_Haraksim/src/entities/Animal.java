package entities;

public class Animal {

    public static class Zaznam{
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

        public String getOdosielatel() {

            return menoOdosielatel + " " + priezviskoOdosielatel;
        }

        public void setMenoOdosielatel(String menoOdosielatel) {
            this.menoOdosielatel = menoOdosielatel;
        }

        public void setPriezviskoOdosielatel(String priezviskoOdosielatel) {
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        public String getMenoOdosielatel() {
            return menoOdosielatel;
        }

        public int getID(){
            return ID;
        }


        private String sprava;
        private String datum;
        private String menoOdosielatel;
        private String priezviskoOdosielatel;
        private int ID;


        public Zaznam(String sprava, String datum, String menoOdosielatel, String priezviskoOdosielatel, int ID ) {
            this.ID = ID;
            this.sprava = sprava;
            this.datum = datum;
            this.menoOdosielatel = menoOdosielatel;
            this.priezviskoOdosielatel = priezviskoOdosielatel;
        }

        public Zaznam(){

        }
    }


    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getDruh() {
        return druh;
    }

    public void setDruh(String druh) {
        this.druh = druh;
    }

    public String getSektor() {
        return sektor;
    }

    public void setSektor(String sektor) {
        this.sektor = sektor;
    }

    private int ID;
    private String meno;
    private String druh;
    private String sektor;

    public Animal(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Animal(int ID, String meno, String druh, String sektor){
        this.ID = ID;
        this.meno = new String(meno);
        this.druh = new String(druh);
        this.sektor = new String(sektor);
    }



}
