package com.example.elizabethcakeshn


import android.os.Bundle
import android.util.Log
import com.example.elizabethcakeshn.R
import com.example.elizabethcakeshn.FireStore
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elizabethcakes.utils.Constants
import com.example.elizabethcakeshn.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_cart_list2.*
import kotlinx.android.synthetic.main.activity_product_details.*

class CartListActivity2 : BaseActivity1(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list2)

        //getCartItemList()
    }

   /* private fun setupActionBar() {

        setSupportActionBar(toolbar_cart_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_add_24)
        }

        toolbar_cart_list_activity.setNavigationOnClickListener { onBackPressed() }
    }*/

    fun successCartItemList(cartList: ArrayList<CartItem>){
        hideProgressDialog()

        if (cartList.size > 0){
            rv_cart_items_list.visibility = View.VISIBLE
            ll_checkout.visibility = View.VISIBLE
            tv_no_cart_item_found.visibility = View.GONE

            rv_cart_items_list.layoutManager = LinearLayoutManager(this@CartListActivity2)
            rv_cart_items_list.setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity2, cartList)

            rv_cart_items_list.adapter = cartListAdapter
            var subTotal: Double = 0.0
            for (item in cartList){
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()
                subTotal += (price * quantity)
            }
            tv_sub_total.text = "$$subTotal"
            tv_shipping_charge.text = "$10.0"

            if (subTotal > 0){
                ll_checkout.visibility = View.VISIBLE

                val total = subTotal + 10
                tv_total_amount.text = "$$total"
            }else {
                ll_checkout.visibility = View.GONE
            }
        }
        else{
            rv_cart_items_list.visibility = View.GONE
            ll_checkout.visibility = View.GONE
            tv_no_cart_item_found.visibility = View.VISIBLE
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