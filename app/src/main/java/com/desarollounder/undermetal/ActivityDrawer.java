package com.desarollounder.undermetal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class ActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentEventoUlt.OnFragmentInteractionListener
        ,FragmentEventoAll.OnFragmentInteractionListener, FragmentEventoMap.OnFragmentInteractionListener
        ,FragmentLocalAll.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_activity_drawer, new FragmentNoticia());
        tx.commit();
        getSupportActionBar().setTitle("Noticias");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer, menu);
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
        boolean FragmentTransaction = false;
        Fragment ft = null;
        int id = item.getItemId();

        if (id == R.id.nav_noticia) {
            ft = new FragmentNoticia();
            FragmentTransaction = true;
        } else if (id == R.id.nav_event_ult) {
            ft = new FragmentEventoUlt();
            FragmentTransaction = true;
        } else if (id == R.id.nav_event_all) {
            ft = new FragmentEventoAll();
            FragmentTransaction = true;
        } else if (id == R.id.nav_local_all) {
            ft = new FragmentLocalAll();
            FragmentTransaction = true;
        } else if (id == R.id.nav_local_map) {
            ft = new FragmentEventoMap();
            FragmentTransaction = true;
        }

        //Intent intent = new Intent(this,ActivityMap.class);
        //this.startActivity(intent);

        if (FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_activity_drawer,ft)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
