package org.wehavepower.joboffers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pc on 3/18/2016.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = {"Recientes", "Intereses", "Aplicaciones"};

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {return RecyclerViewFragment.newInstance(position + 1, TITLES[position]);}



    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < TITLES.length) {
            return TITLES[position];
        }
        return null;
    }


}
