package com.example.dell.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    androidx.appcompat.widget.Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView header_email, header_name;
    String user_id = "";
    static Personal_Detail_Navigation_Header p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Firebase.setAndroidContext(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("DashBoard");
        toolbar.bringToFront();

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

            case R.id.add_expense: {
                Toast.makeText(Dashboard.this, "Add Expense", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.recent_added: {
                Toast.makeText(Dashboard.this, "recent added", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.view_expense_category_wise: {
                Toast.makeText(Dashboard.this, "category wise", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.view_expense_date_wise: {
                Toast.makeText(Dashboard.this, "date wise", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.logout: {
                SplashScreen.sqLite_login.deleteData(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, Login.class));
                Toast.makeText(Dashboard.this, "Logedout", Toast.LENGTH_SHORT).show();
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
                try{
                    p = new Personal_Detail_Navigation_Header(snapshot.child("Name").getValue().toString(),
                                                              snapshot.child("Email").getValue().toString(),
                                                              snapshot.child("Password").getValue().toString(),
                                                              snapshot.child("Contact").getValue().toString());
                    header_name.setText(p.getName());
                    header_email.setText(p.getEmail());

                } catch(Exception e){
                    Toast.makeText(Dashboard.this,"Error !",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
