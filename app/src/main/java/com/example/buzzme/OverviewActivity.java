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

public class OverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_active_project:
                        Toast.makeText(OverviewActivity.this, "Action Active Project", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(OverviewActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_inactive_project:
                        Toast.makeText(OverviewActivity.this, "Action Inactive Project", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(OverviewActivity.this, InactiveActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.action_overview_project:
                        Toast.makeText(OverviewActivity.this, "Action Overview Project", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

}
