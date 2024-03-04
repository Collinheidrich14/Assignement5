package accidentpack;
import java.time.LocalDateTime;

public class Report implements Comparable<Report>{
	Report left;
	Report right;
	private String iD;
	private String severity;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String street;
	private String city;
	private String county;
	private String state;
	private String temperature;
	private String humidity;
	private Double visibility;
	private String weatherCondition;
	private String crossing;
	private String sunriseSunset;

	/**
	 * @param iD
	 * @param severity
	 * @param startTime
	 * @param endTime		4
	 * @param street
	 * @param city
	 * @param county
	 * @param state			8
	 * @param temperature
	 * @param humidity
	 * @param visibility
	 * @param weatherCondition	12
	 * @param crossing
	 * @param sunriseSunset
	 */
	public Report(String iD, String severity, LocalDateTime startTime, LocalDateTime endTime, String street, String city,
			String county, String state, String temperature, String humidity, Double visibility,
			String weatherCondition, String crossing, String sunriseSunset) {
		super();
		this.iD = iD;
		this.severity = severity;
		this.startTime = startTime;
		this.endTime = endTime;
		this.street = street;
		this.city = city;
		this.county = county;
		this.state = state;
		this.temperature = temperature;
		this.humidity = humidity;
		this.visibility = visibility;
		this.weatherCondition = weatherCondition;
		this.crossing = crossing;
		this.sunriseSunset = sunriseSunset;
		this.left = null;
		this.right = null;
	}

	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public Double getVisibility() {
		return visibility;
	}

	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public String getCrossing() {
		return crossing;
	}

	public void setCrossing(String crossing) {
		this.crossing = crossing;
	}

	public String getSunriseSunset() {
		return sunriseSunset;
	}

	public void setSunriseSunset(String sunriseSunset) {
		this.sunriseSunset = sunriseSunset;
	}

	@Override
	public String toString() {
		return "Report [iD=" + iD + ", severity=" + severity + ", startTime=" + startTime.toString() + ", endTime=" + endTime.toString()
				+ ", street=" + street + ", city=" + city + ", county=" + county + ", state=" + state + ", temperature="
				+ temperature + ", humidity=" + humidity + ", visibility=" + visibility + ", weatherCondition="
				+ weatherCondition + ", crossing=" + crossing + ", sunriseSunset=" + sunriseSunset + "]";
	}

	@Override
	public int compareTo(Report o) {
		return this.getVisibility().compareTo(o.getVisibility());
	}

	
	
	
}
