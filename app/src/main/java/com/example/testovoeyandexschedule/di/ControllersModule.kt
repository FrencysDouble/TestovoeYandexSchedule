package com.example.testovoeyandexschedule.di

import com.example.testovoeyandexschedule.controllers.MainMenuController
import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.network.MainApiController
import com.example.testovoeyandexschedule.network.MainMenuApiInterface
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide


@Module
interface ControllersModule {

    @BindInstance
    fun mApiController(ma : MainApiController? = null) : MainApiController

    @Provide(cache = Provide.CacheType.Soft)
    fun provideSelectedText() : SelectedText

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMainMenuController(mainMenuApiInterface: MainMenuApiInterface = mApiController(),selectedText: SelectedText = provideSelectedText()) : MainMenuController
}