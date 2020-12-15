package com.example.elizabethcakeshn


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.elizabethcakeshn.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    private fun getUserDetails(){

        //ProgressDialog(resources.getString(R.string.please_wait))
        FireStore().getUserDetails(this)
    }

    fun userDetailsSuccess(users: Users){

        GlideLoader(this@SettingsActivity).loadProductPicture(users.image, iv_user_photo)

    }
}