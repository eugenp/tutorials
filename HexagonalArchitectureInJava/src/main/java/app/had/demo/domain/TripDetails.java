package app.had.demo.domain;

public class TripDetails {
	private Integer customerId;
	
	private Integer driverId;
	
	private String pickupAddress;
	
	private String dropAddress;
	
	private Double chargedAmount;
	
	private String status;
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getDriverId() {
		return driverId;
	}
	
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	public String getPickupAddress() {
		return pickupAddress;
	}
	
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getDropAddress() {
		return dropAddress;
	}
	
	public void setDropAddress(String dropAddress) {
		this.dropAddress = dropAddress;
	}
	
	public Double getChargedAmount() {
		return chargedAmount;
	}
	
	public void setChargedAmount(Double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
