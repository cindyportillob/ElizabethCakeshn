package com.example.elizabethcakeshn


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import com.example.elizabethcakes.utils.Constants
import com.example.elizabethcakeshn.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserDetails: Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        edit_boton.setOnClickListener(this)
        btn_cerrarSesion.setOnClickListener(this)
    }

    private fun getUserDetails(){
        //(resources.getString(R.string.please_wait))
        FireStore().getUserDetails(this)
    }

    fun userDetailsSuccess(users: Users){

        mUserDetails = users



        GlideLoader(this@SettingsActivity).loadProductPicture(users.image, iv_user_photo)
        tv_nameP.text = users.Nombre
        tv_generoP.text = users.genero
        tv_emailP.text = users.Email
        tv_celularP.text = users.mobile.toString()
    }

    override  fun onResume(){
        super.onResume()
        getUserDetails()
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){

                R.id.edit_boton ->{
                    val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
                    startActivity(intent)
                }

                R.id.btn_cerrarSesion ->{
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                }
            }
        }
    }
}