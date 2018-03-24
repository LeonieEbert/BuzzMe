package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by User on 22.03.2018.
 */

public class InactiveActivity extends AppCompatActivity {
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
                        startActivity(new Intent(InactiveActivity.this, MainActivity.class));
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(InactiveActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(InactiveActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InactiveActivity.this, OverviewActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}
