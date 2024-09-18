package com.example.testovoeyandexschedule.di

import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.network.api.MainMenuApi
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide
import retrofit2.Retrofit

@Module
interface ApiModule {

    @BindInstance(cache = BindInstance.CacheType.Strong)
    fun retrofit(r: Retrofit? = null): Retrofit

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMainMenuApi(retrofit: Retrofit = this.retrofit()) : MainMenuApi
}