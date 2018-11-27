package entities;

public class Segment {
    private int destIndex;

    private String streetName;

    private double length;

    public Segment(int destIndex, String streetName, int length) {
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

    public void setLength(int length) {
        this.length = length;
    }
}
