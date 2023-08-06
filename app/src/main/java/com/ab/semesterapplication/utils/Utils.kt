package com.ab.semesterapplication.utils

import android.view.View
import com.ab.semesterapplication.BuildConfig
import com.ab.semesterapplication.model.Constants

object Utils{

    fun View.showVisibility(condition: Boolean) {
        this.visibility = if (condition) View.VISIBLE else View.GONE
    }

    fun isBuildVariantDebug(): Boolean {
        return BuildConfig.BUILD_TYPE == Constants.BUILD_TYPE_DEBUG
    }
    

}