package com.example.dell.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.transition.Transition;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    androidx.appcompat.widget.Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView email;
    TextView header_email, header_name;
    String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Firebase.setAndroidContext(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("DashBoard");
        toolbar.bringToFront();

        header_email = findViewById(R.id.nav_person_email);
        header_name = findViewById(R.id.nav_person_name);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        email = findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String user_email = user.getEmail();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            user_id = user.getUid();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            email.setText(user.getUid());
            Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
//            getHeader();
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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("https://expense-manager-9b616.firebaseio.com/N3wZWWuKp8c6YyvTsEPxiUc5yru2/Personal_Detail");

        reference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                Map map = snapshot.getValue(Map.class);
                header_email.setText(map.get("Email").toString());
                header_name.setText(map.get("Name").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
