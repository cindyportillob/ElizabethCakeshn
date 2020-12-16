package com.example.elizabethcakeshn

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.elizabethcakes.utils.Constants
import com.example.elizabethcakeshn.ui.dashboard.DashboardFragment
import com.example.elizabethcakeshn.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FireStore {



    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUser(activity: Registro, userInfo: Users) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.Registroexitoso()
            }
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName,
                    "Error no se pudo registrar el user.",
                    e
                )
            }
    }

    fun getCurrentUserID():String{
        //crear instancia de currentUser a traves de FirebaseAuth
        val currentUser= FirebaseAuth.getInstance().currentUser

        //creamos variable para asignar el currentUserId en caso que sea nulo, saldra en blanco
        var currentUserId= ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }


    fun getUserDetails(activity: Activity){

        mFireStore.collection(Constants.USERS)
                //El documento id para llegar al campo de user
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(Users::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.ELIZABETHCAKES_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.Nombre}"
                )
                editor.apply()
                when(activity){
                    is LoginActivity -> {
                        //llamar a una funcion de la actividad base para transferir el resultado
                        activity.userLoggedInSuccess(user)
                    }
                    is SettingsActivity -> {
                        activity.userDetailsSuccess(user)
                        }
                }
                //Fin
            }
            .addOnFailureListener{ e ->
                when(activity){
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity ->{

                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error al obtener detalles de usuario",
                    e
                )


            }

    }

    fun getProductDetails(activity: ProductDetailsActivity, productId: String) {

        // The collection name for PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .get() // Will get the document snapshots.
            .addOnSuccessListener { document ->

                // Here we get the product details in the form of document.
                Log.e(activity.javaClass.simpleName, document.toString())

                // Convert the snapshot to the object of Product data model class.
                val product = document.toObject(Product::class.java)!!

                activity.productDetailsSuccess(product)
            }
            .addOnFailureListener { e ->

                // Hide the progress dialog if there is an error.
                activity.hideProgressDialog()

                Log.e(activity.javaClass.simpleName, "Error while getting the product details.", e)
            }
    }


    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity ->{
                        activity.userProfileUpdateSuccess()
                    }
                }


            }.addOnFailureListener{
                    e->
                when(activity){
                    is UserProfileActivity ->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error al actualizar el detalle del usuario",
                    e
                )

            }
    }


    fun uploadImageToCloudStore(activity: Activity, imageFileURI:Uri?,imageType:String){
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType
                    + System.currentTimeMillis()+"."
        + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapshot ->
            Log.e(
                "Firebase Image URL",
                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri->
                    Log.e("URL de imagen Descargable",
                    uri.toString())
                    when(activity){
                        is UserProfileActivity ->{
                            activity.imageUploadSuccess(uri.toString())
                        }
                        is AddProductActivity->{
                            activity.imageUploadSuccess(uri.toString())

                        }
                    }
                }
        }
            .addOnFailureListener{exception ->

                when(activity){
                    is UserProfileActivity->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadProductDetails(activity: AddProductActivity, productInfo: Product) {

        mFireStore.collection(Constants.PRODUCTS)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    fun getProductsList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is DashboardFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }

    }

    /*fun checkIfItemExistInCart(activity: ProductDetailsActivity, productId: String){
        mFireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .whereEqualTo(Constants.PRODUCT_ID, productId)
            .get()
            .addOnSuccessListener { document->
               Log.e(activity.javaClass.simpleName, document.documents.toString())
                if (document.documents.size > 0) {
                    activity.productExistsInCart()
                } else {
                    activity.hideProgressDialog()
                }

            }

    }*/

    fun getDashboardItemsList(fragment: HomeFragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    productsList.add(product)
                }
                fragment.successDashboardItemsList(productsList)

            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.

                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }

    fun addCartItems(activity: ProductDetailsActivity, addToCart:CartItem){
        mFireStore.collection(Constants.CART_ITEMS)
            .document()
            .set(addToCart, SetOptions.merge())
            .addOnSuccessListener {
                activity.addToCartSuccess()
            }

    }



    fun getCarlList(activity: Activity){
        mFireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val list: ArrayList<CartItem> = ArrayList()

                for (i in document.documents){
                    val cartItem = i.toObject(CartItem::class.java)!!
                    cartItem.id = i.id

                    list.add(cartItem)
                }

                when(activity){
                    is CartListActivity2 ->{
                        activity.successCartItemList(list)

                    }
                }
            }


    }
    fun deleteProduct(fragment: DashboardFragment, productId: String) {

        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {

                fragment.productDeleteSuccess()

            }

    }





}