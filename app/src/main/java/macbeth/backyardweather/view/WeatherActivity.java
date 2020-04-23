package macbeth.backyardweather.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import macbeth.backyardweather.R;
import macbeth.backyardweather.model.WeatherObservation;
import macbeth.backyardweather.presenter.WeatherPresenter;

public class WeatherActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    BottomNavigationView navigation;
    WeatherFragmentStateAdapter fragmentAdapter;
    ProgressBar pbWait;
    TextView tvWait;
    WeatherPresenter weatherPresenter;

    /**
     * Initialize the Weather Activity including the initialization of the fragments.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Log.d("BackyardWeather","WeatherActivity onCreate");

        // Get layout objects
        viewPager = findViewById(R.id.view_pager);
        navigation = findViewById(R.id.navigation);
        pbWait = findViewById(R.id.pb_wait);
        tvWait = findViewById(R.id.tv_wait);

        // Setup Weather Presenter so it is ready to receive registrations
        weatherPresenter = new WeatherPresenter(this);

        // Pending App UI until login instructed by presenter to enable
        pendingUI("Initializing");

        // Setup and Apply adapter between Fragments and View Pager
        fragmentAdapter = new WeatherFragmentStateAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        // Start in the Current Fragment
        loadFragment(R.id.menu_browse);

        // Respond to navigation bar clicks
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                loadFragment(item.getItemId());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BackyardWeather","WeatherActivity onStart");
        weatherPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BackyardWeather","WeatherActivity onStop");
        weatherPresenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BackyardWeather", "WeatherActivity onDestroy");
        weatherPresenter.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("BackyardWeather","WeatherActivity onActivityResult "+requestCode+" " +intent);
        if (requestCode == ObsEditorActivity.OBS_EDITOR_INTENT_CODE) {
            int response = intent.getIntExtra(ObsEditorActivity.OBS_EDITOR_INTENT_RESPONSE,
                    ObsEditorActivity.RESPONSE_CANCEL);
            if (response == ObsEditorActivity.RESPONSE_NEW_OBS) {
                WeatherObservation weatherObservation = (WeatherObservation)
                        (intent.getSerializableExtra(ObsEditorActivity.OBS_EDITOR_INTENT_WEATHER_OBS_OBJECT));
                Log.d("BackyardWeather", "WeatherActivity onActivityResult NEW_OBS "+weatherObservation);
                weatherPresenter.saveWeatherObservation(weatherObservation);
            } else if (response == ObsEditorActivity.RESPONSE_UPDATE_OBS) {
                WeatherObservation weatherObservation = (WeatherObservation)
                        (intent.getSerializableExtra(ObsEditorActivity.OBS_EDITOR_INTENT_WEATHER_OBS_OBJECT));
                String weatherObservationKey = intent.getStringExtra(ObsEditorActivity.OBS_EDITOR_INTENT_WEATHER_OBS_KEY);
                Log.d("BackgroundWeather","WeatherActivity onActivityResult UPDATE_OBS "+ weatherObservationKey);
                Log.d("BackgroundWeather","WeatherActivity onActivityResult "+ weatherObservation);
                weatherPresenter.modifyWeatherObservation(weatherObservationKey, weatherObservation);
            } else if (response == ObsEditorActivity.RESPONSE_DELETE_OBS) {
                String weatherObservationKey = intent.getStringExtra(ObsEditorActivity.OBS_EDITOR_INTENT_WEATHER_OBS_KEY);
                Log.d("BackgroundWeather","WeatherActivity onActivityResult DELETE_OBS "+ weatherObservationKey);
                weatherPresenter.deleteWeatherObservation(weatherObservationKey);
            } else {
                Log.d("BackgroundWeather","WeatherActivity onActivityResult CANCEL");
            }

        }
    }

    /**
     * Provide a reference to the WeatherPresenter for the Fragments.
     * @return
     */
    public WeatherPresenter getWeatherPresenter() {
        return weatherPresenter;
    }

    public void enableUI() {
        pbWait.setVisibility(View.INVISIBLE);
        navigation.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        tvWait.setVisibility(View.INVISIBLE);
    }

    public void pendingUI(String message) {
        pbWait.setVisibility(View.VISIBLE);
        navigation.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        tvWait.setVisibility(View.VISIBLE);
        tvWait.setText(message);
    }

    /**
     * Display a toast in the app
     * @param message
     */
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Load a fragment based on a navigation bar click
     * @param menuId
     */
    private void loadFragment(int menuId) {
        switch (menuId) {
            case R.id.menu_browse :
                Log.d("BackyardWeather","WeatherActivity loadFragment FRAGMENT_CURRENT");
                viewPager.setCurrentItem(WeatherFragmentStateAdapter.FRAGMENT_CURRENT);
                break;
            case R.id.menu_search :
                Log.d("BackyardWeather","WeatherActivity loadFragment FRAGMENT_HISTORY");
                viewPager.setCurrentItem(WeatherFragmentStateAdapter.FRAGMENT_HISTORY);
                break;
            case R.id.menu_settings :
                Log.d("BackyardWeather","WeatherActivity loadFragment FRAGMENT_CHART");
                viewPager.setCurrentItem(WeatherFragmentStateAdapter.FRAGMENT_CHART);
                break;
            default :
        }
    }
}
