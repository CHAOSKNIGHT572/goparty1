package com.example.tansen.goparty1;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.HashMap;

/**
 * Created by tansen on 8/16/17.
 */

public class activity_find_party extends AppCompatActivity implements fragment_recyclerview_findParty.OnListItemSelectedListener {
    Fragment mContent;
    SharedPreferences shre;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_party);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_find_party);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_activity_findparty);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState != null) {
            mContent = getFragmentManager().getFragment(
                    savedInstanceState, "mContent");
            getFragmentManager().beginTransaction()
                    .replace(R.id.id_layout_activity_findparty, mContent)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            mContent = fragment_find_party.newInstance(R.id.id_layout_fragment_findparty);
            getFragmentManager().beginTransaction()
                    .replace(R.id.id_layout_activity_findparty, mContent)
                    //   .addToBackStack(null)
                    .commit();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        disableNavigationViewScrollbars(navigationView);

        View headerView = navigationView.getHeaderView(0);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.navcircleView);
        shre = PreferenceManager.getDefaultSharedPreferences(this);
        edit = shre.edit();
        String img = shre.getString("/scard/Images/img_headPhoto.jpg", "");
        File imgFile = new File(img);
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgView.setImageBitmap(bitmap);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {int id = item.getItemId();
                switch (id) {
                    case R.id.nav_profile:
                        Intent intent1 = new Intent(activity_find_party.this, ProfileActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.nav_party_nearby:
                        Intent intent2 = new Intent(activity_find_party.this, activity_recyclerview.class);
                        startActivity(intent2);
                        return true;
                    case R.id.nav_find_party:
                        Intent intent4 = new Intent(activity_find_party.this, activity_find_party.class);
                        startActivity(intent4);
                        return true;
                    case R.id.nav_host_party:
                        Intent intent3 = new Intent(activity_find_party.this, activity_create_party.class);
                        startActivity(intent3);
                        return true;
                    case R.id.nav_logout:
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        Intent intent = new Intent(activity_find_party.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "mContent", mContent);
    }

    @Override
    public void onListItemSelected(int position, HashMap<String, ?> party) {
        getFragmentManager().beginTransaction()
                .replace(R.id.id_layout_activity_findparty, fragment_party_found_detail.newInstance(party))
                .addToBackStack(null)
                .commit();
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
