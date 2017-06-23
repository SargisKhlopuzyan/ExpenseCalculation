package com.example.sargiskh.expensecalculation;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sargiskh.expensecalculation.currency.fragments.CurrencyFragment;
import com.example.sargiskh.expensecalculation.expensecalculation.fragments.ECFragment;
import com.example.sargiskh.expensecalculation.expensecalculation.fragments.HomeFragment;
import com.example.sargiskh.expensecalculation.notes.fragments.NotesFragment;
import com.example.sargiskh.expensecalculation.notification.NotificationFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.MainFragmentListener {

    private Fragment ecFragment;
    private Fragment homeFragment;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        setHomeFragment();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setHomeFragment();
        } else if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_currency) {
            setCurrencyFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Main Fragment Listeners
    @Override
    public void expenseCalculationButtonClicked() {
        setECFragment();
    }

    @Override
    public void notesButtonClicked() {
        setNotesFragment();
    }

    @Override
    public void notificationButtonClicked() {
        setNotificationFragment();
    }


    public ECFragment getECFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (ECFragment)fragmentManager.findFragmentById(R.id.frameLayout);
    }

    private void setHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        homeFragment = fragmentManager.findFragmentByTag("HomeFragment");
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        ((HomeFragment) homeFragment).setListener(this);
        fragmentTransaction.replace(R.id.frameLayout, homeFragment, "HomeFragment");
        fragmentTransaction.commit();

    }

    private void setECFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ecFragment = fragmentManager.findFragmentByTag("ECFragment");
        if (ecFragment == null) {
            ecFragment = new ECFragment();
        }

        fragmentTransaction.replace(R.id.frameLayout, ecFragment, "ECFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setNotesFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment notesFragment = fragmentManager.findFragmentByTag("NotesFragment");
        if (notesFragment == null) {
            notesFragment = new NotesFragment();
        }

        fragmentTransaction.replace(R.id.frameLayout, notesFragment, "NotesFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setNotificationFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment notificationFragment = fragmentManager.findFragmentByTag("NotificationFragment");
        if (notificationFragment == null) {
            notificationFragment = new NotificationFragment();
        }

        fragmentTransaction.replace(R.id.frameLayout, notificationFragment, "NotificationFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setCurrencyFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currencyFragment = fragmentManager.findFragmentByTag("CurrencyFragment");
        if (currencyFragment == null) {
            currencyFragment = new CurrencyFragment();
        }

        fragmentTransaction.replace(R.id.frameLayout, currencyFragment, "CurrencyFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

