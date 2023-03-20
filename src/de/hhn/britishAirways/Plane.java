package de.hhn.britishAirways;

/**
 * @author Yagmur Simsek
 * @version 0.1
 */

public class Plane {

    public String model;
    public int serialNum;
    public int hoursFlown;
    public int tailNr;

    public Plane(String model, int serialNum, int hoursFlown, int tailNr) {

        this.model = model;
        this.serialNum = serialNum;
        this.hoursFlown = hoursFlown;
        this.tailNr = tailNr;

    }

    public String getModel() {
        return model;

    }

    public void setModel(String model) {
        this.model = model;

    }

    public int getSerialNum() {
        return serialNum;

    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getHoursFlown(int hoursFlown) {
        return hoursFlown;
    }

    public void setHoursFlown(int hoursFlown) {
        this.hoursFlown = hoursFlown;
    }

    public int getTailNr() {
        return tailNr;
    }

    public void setTailNr(int tailNr) {
        this.tailNr = tailNr;
    }

    public void refuel() { System.out.println(this + "is refueld");}

    {
    } public void reserve() { System.out.println(this + "is reserved");}

    {

    }
    public String deice() {System.out.println(this + "is deiced");{
    }
    return null;
    } public String toString() {
        return "Plane" + model + serialNum + hoursFlown + tailNr;

    }
    }


