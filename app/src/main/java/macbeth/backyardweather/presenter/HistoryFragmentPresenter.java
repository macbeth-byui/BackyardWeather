package macbeth.backyardweather.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import macbeth.backyardweather.model.FirebaseLoginHelper;
import macbeth.backyardweather.model.FirebaseLoginListener;
import macbeth.backyardweather.model.WeatherObservation;
import macbeth.backyardweather.view.HistoryFragment;


public class HistoryFragmentPresenter implements FirebaseLoginListener {

    private HistoryFragment fragment;
    private ChildEventListener firebaseListener;
    private List<WeatherObservation> weatherObservationList;

    public HistoryFragmentPresenter(HistoryFragment fragment) {
        this.fragment = fragment;
        firebaseListener = null;
        weatherObservationList = new ArrayList<>();
        FirebaseLoginHelper.getInstance().registerListener(this);
    }

    //
    // REQUEST FUNCTIONS FROM VIEW
    //

    public List<WeatherObservation> getWeatherObservationList() {
        return weatherObservationList;
    }

    //
    // Lifecycle Functions
    //

    /**
     * This function should be called when the activity has been destroyed.  This
     * implies that this presenter is no longer needed.
     */
    public void destroy() {
        // Database registrations are no longer needed.  However, will still
        // want to remain logged in.
        unregisterForWeatherData();
        FirebaseLoginHelper.getInstance().unregisterListener(this);
    }


    //
    // Registration Functions
    //

    private void registerForWeatherData() {
        if (firebaseListener != null) {
            return;  // Already registered
        }
        String path = FirebaseLoginHelper.getInstance().getUserID() + "/observations";
        // TODO: Query should be based on date range
        Query query = FirebaseDatabase.getInstance().getReference(path).orderByChild("date");
        firebaseListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("BackyardWeather", "HistoryPresenter registerForWeatherData onChildAdded "+dataSnapshot);
                WeatherObservation weatherObservation = dataSnapshot.getValue(WeatherObservation.class);
                weatherObservationList.add(weatherObservation);
                fragment.updateDisplay();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("BackyardWeather", "HistoryPresenter registerForWeatherData onChildRemoved "+dataSnapshot);
                WeatherObservation weatherObservation = dataSnapshot.getValue(WeatherObservation.class);
                weatherObservationList.remove(weatherObservation);
                fragment.updateDisplay();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addChildEventListener(firebaseListener);

    }

    private void unregisterForWeatherData() {
        if (firebaseListener != null) {
            String path = FirebaseLoginHelper.getInstance().getUserID() + "/observations";
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
            reference.removeEventListener(firebaseListener);
            firebaseListener = null;
        }

    }

    //
    // CALLBACKS FOR FIREBASELOGINLISTENER
    //

    @Override
    public void loginSuccess() {
        Log.d("BackyardWeather","ViewFragmentPresenter loginSuccess "+this+" "+FirebaseLoginHelper.getInstance().getUserEmail());
        registerForWeatherData();
    }

    @Override
    public void loginFailed() {
        Log.d("BackyardWeather","ViewFragmentPresenter loginFailed "+this);
        unregisterForWeatherData();
    }

    @Override
    public void logoffComplete() {
        Log.d("BackyardWeather","ViewFragmentPresenter logoffComplete "+this);
        unregisterForWeatherData();
    }

}
