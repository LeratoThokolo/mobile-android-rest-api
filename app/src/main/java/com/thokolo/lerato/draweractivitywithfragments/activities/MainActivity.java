package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private int cartSize;
    private TextView textViewLoggedInUserName;
    private TextView textViewLoggedInEmail;
    private SharedPreferences loginSharedPreferences;
    private User user = null;
    private SharedPreferences sharedPreferencesSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Activity");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));


        this.loginSharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        this.sharedPreferencesSupplier = getSharedPreferences("SupplierProducts", Context.MODE_PRIVATE);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);

        if(loginSharedPreferences.contains("user-logged-in")){

            Menu menuDrawer = navigationView.getMenu();
            MenuItem itemDrawerRegister = menuDrawer.findItem(R.id.nav_register);
            MenuItem itemDrawerLogin = menuDrawer.findItem(R.id.nav_login);
            MenuItem itemAdmin = menuDrawer.findItem(R.id.nav_admin);
            MenuItem itemCustomerProfile = menuDrawer.findItem(R.id.nav_customer_profile);
            MenuItem itemCustomerOrders = menuDrawer.findItem(R.id.nav_customer_orders);
            MenuItem itemOrders = menuDrawer.findItem(R.id.nav_list_orders);
            MenuItem itemDriverOrdersDelivered = menuDrawer.findItem(R.id.nav_driver_orders);
            MenuItem itemSupplierProduct = menuDrawer.findItem(R.id.nav_supplier);
            itemDrawerLogin.setVisible(false);
            itemDrawerRegister.setVisible(false);
            itemAdmin.setVisible(false);
            itemCustomerProfile.setVisible(false);
            itemCustomerOrders.setVisible(false);
            itemOrders.setVisible(false);
            itemDriverOrdersDelivered.setVisible(false);
            itemSupplierProduct.setVisible(false);

            Gson gson = new Gson();

            User user1 = new User();
            user1 = gson.fromJson(loginSharedPreferences.getString("user-logged-in", "No logged in user!!"), User.class);
            String userRole = user1.getRoles().get(0).getName();

            if(userRole.equals("Admin")){

                itemAdmin.setVisible(true);
            }else if(userRole.equals("Customer")){

                String userName = user1.getUserName();
                itemCustomerProfile.setTitle(userName + "'s profile");
                itemCustomerOrders.setTitle(userName + "'s orders");
                itemCustomerProfile.setVisible(true);
                itemCustomerOrders.setVisible(true);

            }else if(userRole.equals("Driver")){

                itemOrders.setVisible(true);
                itemDriverOrdersDelivered.setVisible(true);

            }else if(userRole.equals("Supplier")){

                itemSupplierProduct.setVisible(true);

            }

        }
        else if(!loginSharedPreferences.contains("user-logged-in")){

            Menu menuDrawer = navigationView.getMenu();
            MenuItem itemDrawerLogout = menuDrawer.findItem(R.id.nav_logout);
            itemDrawerLogout.setVisible(false);
            MenuItem itemAdmin = menuDrawer.findItem(R.id.nav_admin);
            itemAdmin.setVisible(false);
            MenuItem itemCustomerProfile = menuDrawer.findItem(R.id.nav_customer_profile);
            itemCustomerProfile.setVisible(false);
            MenuItem itemCustomerOrders = menuDrawer.findItem(R.id.nav_customer_orders);
            itemCustomerOrders.setVisible(false);
            MenuItem itemOrders = menuDrawer.findItem(R.id.nav_list_orders);
            itemOrders.setVisible(false);
            MenuItem itemDriverOrdersDelivered = menuDrawer.findItem(R.id.nav_driver_orders);
            itemDriverOrdersDelivered.setVisible(false);
            MenuItem itemSupplierProduct = menuDrawer.findItem(R.id.nav_supplier);
            itemSupplierProduct.setVisible(false);


        }


        //Display logged in user name and email
        this.textViewLoggedInUserName = view.findViewById(R.id.loggedInName);
        this.textViewLoggedInEmail = view.findViewById(R.id.textViewLoggedInEmail);

        this.displayUserNameAndEmail();



        //Default fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment());
        fragmentTransaction.commit();
        navigationView.setCheckedItem(R.id.nav_home);

        sharedPreferences = getSharedPreferences("CartSizeValue", Context.MODE_PRIVATE);
        this.cartSize = sharedPreferences.getInt("cartSize", 0);


    }

    public void setActionBarTitle(String title){

        getSupportActionBar().setTitle(title);

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
        if (id == R.id.cartItems) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Cart")
                    .setMessage(this.cartSize + " item(s)")
                    .setPositiveButton("Go to", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

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

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new HomeFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_categories) {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new CategoriesFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_register) {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new RegisterFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_login) {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new LoginFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_admin) {

            Intent intent = new Intent(this, TabbedActivity.class);
            startActivity(intent);

        }
        else if(id == R.id.nav_logout){

            SharedPreferences.Editor editor = this.loginSharedPreferences.edit();
            editor.remove("user-logged-in");
            editor.apply();

            SharedPreferences.Editor editor1 = this.sharedPreferencesSupplier.edit();
            editor1.remove("supplier-products");
            editor1.apply();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(id == R.id.nav_customer_profile){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new UpdateCustomerProfileFragment());
            fragmentTransaction.commit();


        }
        else if(id == R.id.nav_customer_orders){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new CustomerOrdersFragment());
            fragmentTransaction.commit();

        }else  if(id == R.id.nav_list_orders){

            startActivity(new Intent(MainActivity.this, DriverActivity.class));
        }else if(id ==R.id.nav_driver_orders){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, new DriverOrdersDeliveredFragment());
            fragmentTransaction.commit();

        }else if(id == R.id.nav_supplier){

            startActivity(new Intent(this, SupplierActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayUserNameAndEmail(){

        if(loginSharedPreferences.contains("user-logged-in")){

            Gson gson = new Gson();

            user = new User();
            user = gson.fromJson(loginSharedPreferences.getString("user-logged-in", "No logged in user!!"), User.class);
            this.textViewLoggedInUserName.setText(user.getFullNames());
            this.textViewLoggedInEmail.setText(user.getEmail());

        }
    }
}
