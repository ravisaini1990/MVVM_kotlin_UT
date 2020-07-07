package com.example.mvvm_kotlin_ut.network

import com.example.mvvm_kotlin_ut.login.model.LoginModel
import com.example.mvvm_kotlin_ut.login.model.TokenModel
import com.example.mvvm_kotlin_ut.userlist.model.RetroResult
import com.example.mvvm_kotlin_ut.userlist.model.RetroResultUser
import retrofit2.Response
import retrofit2.http.*

interface NetworkAPIService {

    @POST("/api/login")
    suspend fun validateLogin(@Body loginModel: LoginModel) : Response<TokenModel>

    @GET("/api/users")
    suspend fun fetchUsers(@Query("page") page :Int): Response<RetroResult>

    @GET("/api/users/{id}")
    suspend fun fetchSelectedUsers(@Path("id") id : Int): Response<RetroResultUser>
}