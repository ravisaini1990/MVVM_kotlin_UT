package com.example.mvvm_kotlin_ut.userlist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mvvm_kotlin_ut.R
import com.example.mvvm_kotlin_ut.inTransactionWithoutHistory

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        replaceFragmentWithNoHistory(UserListFragment(), R.id.container_fragment)
    }


    fun AppCompatActivity.replaceFragmentWithNoHistory(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransactionWithoutHistory { replace(frameId, fragment) }
    }
}