package com.example.testovoeyandexschedule.di

import android.content.Context
import com.github.klee0kai.stone.annotations.component.Component
import com.github.klee0kai.stone.annotations.module.BindInstance

@Component
interface CoreInterface {


    // @BindInstance(cache = BindInstance.CacheType.Weak)
    //fun context(c: Context? = null): Context

    @BindInstance(cache = BindInstance.CacheType.Strong)
    fun coreSuper(cs: CoreSupervisor? = null) : CoreSupervisor


    fun controllerModule(): ControllersModule

    fun apiModule(): ApiModule

    fun coreModule() : CoreModule

}