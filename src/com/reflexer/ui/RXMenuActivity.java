
package com.reflexer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.reflexer.R;
import com.reflexer.ui.adapters.RXAdapter;
import com.reflexer.ui.dummy.RXItem;

import java.util.ArrayList;

public class RXMenuActivity extends SherlockActivity {

    private ListView rxList;
    private ArrayList<RXItem> rxItems = new ArrayList<RXItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        initUI();

        rxItems = generateRX(10);
        initRXList();
    }

    private void initUI() {
        rxList = (ListView)findViewById(R.id.rx_list);
    }

    private void initRXList() {
        RXAdapter adapter = new RXAdapter(this, rxItems);
        rxList.setAdapter(adapter);

    }

    private ArrayList<RXItem> generateRX(int n) {
        ArrayList<RXItem> items = new ArrayList<RXItem>();
        for (int i = 0; i < n; i++) {
            if (i < 3) {
                items.add(new RXItem("Reflex " + i, true));
            } else {
                items.add(new RXItem("Reflex " + i, false));
            }
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add new Stimuli").setIcon(android.R.drawable.ic_input_add)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Toast.makeText(this, "Add new Stimulid", Toast.LENGTH_SHORT).show();
        return super.onMenuItemSelected(featureId, item);
    }

}
