package com.example.tp7;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp7.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding bind;
    private List<Teacher> teachers;
    private boolean enseig = false;
    private boolean about = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);
        setTitle("My App");

        setSupportActionBar(bind.toolbar);
        bind.navView.setNavigationItemSelectedListener(this);

        teachers = new ArrayList<>();
        teachers.add(new Teacher("Ben Ayed Hend", "hendbenayed@gmail.com"));
        teachers.add(new Teacher("Ben Salem Asma", "bensalemasma@gmail.com"));
        teachers.add(new Teacher("Dachraoui Ahmed","ahmed@gmail.com"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.drawerLayout, bind.toolbar, R.string.open_drawer, R.string.close_drawer);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment.newInstance(teachers)).commit();
            bind.navView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            bind.toolbar.setTitle("Enseignants");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment.newInstance(teachers)).commit();
            enseig = true;
            about = false;
        } else if (item.getItemId() == R.id.nav_about) {
            bind.toolbar.setTitle("About");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
            enseig = false;
            about = true;
        } else if (item.getItemId() == R.id.nav_logout) {
            Log.i("tag", "exit");
            Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
            finish();
        }
        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_enseig, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (enseig) {
            getMenuInflater().inflate(R.menu.menu_main_enseig, menu);
        } else if (about) {
            getMenuInflater().inflate(R.menu.menu_main_about, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}

