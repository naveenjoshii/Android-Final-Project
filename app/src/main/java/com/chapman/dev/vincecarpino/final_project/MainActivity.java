package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends Activity {

    // TODO: Fix all the layout issues

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case (R.id.navigation_profile):
                    transaction.replace(R.id.container, new ProfileFragment());
                    break;
                case (R.id.navigation_home):
                    transaction.replace(R.id.container, new FeedFragment());
                    break;
                case (R.id.navigation_search):
                    transaction.replace(R.id.container, new SearchFragment());
                    break;
                case (R.id.navigation_notifications):
                    transaction.replace(R.id.container, new NotificationsFragment());
                    break;
            }

            transaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Database db = new Database(this);

        Toolbar actionbar = findViewById(R.id.action_bar);
        setActionBar(actionbar);

        BottomNavigationView menu;

        menu = findViewById(R.id.navigationView);

        menu.setOnNavigationItemSelectedListener(listener);

        menu.setSelectedItemId(R.id.navigation_home);

        int currentUserId = getIntent().getIntExtra("UserId", -1);

        //Log.e("MAIN", String.valueOf(currentUserId));
        Database.setCurrentUserId(currentUserId);
    }
}
