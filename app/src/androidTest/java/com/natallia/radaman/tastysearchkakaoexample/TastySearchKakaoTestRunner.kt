package com.natallia.radaman.tastysearchkakaoexample

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TastySearchKakaoTestRunner : AndroidJUnitRunner() {
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(
            cl, TastySearchKakaoTestApp::class.java.name, context
        )
    }
}
