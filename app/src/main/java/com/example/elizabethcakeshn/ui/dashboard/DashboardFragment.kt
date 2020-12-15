package com.example.elizabethcakeshn.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elizabethcakeshn.FireStore
import com.example.elizabethcakeshn.Product
import com.example.elizabethcakeshn.R
import com.example.elizabethcakeshn.ui.BaseFragment

class DashboardFragment : BaseFragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        for (i in productsList){
            Log.i("Product Name",i.title)
        }

    }

    private fun getProductListFromFireStore() {
        // Show the progress dialog.


        // Call the function of Firestore class.
        FireStore().getProductsList(this@DashboardFragment)
    }

    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
       // dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)


        return root
    }







}