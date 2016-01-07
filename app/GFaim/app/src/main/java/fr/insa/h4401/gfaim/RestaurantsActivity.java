package fr.insa.h4401.gfaim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appyvet.rangebar.RangeBar;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

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
        navigationView.setCheckedItem(R.id.nav_restaurants);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(new MapFragment(), null)
                .replace(R.id.frame_content, new RestaurantsFragment())
                .commit();

        fragmentManager.executePendingTransactions();

        Log.d("fragment", Integer.toString(getSupportFragmentManager().getBackStackEntryCount()));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Log.d("fragment", Integer.toString(fragmentManager.getBackStackEntryCount()));

            if (fragmentManager.getBackStackEntryCount() > 0) {
                updateOptionsMenuSelectedItem(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getId());
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    private void updateOptionsMenuSelectedItem(int idLayout) {
        int checkedItem = 0;

        switch (idLayout) {
            case R.layout.fragment_restaurants:
                checkedItem = R.id.nav_restaurants;
                break;

            case R.layout.fragment_details_restaurant:
                checkedItem = R.id.nav_restaurants;
                break;

            case R.layout.fragment_map:
                checkedItem = R.id.nav_map;
                break;

            case R.layout.fragment_settings:
                checkedItem = R.id.nav_settings;
                break;

            // Todo : Vérifier si des cas ne sont pas manquants

            default:
                checkedItem = R.id.nav_restaurants;
                break;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(checkedItem);
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
        if (id == R.id.advanced_search) {

            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("Paramètres de recherche")
                    .customView(R.layout.advanced_search_layout, false)
                    .positiveText("Enregistrer")
                    .show();

            View view = dialog.getCustomView();

//            Spinner spinner = (Spinner) view.findViewById(R.id.search_type);
//            // Create an ArrayAdapter using the string array and a default spinner layout
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                    R.array.regimes, android.R.layout.simple_spinner_item);
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            // Apply the adapter to the spinner
//            spinner.setAdapter(adapter);

            final Activity app = this;
            Button selectType = (Button) view.findViewById(R.id.search_restaurants_types);
            selectType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(app)
                            .title(R.string.type_restaurants)
                            .widgetColor(getResources().getColor(R.color.colorPrimary))
                            .items(R.array.type_restaurants)
                            .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    return true;
                                }
                            })
                            .positiveText(R.string.choose)
                            .show();
                }
            });


            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.search_open);
            checkBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(checkBox.isChecked()) {
                        checkBox.setText(" Peu importe");
                    } else {
                        checkBox.setText(" Oui");
                    }
                    return false;
                }
            });

            RangeBar priceRange = (RangeBar) view.findViewById(R.id.search_price_rangebar);
            final TextView priceRangeText = (TextView) view.findViewById(R.id.search_price_rangebar_text);
            priceRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                @Override
                public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                                  int rightPinIndex,
                                                  String leftPinValue, String rightPinValue) {
                    priceRangeText.setText("Prix entre "+leftPinValue+"€ et "+rightPinValue+"€");

                }
            });


            DiscreteSeekBar distanceMax = (DiscreteSeekBar) view.findViewById(R.id.search_distance_max);
            final TextView distance_max_text = (TextView) view.findViewById(R.id.search_distance_max_text);
            distanceMax.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    distance_max_text.setText("Distance max. : " + value + "km");
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                }
            });

            DiscreteSeekBar waitingTime = (DiscreteSeekBar) view.findViewById(R.id.search_waiting_time);
            final TextView waiting_text = (TextView) view.findViewById(R.id.search_waiting_max_text);
            waitingTime.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    waiting_text.setText("Tems d'attente max. : " + value + "min");
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                }
            });


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
                fragment = new AlarmsFragment();
                break;

            case R.id.nav_map:
                fragment = new MapFragment();
                break;

            case R.id.nav_manage_restaurants:
                fragment = new ManageRestaurantsFragment();
                break;

            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;

            case R.id.nav_deco:
                signOut();
                return true;

            default:
                fragment = new RestaurantsFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, fragment)
                .addToBackStack(null)
                .commit();

        fragmentManager.executePendingTransactions();

        Log.d("fragment", Integer.toString(getFragmentManager().getBackStackEntryCount()));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        ConnectionActivity.requireSignOut();
        Intent myIntent = new Intent(RestaurantsActivity.this, ConnectionActivity.class);
        startActivity(myIntent);
    }
}
