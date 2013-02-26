
package com.reflexer.ui.dummy;

import android.graphics.drawable.Drawable;

public class RXItem {

    private String name;
    private Drawable icon;
    private boolean enabled;

    public RXItem() {

    }

    public RXItem(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public RXItem(String name, boolean enabled, Drawable icon) {
        this.name = name;
        this.enabled = enabled;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
