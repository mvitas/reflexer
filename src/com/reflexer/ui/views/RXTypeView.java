package com.reflexer.ui.views;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * View that is used to display RXType.
 * 
 * @author ivan
 * 
 */
public abstract class RXTypeView extends LinearLayout {

	public interface OnValueChangedListener {
		public void onValueChanged(Object value);
	}

	protected OnValueChangedListener onValueChangedListener;

	public static RXTypeView createView(int type) {
		switch (type) {

		default:
			return null;
		}

	}

	public RXTypeView(Context context) {
		super(context);
	}

	public abstract void setValue(Object value);

	public abstract Object getValue();

	public void setOnValueChangedListener(OnValueChangedListener listener) {
		this.onValueChangedListener = listener;
	}

	protected void valueChanged(Object value) {
		if (onValueChangedListener != null) {
			onValueChangedListener.onValueChanged(value);
		}
	}
}
