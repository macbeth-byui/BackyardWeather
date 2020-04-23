package macbeth.backyardweather.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class WeatherObservation implements Serializable {

    public static final String []weatherTypes = {"Unknown", "Sunny", "Cloudy", "Rain"};
    public static final double UNKNOWN_VALUE = -9999.0;

    public long date;
    public double temp;
    public double humidity;
    public double pressure;
    public int weatherType;
    public double windSpeed;
    public double windDirection;
    public double precipTotal;
    public String notes;

     public WeatherObservation() {
        date = new Date().getTime();
        temp = UNKNOWN_VALUE;
        humidity = UNKNOWN_VALUE;
        pressure = UNKNOWN_VALUE;
        weatherType = 0;
        windSpeed = UNKNOWN_VALUE;
        windDirection = UNKNOWN_VALUE;
        precipTotal = UNKNOWN_VALUE;
        notes = "";
    }



    @Exclude
    @Override
    public String toString() {
        Date dateObj = new Date(date);
        return "WeatherObservation{" +
                "date=" + date +
                ", dateObj=" + dateObj +
                ", temp=" + temp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", weatherType=" + weatherType +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", precipTotal=" + precipTotal +
                ", notes='" + notes + '\'' +
                '}';
    }
}
