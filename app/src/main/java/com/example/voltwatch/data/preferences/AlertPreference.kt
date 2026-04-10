package com.example.voltwatch.data.preferences

import android.content.Context
import androidx.core.content.edit

object AlertPreference {

    private const val PREF_NAME = "alert_pref"
    private const val KEY_TARGET = "target_percentage"
    private const val KEY_TRIGGERED = "triggered"

    fun saveTarget(context: Context, value: Int) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit {
            putInt(KEY_TARGET, value)
                .putBoolean(KEY_TRIGGERED, false)
        }
    }

    fun getTarget(context: Context): Int {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getInt(KEY_TARGET, -1)
    }

    fun setTriggered(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit { putBoolean(KEY_TRIGGERED, true) }
    }

    fun resetTriggered(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit { putBoolean(KEY_TRIGGERED, false) }
    }

    fun isTriggered(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_TRIGGERED, false)
    }
}