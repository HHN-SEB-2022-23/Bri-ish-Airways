package de.hhn.britishAirways;


public class Airport {
    private String IATACode;
    private String name;

    public Airport(String IATACode, String name) {
        this.IATACode = IATACode;
        this.name = name;
        System.out.println("Airport created: " + this);
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Airport " + name + "(" + IATACode + ")";
    }

    public void deice(){
        System.out.println(this + " is deicing");
    }
}
