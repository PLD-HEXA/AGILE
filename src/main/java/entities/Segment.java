package entities;

import java.util.Objects;

public class Segment {
    private int destIndex;

    private String streetName;

    private double length;

    public Segment(int destIndex, String streetName, double length) {
        this.destIndex = destIndex;
        this.streetName = streetName;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "destId=" + destIndex +
                ", streetName='" + streetName + '\'' +
                ", length=" + length +
                '}';
    }

    public long getDestIndex() {
        return destIndex;
    }
      
    public int getDestIndex() {
        return destIndex;
    }

    public void setDestIndex(int destIndex) {
        this.destIndex = destIndex;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Segment other = (Segment) obj;
        if (this.destIndex != other.destIndex) {
            return false;
        }
        if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        return true;
    }
    
    
}
