package com.achint.mangaface.di

import android.util.Log
import com.achint.mangaface.BuildConfig
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
                .addHeader("x-rapidapi-key", BuildConfig.API_KEY)
                .addHeader("x-rapidapi-host", "mangaverse-api.p.rapidapi.com")
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
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiInterface::class.java)
}