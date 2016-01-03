package fr.insa.h4401.gfaim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.net.URL;

public class RestaurantsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, new RestaurantsFragment())
                .commit();

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
        getMenuInflater().inflate(R.menu.restaurants, menu);

        // Mise à jour du menu avec les informations de connexion
        GoogleSignInAccount acct = ConnectionActivity.getGoogleSignInAccount();


        try {
            ((TextView) findViewById(R.id.menu_account_name)).setText(acct.getDisplayName());
            ((TextView) findViewById(R.id.menu_account_email)).setText(acct.getEmail());

            URL url = new URL(acct.getPhotoUrl().toString());
            RoundImage bmp = new RoundImage(new BitmapGetter().execute(url).get());

            ((ImageView) findViewById(R.id.menu_account_image)).setImageDrawable(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return super.onCreateOptionsMenu(menu);
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

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_restaurants:
                fragment = new RestaurantsFragment();
                break;

            case R.id.nav_alarms:
                fragment = new AlarmSettingsFragment();
                break;

            case R.id.nav_map:
                fragment = new MapFragment();
                break;

            case R.id.nav_manage_restaurants:
                Intent myIntent = new Intent(RestaurantsActivity.this, RestaurateurActivity.class);
                RestaurantsActivity.this.startActivity(myIntent);
                fragment = new DetailsRestaurantFragment();
                break;

            case R.id.nav_deco:
                signOut();
                return true;

            default:
                fragment = new DetailsRestaurantFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        Intent myIntent = new Intent(RestaurantsActivity.this, ConnectionActivity.class);
        startActivity(myIntent);
    }
}
