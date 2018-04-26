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
import android.widget.Toast;

/**
 * Created by User on 22.03.2018.
 */

public class InactiveActivity extends AppCompatActivity {

    /* @Override
     int getContentViewId() {
         return R.layout.activity_inactive;
     }

     @Override
     int getNavigationMenuItemId() {
         return R.id.action_inactive_project;
     }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_active_project:
                        Toast.makeText(InactiveActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InactiveActivity.this, ActiveActivity.class));
                        finish();
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(InactiveActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(InactiveActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InactiveActivity.this, OverviewActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
    }

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
        finish();

        return true;
    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(InactiveActivity.this, ActiveActivity.class);
        startActivity(i);
        finish();
    }
}
