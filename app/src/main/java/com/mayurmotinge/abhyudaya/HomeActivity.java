package com.mayurmotinge.abhyudaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnvHome;
    ImageView ivProfile;
    TextView tvPageTitle;

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivProfile = findViewById(R.id.ivProfile);

        bnvHome = findViewById(R.id.bottom_navigation);
        bnvHome.setOnNavigationItemSelectedListener(this);
        bnvHome.setSelectedItemId(R.id.home);

        fragmentManager = getSupportFragmentManager();
        
        ViewCompat.setOnApplyWindowInsetsListener(bnvHome, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int systemWindowInsetBottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).top;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                params.bottomMargin = systemWindowInsetBottom;
                v.setLayoutParams(params);
                return insets;
            }
        });

        tvPageTitle = findViewById(R.id.tvPageTitle);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flOnHome); // Assuming flOnHome is your FrameLayout's ID
                if (!(currentFragment instanceof HomeFragment)) {
                    bnvHome.setSelectedItemId(R.id.home);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flOnHome, new HomeFragment()).commit();
                } else {
                    setEnabled(false);
                    onBackPressed();
                }
            }
        });

    }

    HomeFragment homeFragment = new HomeFragment();
    PeopleFragment peopleFragment = new PeopleFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        MenuItem menuItemHome, menuItemPeople, menuItemNotifications;

        menuItemHome = bnvHome.getMenu().findItem(R.id.home);
        menuItemPeople = bnvHome.getMenu().findItem(R.id.people);
        menuItemNotifications = bnvHome.getMenu().findItem(R.id.notifications);

        menuItemHome.setIcon(R.drawable.icon_home);
        menuItemPeople.setIcon(R.drawable.icon_people);
        menuItemNotifications.setIcon(R.drawable.icon_bell);

        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flOnHome, homeFragment).commit();
            menuItemHome.setIcon(R.drawable.icon_home_filled);
            return true;
        } else if (item.getItemId() == R.id.people) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flOnHome, peopleFragment).commit();
            menuItemPeople.setIcon(R.drawable.icon_people_filled);
            return true;
        } else if (item.getItemId() == R.id.notifications) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flOnHome, notificationsFragment).commit();
            menuItemNotifications.setIcon(R.drawable.icon_bell_filled);
            return true;
        }

        return false;
    }
}