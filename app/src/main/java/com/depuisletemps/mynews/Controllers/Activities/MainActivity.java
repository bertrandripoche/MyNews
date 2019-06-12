package com.depuisletemps.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Views.PageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean onNavigationItemSelected(MenuItem item) {
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
        pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void launchActivity(String activity) {
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

}
