package com.example.java_viewmodel;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.java_viewmodel.countlist.CountListFragment;
import com.example.java_viewmodel.mainlist.MainFragment;

public class MainActivity extends FragmentActivity implements MainFragment.OnActivityInterActonListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .addToBackStack(MainFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onSelectGoToCountList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CountListFragment.newInstance())
                .addToBackStack(CountListFragment.class.getSimpleName())
                .commit();
    }
}