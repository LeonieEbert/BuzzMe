package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.buzzme.Utils.TimerUtil;

/**
 * Created by User on 25.04.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        //initialize BottomNavigationBar
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(getNavigationMenuItemId());
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_active_project:
                        startActivity(new Intent(getBaseContext(), ActiveActivity.class));
                        break;
                    case R.id.action_inactive_project:
                        startActivity(new Intent(getBaseContext(), InactiveActivity.class));
                        if (ActiveActivity.getTimerFlag())
                            new TimerUtil().finishingTimer();
                        finish();
                        break;
                    case R.id.action_overview_project:
                        startActivity(new Intent(getBaseContext(), OverviewActivity.class));
                        if (ActiveActivity.getTimerFlag())
                            new TimerUtil().finishingTimer();
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    abstract int getNavigationMenuItemId();

    abstract int getContentViewId();


    //action bar with AddProject button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_project:
                startActivity(new Intent(this, AddProjectActivity.class));
                if (ActiveActivity.getTimerFlag())
                    new TimerUtil().finishingTimer();
                finish();
        }
        return true;
    }
}
