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

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@Override
	public String toString() {
		return "DeliveryPoint [coordinate=" + coordinate + ", arrivalTime=" + arrivalTime + ", departureTime="
				+ departureTime + "]";
	}
	
	
    
    
}
