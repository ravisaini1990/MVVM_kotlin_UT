package com.example.mvvm_kotlin_ut.di

import com.example.mvvm_kotlin_ut.network.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {
    @Singleton
    @Provides
    fun  provideCoroutineRetroInfo(okHttpClient: OkHttpClient) : Retrofit {
        return  Retrofit
            .Builder()
            .baseUrl(NetworkUtil.NETWORK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {

        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(MyInterceptor())
        return  httpClient.build();

    }


    //https://blog.mindorks.com/okhttp-interceptor-making-the-most-of-it
    class MyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")

            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}