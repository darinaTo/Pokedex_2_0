package com.example.pokedex_2_0.di

import android.content.Context
import com.example.pokedex_2_0.data.constants.Constants
import com.example.pokedex_2_0.data.service.networkSerivices.PokeApi
import com.example.pokedex_2_0.utils.NotificationUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    fun provideNotificationUtils(@ApplicationContext context : Context) :NotificationUtils =
        NotificationUtils(context)
}
