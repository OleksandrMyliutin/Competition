package org.example;

public class ElementCoordinates {
    private int i;
    private int j;

    public ElementCoordinates(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        ElementCoordinates temp = (ElementCoordinates)obj;
        if (temp.getI() == this.i && temp.getJ() == this.j) return true;
        return false;
    }

}

