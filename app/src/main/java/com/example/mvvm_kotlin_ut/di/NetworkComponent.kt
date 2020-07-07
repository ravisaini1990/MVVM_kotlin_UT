package com.example.mvvm_kotlin_ut.di

import com.example.mvvm_kotlin_ut.login.viewmodel.LoginViewModelFactory
import com.example.mvvm_kotlin_ut.userlist.viewmodel.UserListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun inject(loginViewModelFactory: LoginViewModelFactory)
    fun inject(userListViewModelFactory: UserListViewModelFactory)
}

