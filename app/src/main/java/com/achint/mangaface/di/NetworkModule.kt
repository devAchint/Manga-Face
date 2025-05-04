package com.achint.mangaface.di

import android.util.Log
import com.achint.mangaface.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule() {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            Log.d("MYDEBUG", "${chain.request().url}")
            val request = chain.request().newBuilder()
                //23faf46033msh6c913195d8814a8p1c67cfjsnb48067f6db09
                //cb85ce1a1cmsh588514d9f0c2206p17bc57jsnc7feb5b73b69
                .addHeader("x-rapidapi-key", "23faf46033msh6c913195d8814a8p1c67cfjsnb48067f6db09")
                .build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder().addInterceptor(headerInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mangaverse-api.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiInterface::class.java)
}