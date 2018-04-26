package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by User on 25.04.2018.
 */

public abstract class BaseActivity extends AppCompatActivity  {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
/*    System.out.println(getContentViewId() +"laaaaaaaaaaaaaaaaaaaaaa");
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);*/
    }
/*    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("TRARAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        switch (item.getItemId())
        {
            case R.id.action_active_project:
                Toast.makeText(this, "Action Active Project", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ActiveActivity.class));
                break;
            case R.id.action_inactive_project:
                Toast.makeText(getApplicationContext(), "Action Inactive Project", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, InactiveActivity.class));
                break;
            case R.id.action_overview_project:
                Toast.makeText(getApplicationContext(), "Action Overview Project", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, OverviewActivity.class));
                break;
        }
        finish();
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = bottomNavigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getNavigationMenuItemId();*/
    abstract int getContentViewId();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        startActivity(new Intent(this, AddProjectActivity.class));

        return true;
    }
}
