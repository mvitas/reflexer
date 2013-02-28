package com.reflexer.ui.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.reflexer.util.RXTypes;

/**
 * View that is used to display RXType.
 * 
 * @author ivan
 * 
 */
public abstract class RXTypeView extends LinearLayout {

	public interface OnValueChangedListener {
		public void onValueChanged(String name, Object value);
	}

	protected OnValueChangedListener onValueChangedListener;

	protected String name;

	public static RXTypeView createView(Context context, String name, int type) {
		switch (type) {
		case RXTypes.RX_BOOL:
			RXBoolView boolView = new RXBoolView(context);
			boolView.setName(name);
			return boolView;
		case RXTypes.RX_INT:
			RXIntView intView = new RXIntView(context);
			intView.setName(name);
			return intView;
		case RXTypes.RX_STRING:
			RXStringView stringView = new RXStringView(context);
			stringView.setName(name);
			return stringView;
		default:
			return null;
		}

	}

	public RXTypeView(Context context) {
		super(context);
	}

	public abstract void setValue(Object value);

	public abstract Object getValue();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void setRequired(boolean required);

	public abstract boolean getRequired();

	public void setOnValueChangedListener(OnValueChangedListener listener) {
		this.onValueChangedListener = listener;
	}

	protected void valueChanged(Object value) {
		if (onValueChangedListener != null) {
			onValueChangedListener.onValueChanged(name, value);
		}
	}
}
