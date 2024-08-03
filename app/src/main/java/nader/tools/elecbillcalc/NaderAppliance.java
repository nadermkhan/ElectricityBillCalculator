package nader.tools.elecbillcalc;

enum TIMETYPE {
	HOUR,
	MINUTE,
	SECOND
}

public class NaderAppliance {
	private int wattage; // in watts
	private int timeInSeconds; // in seconds
	private double costPerKwh; // cost per kilowatt-hour (kWh)
	
	public NaderAppliance(int wattage, TIMETYPE t, int time) {
		this.wattage = wattage;
		this.costPerKwh = 9;
		this.timeInSeconds = convertTimeToSeconds(t, time);
	}
	
	private int convertTimeToSeconds(TIMETYPE t, int time) {
		switch (t) {
			case HOUR:
			return time * 3600;
			case MINUTE:
			return time * 60;
			case SECOND:
			return time;
			default:
			throw new IllegalArgumentException("Invalid time type");
		}
	}
	
	public int getWattage() {
		return this.wattage;
	}
	
	public int getTimeInSeconds() {
		return this.timeInSeconds;
	}
	
	public double getCostPerKwh() {
		return this.costPerKwh;
	}
	
	public double costPerItem() {
		return (this.wattage / 1000.0) * (this.timeInSeconds / 3600.0) * this.costPerKwh;
	}
	
	@Override
	public String toString() {
		return "Appliance [wattage=" + wattage + ", timeInSeconds=" + timeInSeconds + ", costPerKwh=" + costPerKwh + "]";
	}
}