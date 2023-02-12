package com.example.redsocial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.redsocial.Fragments.ChatFragment;
import com.example.redsocial.Fragments.FiltroFragment;
import com.example.redsocial.Fragments.HomeFragment;
import com.example.redsocial.Fragments.PerfilFragment;
import com.example.redsocial.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedor, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
    new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            if(item.getItemId() == R.id.itemHome)
            {
                openFragment(new HomeFragment());
            }
            else if(item.getItemId() == R.id.itemChat)
            {
                openFragment(new ChatFragment());
            }
            else if(item.getItemId() == R.id.itemFiltros)
            {
                openFragment(new FiltroFragment());
            }
            else if(item.getItemId() == R.id.itemPerfil)
            {
                openFragment(new PerfilFragment());
            }
            return true;
        }
    };
}