package com.example.mvvm_kotlin_ut.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_kotlin_ut.R
import com.example.mvvm_kotlin_ut.replaceFragmentWithNoHistory

/**
 * @author Ravi
 */
class RetrofitLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_login)
        replaceFragmentWithNoHistory(RetrofitLoginFragment(), R.id.container_fragment)
    }
}