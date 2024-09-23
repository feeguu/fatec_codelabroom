package com.example.todo

import android.app.Application
import com.example.todo.data.AppContainer
import com.example.todo.data.AppContainerImpl

class TodoApplication : Application() {
    lateinit var appContainer: AppContainer;
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}