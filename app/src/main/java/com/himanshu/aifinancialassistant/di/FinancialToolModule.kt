package com.himanshu.aifinancialassistant.di

import com.himanshu.aifinancialassistant.domain.tool.FinancialTool
import com.himanshu.aifinancialassistant.domain.tool.MonthlySpendingTool.GetMonthlySpendingTool
import com.himanshu.aifinancialassistant.domain.tool.GetSpendingByCategoryTool
import com.himanshu.aifinancialassistant.domain.tool.GetTopCategoryTool
import com.himanshu.aifinancialassistant.domain.tool.GetTotalSpendingTool
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object FinancialToolModule {

    @Provides   //Normally Hilt provides one object
    // @IntoSet  -> Don't keep this as one standalone dependency. Add it to a Set<FinancialTool>.”
    @IntoSet     //open for adding tools, closed for modifying the registry.
    fun provideGetSpendingByCategoryTool(
        tool: GetSpendingByCategoryTool
    ): FinancialTool = tool

    @Provides
    @IntoSet
    fun provideGetTotalSpendingTool(
        tool: GetTotalSpendingTool
    ): FinancialTool = tool

    @Provides
    @IntoSet
    fun provideGetTopCategoryTool(
        tool: GetTopCategoryTool
    ): FinancialTool = tool

    @Provides
    @IntoSet
    fun provideGetMonthlySpendingTool(
        tool: GetMonthlySpendingTool
    ): FinancialTool = tool
}