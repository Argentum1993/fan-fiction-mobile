package com.example.fanfics.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fanfics.R

class LoginProgressDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            var view = LayoutInflater.from(context).inflate(R.layout.layout_proggres, null)
            AlertDialog.Builder(it, R.style.Theme_LoadingDialog)
                .setView(view)
                .create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}