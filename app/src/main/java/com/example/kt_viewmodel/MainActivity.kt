package com.example.kt_viewmodel

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.kt_viewmodel.countlist.CountListFragment
import com.example.kt_viewmodel.mainlist.MainFragment
import com.example.kt_viewmodel.mainlist.MainFragment.OnActivityInterActonListener

class MainActivity : FragmentActivity(), OnActivityInterActonListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance())
                    .addToBackStack(MainFragment::class.java.simpleName).commit()
        }
    }

    override fun onSelectGoToCountList() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CountListFragment.newInstance())
                .addToBackStack(CountListFragment::class.java.simpleName)
                .commit()
    }
}