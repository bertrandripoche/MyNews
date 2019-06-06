package com.depuisletemps.mynews.Controllers.Activities;

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
import android.widget.Toast;

import com.depuisletemps.mynews.Controllers.Fragments.TopStoryFragment;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Views.PageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private TopStoryFragment topStoryFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureViewPagerAndTabs(0);
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
                Toast.makeText(this, "Ecran de recherche", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_notifications:
                Toast.makeText(this, "Ecran de notifications", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_help:
                Toast.makeText(this, "Ecran d'aide", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_about:
                Toast.makeText(this, "Ecran autour de l'appli", Toast.LENGTH_LONG).show();
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
            case R.id.activity_main_drawer_business:
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_books:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_television:
                pager.setCurrentItem(4);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(int position){
        pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);

        pager.setCurrentItem(position);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
