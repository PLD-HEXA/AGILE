package entities;

public class Segment {
    private int destId;

    private String streetName;

    private int length;

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

    public int getDestId() {
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
