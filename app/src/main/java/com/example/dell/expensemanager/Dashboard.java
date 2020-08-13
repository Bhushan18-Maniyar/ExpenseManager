package com.example.dell.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvTillDate, tvTPrice;
    ListView dashboard_list_items;
    FragmentManager fragmentManager;
    Fragment TopFragment, BottomFragment;

    androidx.appcompat.widget.Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView header_email, header_name;
    String user_id = "";
    private static final String TAG = "DATA";
    static Personal_Detail_Navigation_Header p;

    ArrayList<FirebaseData> data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        data = new ArrayList<>();
//        data.add(new FirebaseData("500","Food","BBM pizza","cash"));


        tvTillDate = findViewById(R.id.tvTillDate);
        tvTPrice = findViewById(R.id.tvTPrice);
        dashboard_list_items = findViewById(R.id.list_dashboard);
        fragmentManager = getSupportFragmentManager();
        TopFragment = fragmentManager.findFragmentById(R.id.TopFragment);
//        BottomFragment = fragmentManager.findFragmentById(R.id.BottomFragment);
        fragmentManager.beginTransaction()
                .show(TopFragment).
                commit();

        Firebase.setAndroidContext(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

//        getting View from navigation header......
        View v = navigationView.getHeaderView(0);
        header_name = v.findViewById(R.id.nav_person_name);
        header_email = v.findViewById(R.id.nav_person_email);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_id = user.getUid();
            Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
            getHeader();
        }

        getList();
//****************************************** Getting total expanse till now ******************************************
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user_id).child("Expense_Detail").
                child(new Date().getYear() + 1900 + "").
                child("Total");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvTPrice.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//*************************************************************************************************************************

    }

    private void getList() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user_id).child("Expense_Detail");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot year : snapshot.getChildren()) {
                    for (DataSnapshot month : year.getChildren()) {
                        for (DataSnapshot date : month.getChildren()) {
                            for (DataSnapshot time : date.getChildren()) {
//                                for (DataSnapshot datatime: time.getChildren()) {
//                                    Toast.makeText(Dashboard.this, time.getKey(), Toast.LENGTH_SHORT).show();
//                                    FirebaseData expense_data = new FirebaseData(
//                                            (String) time.child("Ammount").getValue(),
//                                            (String) time.child("Category").getValue(),
//                                            (String) time.child("Detail").getValue(),
//                                            (String) time.child("Payment Method").getValue());
//                                    Toast.makeText(Dashboard.this, expense_data.getDetail(), Toast.LENGTH_SHORT).show();
                                    data.add( new FirebaseData(
                                            (String) time.child("Ammount").getValue(),
                                            (String) time.child("Category").getValue(),
                                            (String) time.child("Detail").getValue(),
                                            (String) time.child("Payment Method").getValue()));
//                                Log.d(TAG,expense_data.getCategoty()+"");
//                                }
                            }
                        }
                    }
                }
//                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ExpenseListAdapter listAdapter = new ExpenseListAdapter(this, data);
        dashboard_list_items.setAdapter(listAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_income: {
                Toast.makeText(Dashboard.this, "Add Income", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.add_expense: {
                startActivity(new Intent(Dashboard.this, AddExpense.class));
                Toast.makeText(Dashboard.this, "Add Expense", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.recent_added: {
                Toast.makeText(Dashboard.this, "recent added", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.view_expense_category_wise: {
                startActivity(new Intent(Dashboard.this, CategoryWiseGraph.class));
                Toast.makeText(Dashboard.this, "category wise", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.view_expense_date_wise: {
                Toast.makeText(Dashboard.this, "date wise", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.logout: {
                SplashScreen.sqLite_login.deleteData(Dashboard.p.getEmail());
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, Login.class));
                Toast.makeText(Dashboard.this, "Log out", Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
            default: {
                Toast.makeText(Dashboard.this, "NULL", Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    private void getHeader() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user_id).child("Personal_Detail");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    p = new Personal_Detail_Navigation_Header(snapshot.child("Name").getValue().toString(),
                            snapshot.child("Email").getValue().toString(),
                            snapshot.child("Password").getValue().toString(),
                            snapshot.child("Contact").getValue().toString());
                    header_name.setText(p.getName());
                    header_email.setText(p.getEmail());

                } catch (Exception e) {
                    Toast.makeText(Dashboard.this, "Error try after some time..!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
