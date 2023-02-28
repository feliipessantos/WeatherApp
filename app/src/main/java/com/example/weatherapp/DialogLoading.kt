package com.example.weatherapp

import android.app.Activity
import android.app.AlertDialog

class DialogLoading(private val activity: Activity) {
    lateinit var dialog: AlertDialog
    fun DialogLoadingInit() {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(R.layout.dialog_loading, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun DialogLoadingFinish(){
        dialog.dismiss()
    }
}