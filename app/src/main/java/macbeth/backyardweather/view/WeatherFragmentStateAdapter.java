package macbeth.backyardweather.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Provide an Adapter between the Fragments and the View Pager.
 * The fragments are created her as needed.
 */
public class WeatherFragmentStateAdapter extends FragmentStateAdapter {

    public static final int FRAGMENT_CURRENT = 0;
    public static final int FRAGMENT_HISTORY = 1;
    public static final int FRAGMENT_CHART = 2;
    private static final int FRAGMENT_COUNT = 3;

    /**
     * Route to the FragmentStateAdapter constuctor.
     * @param fm - FragmentManager from the Fragment Activity
     * @param lc - Lifecycle (which is the Fragment Activity)
     */
    public WeatherFragmentStateAdapter(FragmentManager fm, Lifecycle lc) {
        super(fm, lc);
    }

    /**
     * Create a new Fragment based on a position.
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        if (position == FRAGMENT_CURRENT) {
            fragment = new CurrentFragment();
        } else if (position == FRAGMENT_HISTORY) {
            fragment = new HistoryFragment();
        } else if (position == FRAGMENT_CHART) {
            fragment = new ChartFragment();
        }

        assert fragment != null; // To satisfy the NonNull

        return fragment;
    }

    /**
     * Provide the number of fragments in the View Pager.
     * @return
     */
    @Override
    public int getItemCount() {
        return FRAGMENT_COUNT;
    }

}
