package macbeth.backyardweather.presenter;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import macbeth.backyardweather.model.FirebaseLoginHelper;
import macbeth.backyardweather.model.FirebaseLoginListener;
import macbeth.backyardweather.model.WeatherStationDetails;
import macbeth.backyardweather.model.Config;
import macbeth.backyardweather.model.WeatherObservation;
import macbeth.backyardweather.view.WeatherActivity;

public class WeatherPresenter implements FirebaseLoginListener {

    private WeatherActivity activity;


    public WeatherPresenter(WeatherActivity activity) {
        this.activity = activity;

        Config.getInstance().load();
        FirebaseLoginHelper.getInstance().registerListener(this);
    }

    //
    // LIFECYCLE FUNCTIONS
    //

    /**
     * This function should be called when the presenter starts for the first
     * time or if the presenter needs to restart because the activity has
     * been restarted.
     */
    public void start() {
        // Attempt to re-login in.  If not logged in, nothing will happen
        // TODO: Redo this so that it uses email and password from config
        if (!FirebaseLoginHelper.getInstance().isLoggedIn()) {
            activity.pendingUI("Login in Progress");
            FirebaseLoginHelper.getInstance().login(activity, "chad.macbeth@att.net", "weather121");
        }
    }

    /**
     * This function should be called when the activity has been stopped.
     */
    public void stop() {
        // We still want to receive Firebase notifications and keep our views
        // updated.
        // TODO: Do we want to stop receiving firebase data until we start again?
        // TODO: Do we want to stop notifying activities (go silient)?
    }

    /**
     * This function should be called when the activity has been destroyed.  This
     * implies that this presenter is no longer needed.
     */
    public void destroy() {
        // Database registrations are no longer needed.  However, will still
        // want to remain logged in.
        FirebaseLoginHelper.getInstance().unregisterListener(this);
        FirebaseLoginHelper.getInstance().logoff();
    }

    //
    // CALLBACKS FOR FIREBASELOGINLISTENER
    //

    @Override
    public void loginSuccess() {
        Log.d("BackyardWeather","WeatherPresenter loginSuccess "+FirebaseLoginHelper.getInstance().getUserEmail());
        activity.enableUI();
        // TODO: Update WeatherActivity to show logon
    }

    @Override
    public void loginFailed() {
        Log.d("BackyardWeather","WeatherPresenter loginFailed");
        activity.pendingUI("Login Failed");
        // TODO: Update WeatherActivity to show logoff
    }

    @Override
    public void logoffComplete() {
        Log.d("BackyardWeather","WeatherPresenter logoffComplete");
        activity.pendingUI("Need to Login");
        // TODO: Update WeatherActivity to show logoff
    }


    //
    // REQUEST FUNCTIONS FROM VIEW
    //


    /**
     * This function should be called when the user has requested to login.
     */
    public void login(String email, String password) {
        activity.pendingUI("Login in Progress");
        FirebaseLoginHelper.getInstance().login(activity, email, password);
    }

    /**
     * This function should be called when the user has requested to logoff.
     */
    public void logoff() {
        // Note that FirebaseHelper will unregister all Firebase Requests
        // automatically on logoff.
        activity.pendingUI("Logoff in Progress");
        FirebaseLoginHelper.getInstance().logoff();
    }


    /**
     * Save a new weather observation to the station.
     * @param weatherObservation
     */
    public void saveWeatherObservation(WeatherObservation weatherObservation) {
        String path = FirebaseLoginHelper.getInstance().getUserID()+"/observations";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.push().setValue(weatherObservation);
    }

    /**
     * Save updated station details
     * @param weatherStationDetails
     */
    public void saveWeatherStationDetails(WeatherStationDetails weatherStationDetails) {
        String path = FirebaseLoginHelper.getInstance().getUserID()+"/details";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.setValue(weatherStationDetails);
    }

    public void deleteWeatherObservation(String key) {

    }

    public void modifyWeatherObservation(String key, WeatherObservation updated) {

    }



}
