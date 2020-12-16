package com.example.elizabethcakeshn


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.elizabethcakes.utils.Constants
import com.example.elizabethcakeshn.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*

class CartListActivity2 : BaseActivity1() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list2)
    }

    fun successCartItemList(cartList: ArrayList<CartItem>){
        hideProgressDialog()

        for (i in cartList){
            Log.i("Titulo del item del carrito", i.title)
        }
    }

    private  fun getCartItemList (){
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStore().getCarlList(this@CartListActivity2)
    }

    override fun onResume() {
        super.onResume()
        getCartItemList()
    }



}