package com.himanshu.aifinancialassistant.di

import androidx.room.Room
import com.himanshu.aifinancialassistant.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import android.content.Context
import com.himanshu.aifinancialassistant.data.local.TransactionDao
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext contexts: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            contexts,
            AppDatabase::class.java,
            "financial_database"
        ).build()
    }

    @Provides
    fun provideTransactionDao(
        database: AppDatabase
    ): TransactionDao{
        return database.transactionDao()
    }
}