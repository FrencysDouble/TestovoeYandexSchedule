package com.example.testovoeyandexschedule.di

import android.app.Application
import com.github.klee0kai.stone.Stone
import org.osmdroid.config.Configuration

class MyApp : Application() {

    private lateinit var coreInterface: CoreInterface

    val cs : CoreSupervisor
        get() = coreInterface.coreSuper()

    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = "YandexSchedule/1.0"
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        coreInterface = Stone.createComponent(CoreInterface::class.java)

        coreInterface.coreSuper(CoreSupervisor((coreInterface)))

        cs.ApplicationMain().initArchitecture()

    }
}