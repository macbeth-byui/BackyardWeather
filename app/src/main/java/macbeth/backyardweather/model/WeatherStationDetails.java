package macbeth.backyardweather.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WeatherStationDetails {
    public String name;
    public String owner;
    public String deviceID;
    public double latitude;
    public double longitude;
    public long altitude;

    public WeatherStationDetails() {
        name = "";
        owner = "";
        latitude = 0.0;
        longitude = 0.0;
        altitude = 0;
        deviceID = "";
    }

    @Exclude
    @Override
    public String toString() {
        return "WeatherStationDetails{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }
}
