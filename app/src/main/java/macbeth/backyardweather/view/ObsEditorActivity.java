package macbeth.backyardweather.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import macbeth.backyardweather.R;
import macbeth.backyardweather.model.WeatherObservation;

// TODO: Display units from the Config

public class ObsEditorActivity extends AppCompatActivity {

    public static final int OBS_EDITOR_INTENT_CODE = 200;

    public static final String OBS_EDITOR_INTENT_REQUEST = "REQUEST";
    public static final String OBS_EDITOR_INTENT_RESPONSE = "RESPONSE";
    public static final String OBS_EDITOR_INTENT_WEATHER_OBS_KEY = "WEATHER_OBS_KEY";
    public static final String OBS_EDITOR_INTENT_WEATHER_OBS_OBJECT = "WEATHER_OBS_OBJECT";

    public static final int REQUEST_NEW_OBS = 0;
    public static final int REQUEST_MOD_OBS = 1;

    public static final int RESPONSE_DELETE_OBS = 0;
    public static final int RESPONSE_UPDATE_OBS = 1;
    public static final int RESPONSE_NEW_OBS = 2;
    public static final int RESPONSE_CANCEL = 3;

    private TextView tvDate;
    private EditText etTemp;
    private EditText etHumidity;
    private EditText etPressure;
    private EditText etWindSpeed;
    private EditText etWindDirection;
    private EditText etTotalPrecip;
    private AppCompatSpinner spWeather;
    private EditText etNotes;
    private Button bSave;
    private Button bCancel;
    private Button bDelete;
    private int requestId;
    private WeatherObservation weatherObservation;
    private String weatherObservationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_editor);

        // Create view objects
        tvDate = findViewById(R.id.tv_date);
        etTemp = findViewById(R.id.et_temp);
        etHumidity = findViewById(R.id.et_humidity);
        etPressure = findViewById(R.id.et_pressure);
        etWindSpeed = findViewById(R.id.et_winddspeed);
        etWindDirection = findViewById(R.id.et_winddirection);
        etTotalPrecip = findViewById(R.id.et_totprecip);
        spWeather = findViewById(R.id.sp_weather);
        etNotes = findViewById(R.id.et_notes);
        bSave = findViewById(R.id.b_save);
        bCancel = findViewById(R.id.b_cancel);
        bDelete = findViewById(R.id.b_delete);

        // Setup spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WeatherObservation.weatherTypes);
        spWeather.setAdapter(spinnerAdapter);

        // Connect buttons with functions
        bSave.setOnClickListener((view)->{selectSave();});
        bCancel.setOnClickListener((view)->{selectCancel();});
        bDelete.setOnClickListener((view)->{selectDelete();});

        // Extract inputs from the intent
        Intent intent = getIntent();
        requestId = intent.getIntExtra(OBS_EDITOR_INTENT_REQUEST, REQUEST_NEW_OBS);
        ArrayList<String> i = intent.getStringArrayListExtra("Ing");
        Log.d("BackyardWeather", "i = "+i);
        ArrayList<String> ii = (ArrayList<String>) intent.getSerializableExtra("Ingredients");
        Log.d("BackyardWeather", "ii = "+ii);
        if (requestId == REQUEST_MOD_OBS) {
            // There will be a key and observation object for a modify request
            // Delete button will remain visible
            weatherObservation = (WeatherObservation) (intent.getSerializableExtra(OBS_EDITOR_INTENT_WEATHER_OBS_OBJECT));
            weatherObservationKey = intent.getStringExtra(OBS_EDITOR_INTENT_WEATHER_OBS_KEY);
            populateView();
        } else {
            // There will be no key for a new observation and a new observation object
            // needs to be created.
            // The Delete button is hidden
            weatherObservation = new WeatherObservation();
            weatherObservationKey = null;
            bDelete.setVisibility(View.INVISIBLE);
        }

        // Set the date of the observation at the top
        tvDate.setText(new Date(weatherObservation.date).toString());

        Log.d("BackyardWeather","ObsEditorActivity onCreate key "+ weatherObservationKey);
        Log.d("BackyardWeather","ObsEditorActivity onCreate obs "+ weatherObservation);
    }

    /**
     * Copy all information from weatherObservation to the view objects.
     */
    private void populateView() {
        if (weatherObservation.temp != WeatherObservation.UNKNOWN_VALUE) {
            etTemp.setText(String.valueOf(weatherObservation.temp));
        }
        if (weatherObservation.humidity != WeatherObservation.UNKNOWN_VALUE) {
            etHumidity.setText(String.valueOf(weatherObservation.humidity));
        }
        if (weatherObservation.pressure != WeatherObservation.UNKNOWN_VALUE) {
            etPressure.setText(String.valueOf(weatherObservation.pressure));
        }
        if (weatherObservation.windSpeed != WeatherObservation.UNKNOWN_VALUE) {
            etWindSpeed.setText(String.valueOf(weatherObservation.windSpeed));
        }
        if (weatherObservation.windDirection != WeatherObservation.UNKNOWN_VALUE) {
            etWindDirection.setText(String.valueOf(weatherObservation.windDirection));
        }
        if (weatherObservation.precipTotal != WeatherObservation.UNKNOWN_VALUE) {
            etTotalPrecip.setText(String.valueOf(weatherObservation.precipTotal));
        }
        spWeather.setSelection(weatherObservation.weatherType);
        etNotes.setText(String.valueOf(weatherObservation.notes));
    }

    /**
     * Copy all information from the view objects to the weatherObservation
     */
    private void populateObject() {
        try {
            weatherObservation.temp = Double.parseDouble(etTemp.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.temp = WeatherObservation.UNKNOWN_VALUE;
        }
        try {
            weatherObservation.humidity = Double.parseDouble(etHumidity.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.humidity = WeatherObservation.UNKNOWN_VALUE;
        }
        try {
            weatherObservation.pressure = Double.parseDouble(etPressure.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.pressure = WeatherObservation.UNKNOWN_VALUE;
        }
        try {
            weatherObservation.windSpeed = Double.parseDouble(etWindSpeed.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.windSpeed = WeatherObservation.UNKNOWN_VALUE;
        }
        try {
            weatherObservation.windDirection = Double.parseDouble(etWindDirection.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.windDirection = WeatherObservation.UNKNOWN_VALUE;
        }
        try {
            weatherObservation.precipTotal = Double.parseDouble(etTotalPrecip.getText().toString());
        } catch (NumberFormatException e) {
            weatherObservation.precipTotal = WeatherObservation.UNKNOWN_VALUE;
        }

        weatherObservation.weatherType = spWeather.getSelectedItemPosition();
        weatherObservation.notes = etNotes.getText().toString();
    }

    /**
     * Create an intent for saving the observation and return back to the previous
     * activity.
     */
    private void selectSave() {
        populateObject();
        Intent intent = new Intent();
        if (weatherObservationKey == null) {
            // If there is no key then this is new observation
            intent.putExtra(OBS_EDITOR_INTENT_RESPONSE, RESPONSE_NEW_OBS);
        } else {
            // Otherwise, this is an updated observation so the key needs to be included
            intent.putExtra(OBS_EDITOR_INTENT_RESPONSE, RESPONSE_UPDATE_OBS);
            intent.putExtra(OBS_EDITOR_INTENT_WEATHER_OBS_KEY, weatherObservationKey);
        }
        // Add the new or udpated weather observation object
        intent.putExtra(OBS_EDITOR_INTENT_WEATHER_OBS_OBJECT, weatherObservation);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Create an intent for canceling the request and return back to the previous
     * activity.
     */
    private void selectCancel() {
        Intent intent = new Intent();
        intent.putExtra(OBS_EDITOR_INTENT_RESPONSE, RESPONSE_CANCEL);
        // No additional information is needed for a cancel
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Create an intent for deleting the observation and return back to the previous
     * activity.
     */
    private void selectDelete() {
        Intent intent = new Intent();
        // The key is needed to delete
        intent.putExtra(OBS_EDITOR_INTENT_RESPONSE, RESPONSE_DELETE_OBS);
        intent.putExtra(OBS_EDITOR_INTENT_WEATHER_OBS_KEY, weatherObservationKey);
        setResult(RESULT_OK, intent);
        finish();
    }
}
