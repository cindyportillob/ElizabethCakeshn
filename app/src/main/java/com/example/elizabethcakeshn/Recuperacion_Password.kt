package com.example.elizabethcakeshn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_recuperacion__password.*


class Recuperacion_Password : BaseActivity1() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperacion__password)

        btn_enviar_recu.setOnClickListener {
            val email: String = txtEmailPass.text.toString().trim{ it <= ' '}
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this@Recuperacion_Password,
                                "Ya se envio a tu correo para que puedas reestablecer tu contraseña",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }else{

                            showErrorSnackBar(task.exception!!.message.toString(),true)

                        }
                    }
            }


        }
    }

}