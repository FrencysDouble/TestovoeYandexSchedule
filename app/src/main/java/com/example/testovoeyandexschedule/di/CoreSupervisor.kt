package com.example.testovoeyandexschedule.di

import com.example.testovoeyandexschedule.network.MainApiController

class CoreSupervisor(private val core : CoreInterface) {

    private val controllersModule : ControllersModule
        get() = core.controllerModule()

    private val apiModule : ApiModule
        get() = core.apiModule()

    private val coreModule : CoreModule
        get() = core.coreModule()


    inner class ApplicationMain
    {
        fun initArchitecture()
        {
            val mapi = MainApiController(apiModule)

            controllersModule.mApiController(mapi)

            apiModule.retrofit(coreModule.provideRetrofit())
        }

        fun getAppControllers() : ControllersModule = core.controllerModule()
    }
}