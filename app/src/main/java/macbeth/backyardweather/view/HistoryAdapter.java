package macbeth.backyardweather.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import macbeth.backyardweather.R;
import macbeth.backyardweather.model.WeatherObservation;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<WeatherObservation> weatherObservations;

    public HistoryAdapter(List<WeatherObservation> weatherObservations) {
        this.weatherObservations = weatherObservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0 && weatherObservations.size() == 0) {
            holder.tvWeather.setText("Loading...");
        } else {
            holder.tvWeather.setText(weatherObservations.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        if (weatherObservations.size() == 0) {
            // We want to display the "Loading" message
            return 1;
        } else {
            return weatherObservations.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeather = itemView.findViewById(R.id.tv_weather);
        }
    }
}
