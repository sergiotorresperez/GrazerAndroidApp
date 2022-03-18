package com.sergiotorres.grazer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergiotorres.grazer.R
import com.sergiotorres.grazer.presentation.login.LoginFragment
import com.sergiotorres.grazer.presentation.startupscreen.StartUpScreenFragment
import com.sergiotorres.grazer.presentation.userlist.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), StartUpScreenFragment.Listener, LoginFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StartUpScreenFragment.newInstance())
                .commitNow()
        }
    }

    // TODO: extract navigation to coordinator/navigator
    override fun onNavigateToLoginScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commit()
    }

    // TODO: extract navigation to coordinator/navigator
    override fun onNavigateToUserListScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, UserListFragment.newInstance())
            .commit()
    }
}
