package macbeth.backyardweather.view;

import android.content.Intent;
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

import macbeth.backyardweather.R;
import macbeth.backyardweather.presenter.ChartFragmentPresenter;

public class ChartFragment extends Fragment {

    private View rootView;
    private ChartFragmentPresenter chartPresenter;

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
        Log.d("BackyardWeather","ChartFragment onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chart, container, false);
            chartPresenter = new ChartFragmentPresenter(this);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BackyardWeather","ConfigFragment onDestroy");
        chartPresenter.destroy();
    }

    public void updateDisplay() {
    }

}
