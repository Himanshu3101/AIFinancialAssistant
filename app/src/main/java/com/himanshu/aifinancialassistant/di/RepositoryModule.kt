package com.himanshu.aifinancialassistant.di

import com.himanshu.aifinancialassistant.data.repositoryImpl.FinancialRepositoryImpl
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFinancialRepository(
        implementation: FinancialRepositoryImpl
    ): FinancialRepository
}