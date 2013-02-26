
package com.reflexer.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.reflexer.ui.RXReactionActivity;
import com.reflexer.ui.RXStimuliActivity;

public class RXFragmentAdapter extends FragmentPagerAdapter {
    protected static final String[] CONTENT = new String[] {
            "STIMULI", "REACTION"
    };

    private Context context;
    private int count = CONTENT.length;

    public RXFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int arg0) {

        switch (arg0) {
            case 0:
                return new RXStimuliActivity();
            case 1:
                return new RXReactionActivity();
        }

        return null;
    }

    @Override
    public int getCount() {

        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return RXFragmentAdapter.CONTENT[position % CONTENT.length];
    }

}
