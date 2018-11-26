package entities;

public class Segment {
    private long destId;

    private String streetName;

    private double length;

    public Segment(int destId, String streetName, int length) {
        this.destId = destId;
        this.streetName = streetName;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "destId=" + destId +
                ", streetName='" + streetName + '\'' +
                ", length=" + length +
                '}';
    }

    public long getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
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
