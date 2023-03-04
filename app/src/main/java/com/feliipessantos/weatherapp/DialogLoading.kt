package com.feliipesantos.weatherapp

import android.app.Activity
import android.app.AlertDialog

class DialogLoading(private val activity: Activity) {
    private lateinit var dialog: AlertDialog
    fun dialogLoadingInit(layout: Int) {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(layout, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

    }

    fun dialogLoadingFinish(){
        dialog.dismiss()
    }
}