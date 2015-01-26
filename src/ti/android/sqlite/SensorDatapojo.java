package ti.android.sqlite;

public class SensorDatapojo {
	
	String Accelerometer;
	public String getAccelerometer() {
		return Accelerometer;
	}
	public void setAccelerometer(String accelerometer) {
		Accelerometer = accelerometer;
	}
	public String getMagnometer() {
		return Magnometer;
	}
	public void setMagnometer(String magnometer) {
		Magnometer = magnometer;
	}
	public String getGyroscope() {
		return Gyroscope;
	}
	public void setGyroscope(String gyroscope) {
		Gyroscope = gyroscope;
	}
	public String getIRTemperature() {
		return IRTemperature;
	}
	public void setIRTemperature(String iRTemperature) {
		IRTemperature = iRTemperature;
	}
	public String getHumidity() {
		return Humidity;
	}
	public void setHumidity(String humidity) {
		Humidity = humidity;
	}
	public String getBarometer() {
		return Barometer;
	}
	public void setBarometer(String barometer) {
		Barometer = barometer;
	}
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	String Magnometer;
	String Gyroscope;
	String IRTemperature;
	String Humidity;
	String Barometer;
	String TransactionID;
    String DateTime;
    String AMBTemperature;
	public String getAMBTemperature() {
		return AMBTemperature;
	}
	public void setAMBTemperature(String aMBTemperature) {
		AMBTemperature = aMBTemperature;
	}
}
