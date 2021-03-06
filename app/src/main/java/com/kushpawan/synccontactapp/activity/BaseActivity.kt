package com.kushpawan.synccontactapp.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kushpawan.synccontactapp.R

open class BaseActivity : AppCompatActivity() {

    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun createProgressLoader() {
        if (pd == null) {
            pd = ProgressDialog(
                this,
                R.style.MyProgressTheme
            )
        }
        pd!!.isIndeterminate = true
    }

    fun showProgressDialog(msg: String?) {
        createProgressLoader()
        if (!this.isDestroyed && !this.isFinishing && !pd!!.isShowing) {
            pd!!.setMessage(msg)
            pd!!.show()
        }
    }

    fun hideProgressDialog() {
        if (!this.isDestroyed && !this.isFinishing && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }

}