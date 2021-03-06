package com.depuisletemps.mynews.controllers.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.views.PageAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.createNotificationChannel();
    }

    @Override
    public void onBackPressed() {
        // When back pressed, we close the navigation drawer if open or use the standard back option
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Secondary menu creation
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Secondary menu item selection
        switch (item.getItemId()) {
            case R.id.menu_activity_main_search:
                launchActivity("Search");
                return true;
            case R.id.menu_activity_main_notifications:
                launchActivity("Notifications");
                return true;
            case R.id.menu_activity_main_help:
                launchActivity("Help");
                return true;
            case R.id.menu_activity_main_about:
                launchActivity("About");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Navigation Drawer item selection
        int id = item.getItemId();
        switch (id){
            case R.id.activity_main_drawer_top_stories :
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_most_popular:
                pager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_arts:
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_books:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_science:
                pager.setCurrentItem(4);
                break;
            case R.id.activity_main_drawer_sports:
                pager.setCurrentItem(5);
                break;
            case R.id.activity_main_drawer_technology:
                pager.setCurrentItem(6);
                break;
            case R.id.activity_main_drawer_world:
                pager.setCurrentItem(7);
                break;
            case R.id.activity_main_drawer_search:
                launchActivity("Search");
                break;
            case R.id.activity_main_drawer_notifications:
                launchActivity("Notifications");
                break;
            case R.id.activity_main_drawer_help:
                launchActivity("Help");
                break;
            case R.id.activity_main_drawer_about:
                launchActivity("About");
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(){
        // Create the view pager and tabs bar
        pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
    }

    private void configureDrawerLayout(){
        // Create the navigation drawer page
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        // Create the navigation part of the navigation drawer
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void launchActivity(String activity) {
        // Launch activity depending on the category chosen
        Class myClass;
        switch(activity) {
            case "Help":
                myClass = HelpActivity.class;
                break;
            case "About":
                myClass = AboutActivity.class;
                break;
            case "Notifications":
                myClass = NotificationsActivity.class;
                break;
            case "Search":
                myClass = SearchArticlesActivity.class;
                break;
            default:
                myClass = MainActivity.class;
        }
        Intent myIntent = new Intent(MainActivity.this,myClass);
        this.startActivity(myIntent);
    }

    private void createNotificationChannel() {
        // Needed for notification feature, need to be started first
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Notification channel description");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }
}
