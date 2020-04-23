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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import macbeth.backyardweather.R;
import macbeth.backyardweather.presenter.HistoryFragmentPresenter;

public class HistoryFragment extends Fragment {

    private View rootView;
    private FloatingActionButton bAdd;
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    private HistoryFragmentPresenter historyPresenter;

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
        Log.d("BackyardWeather","HistoryFragment onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_history, container, false);
            historyPresenter = new HistoryFragmentPresenter(this);
            bAdd = rootView.findViewById(R.id.b_add);
            rvHistory = rootView.findViewById(R.id.rv_history);
            rvHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            historyAdapter = new HistoryAdapter(historyPresenter.getWeatherObservationList());
            rvHistory.setAdapter(historyAdapter);

            bAdd.setOnClickListener((view)->{selectAdd();});
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BackyardWeather","HistoryFragment onDestroy");
        historyPresenter.destroy();
    }


    private void selectAdd() {
        Intent intent = new Intent(getActivity(), ObsEditorActivity.class);
        intent.putExtra(ObsEditorActivity.OBS_EDITOR_INTENT_REQUEST, ObsEditorActivity.REQUEST_NEW_OBS);
        getActivity().startActivityForResult(intent, ObsEditorActivity.OBS_EDITOR_INTENT_CODE);
    }

    public void updateDisplay() {
        historyAdapter.notifyDataSetChanged();
    }



}
