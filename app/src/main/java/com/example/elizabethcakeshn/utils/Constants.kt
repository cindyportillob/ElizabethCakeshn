package com.example.elizabethcakes.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    //Constantes Firebase
    //Esto es usado para la coleccion con el nombre USERS
    const val USERS: String = "users"
    const val ELIZABETHCAKES_PREFERENCES: String = "ElizabethCakesPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val NAME = "Nombre"
    //Constantes extras
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

//Codigo unico para permitir acceso a la galeria
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

//Variables constante del genero
    const val MALE : String = "male"
    const val FEMALE: String = "female"
//campos de base de dato con sus nombres
    const val MOBILE: String = "mobile"
    const val GENDER: String = "genero"
    const val IMAGE: String="image"
    const val COMPLETE_PROFILE: String = "Pcompleto"

    const val EMAIL: String = "Email"

    const val PRODUCT_IMAGE:String= "product_image"
    const val PRODUCTS:String= "products"
    const val USER_ID :String= "user_id"



    const val EXTRA_PRODUCT_OWNER_ID: String= "extra_product_owner_id"
    const val EXTRA_PRODUCT_ID: String = "extra_product_id"



    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"


    fun showImageChooser(activity: Activity ){
        var galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
        }

    /**
     * A function to get the image file extension of the selected image.
     *
     * @param activity Activity reference.
     * @param uri Image file uri.
     */

    fun getFileExtension(activity: Activity, uri:Uri?): String?{
    /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
    }
