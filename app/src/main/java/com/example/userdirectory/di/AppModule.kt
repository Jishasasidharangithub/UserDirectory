package com.example.userdirectory.di

import com.example.userdirectory.BuildConfig
import com.example.userdirectory.network.ApiService
import com.example.userdirectory.utils.NullOnExceptionTypeAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapterFactory(NullOnExceptionTypeAdapterFactory())
            .create()
    }

    /*---------------------------------------------------------------------------------------------------*/
    @Singleton
    @Provides
    fun provideOkHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
            else setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Provides
    @Singleton
    fun clientInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder().build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        clientInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(clientInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}

