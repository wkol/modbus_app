package com.example.modbus

import android.app.Application
import timber.log.Timber

class ModbusApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant()
    }
}