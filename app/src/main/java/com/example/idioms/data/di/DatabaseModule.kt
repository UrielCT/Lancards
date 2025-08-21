package com.example.idioms.data.di

import android.content.Context
import androidx.room.Room
import com.example.idioms.data.IdiomsDatabase
import com.example.idioms.data.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): IdiomsDatabase{
        return Room.databaseBuilder(appContext, IdiomsDatabase::class.java, "IdiomsDatabase").build()
    }


    @Provides
    fun provideWordDao(idiomsDatabase: IdiomsDatabase): WordDao {
        return idiomsDatabase.wordDao()
    }


}