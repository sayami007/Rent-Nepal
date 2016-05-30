package com.mobile.app.rentnepal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Toolbar bar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    EditText sendEmails;
    Button sendEmail;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        Snackbar.make(findViewById(android.R.id.content), "Welcome " + pref.getString("Name", null), Snackbar.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new HomeFragment()).commit();
        bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        setTitle("Home");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        View vi = navigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView header = (ImageView) vi.findViewById(R.id.imageView2);

        Log.v("ibehs mananhr", pref.getString("profile", "sdfdsf"));
        Picasso.with(vi.getContext()).load(pref.getString("profile", "https://lh6.googleusercontent.com/-qUJ_--4g5mo/AAAAAAAAAAI/AAAAAAAABh4/hohwfdR_Okc/photo.jpg")).into(header);
        toggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(toggle);
        setupDrawerContent(navigationView);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, bar, R.string.open, R.string.close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        toggle.onConfigurationChanged(config);
    }

    void setupDrawerContent(NavigationView view) {
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new HomeFragment()).commit();
                setTitle(item.getTitle());
                break;

            case R.id.postMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new PostAd()).commit();
                setTitle(item.getTitle());
                break;

            case R.id.sugMenu:
                final Dialog d = new Dialog(this);
                d.setContentView(R.layout.mail_dialog);
                sendEmail = (Button) d.findViewById(R.id.sendMail);
                sendEmails = (EditText) d.findViewById(R.id.msg);
                sendEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        String[] addes = new String[]{"binav_rocks@yahoo.com"};
                        Intent inte = new Intent(Intent.ACTION_SEND);
                        inte.putExtra(Intent.EXTRA_EMAIL, addes);
                        inte.putExtra(Intent.EXTRA_SUBJECT, "Rent Nepal Suggestion");
                        inte.putExtra(Intent.EXTRA_TEXT, String.valueOf(sendEmails.getText()));
                        inte.setType("message/rfc822");
                        startActivity(Intent.createChooser(inte, "Choose Email client:"));

                    }
                });
                WindowManager.LayoutParams p = new WindowManager.LayoutParams();
                p.width = WindowManager.LayoutParams.MATCH_PARENT;
                p.height = WindowManager.LayoutParams.WRAP_CONTENT;
                p.windowAnimations = R.style.dialog_animation;
                p.setTitle("Send Feedbacks");
                d.show();
                d.getWindow().setAttributes(p);
                break;

            case R.id.devMenu:
                Dialog di = new Dialog(this);
                WindowManager.LayoutParams ps = new WindowManager.LayoutParams();
                ps.width = WindowManager.LayoutParams.MATCH_PARENT;
                ps.height = WindowManager.LayoutParams.WRAP_CONTENT;
                ps.windowAnimations = R.style.dialog_animation;
                ps.setTitle("Developed By:");
                di.setContentView(R.layout.fragment_suggesstion);
                di.show();
                di.getWindow().setAttributes(ps);
                break;

            case R.id.exitMenu:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setMessage("Do you really want to sign out?");
                build.setTitle("Sign Out");
                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pref=getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor edit=pref.edit();
                        edit.clear();
                        edit.commit();
                        finish();
                    }
                });
                build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.show();
                break;
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        getDialog();
    }

    public void getDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Quit");
        dialog.setMessage("Do you want to quit ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pref = getSharedPreferences("open", MODE_PRIVATE);
                pref.edit().clear().commit();
                MainActivity.this.finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
