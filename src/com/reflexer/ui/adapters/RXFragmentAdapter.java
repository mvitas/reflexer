package com.reflexer.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.reflexer.model.RXReflex;
import com.reflexer.ui.RXNameFragment;
import com.reflexer.ui.RXReactionFragment;
import com.reflexer.ui.RXStimuliFragment;

public class RXFragmentAdapter extends FragmentPagerAdapter {

	protected static final String[] CONTENT = new String[] { "NAME", "STIMULI", "REACTION" };

	private final Context context;
	private RXReflex reflex;
	private final int count = CONTENT.length;

	private final RXNameFragment nameFragment;

	private final RXStimuliFragment stimuliFragment;

	private final RXReactionFragment reactionFragment;

	public RXFragmentAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;

		nameFragment = RXNameFragment.newInstance();
		stimuliFragment = RXStimuliFragment.newInstance();
		reactionFragment = RXReactionFragment.newInstance();
	}

	public void setReflex(RXReflex reflex) {
		nameFragment.setReflex(reflex);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return nameFragment;
		case 1:
			return stimuliFragment;
		case 2:
			return reactionFragment;
		default:
			return null;
		}
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
