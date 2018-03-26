package com.example.buzzme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by User on 26.03.2018.
 */

public class ActiveActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_active_project:
                        Toast.makeText(ActiveActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(ActiveActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, InactiveActivity.class));
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(ActiveActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActiveActivity.this, OverviewActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void onClick(View view) {
        startActivity(new Intent(ActiveActivity.this, AddProjectActivity.class));
    }


}
