package com.ab.semesterapplication

import android.app.Application
import android.content.Intent
import android.util.Log
import com.ab.semesterapplication.utils.Utils
import kotlin.system.exitProcess


class SemesterApplication  : Application(){
    override fun onCreate() {
        super.onCreate()
        if(!Utils.isBuildVariantDebug()){
            Thread.setDefaultUncaughtExceptionHandler { _, e ->
                handleUncaughtException(e)
                restartApp()
            }
        }
    }


    private fun handleUncaughtException(e : Throwable){
        Log.e("app_crash",e.message.toString())
    }

    private fun restartApp() {
        val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        exitProcess(1)
    }

}