package entities;

import java.util.Date;

public class DeliveryPoint {
    private Coordinate coordinate;
    private Date arrivalTime;
    private Date departureTime;

    public DeliveryPoint(Coordinate coordinate, Date arrivalTime, Date departureTime) {
        this.coordinate = coordinate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
    

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }
    

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    
    
}
