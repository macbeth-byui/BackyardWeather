package macbeth.backyardweather.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

import macbeth.backyardweather.R;
import macbeth.backyardweather.model.WeatherObservation;
import macbeth.backyardweather.presenter.CurrentFragmentPresenter;

public class CurrentFragment extends Fragment {

    private View rootView;
    private CurrentFragmentPresenter currentPresenter;
    private TextView tvTemp;
    private TextView tvDate;


    /**
     * Initialize Fragment Object
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = null;
    }

    /**
     * So long as this has not been previously done, onCreateView will load the layout for
     * the fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BackyardWeather","CurrentFragment onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_current, container, false);
            currentPresenter = new CurrentFragmentPresenter(this);
            tvDate = rootView.findViewById(R.id.tv_date);
            tvTemp = rootView.findViewById(R.id.tv_temp);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         Log.d("BackyardWeather","CurrentFragment onDestroy");
        currentPresenter.destroy();
    }

    public void updateDisplay(WeatherObservation observation) {
        if (observation != null) {
            tvDate.setText(new Date(observation.date).toString());
            tvTemp.setText(String.valueOf(observation.temp));
        }
    }

}
