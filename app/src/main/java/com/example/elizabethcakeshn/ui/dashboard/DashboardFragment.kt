package com.example.elizabethcakeshn.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elizabethcakeshn.*
import com.example.elizabethcakeshn.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        if (productsList.size > 0) {
            rv_my_product_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_product_items.layoutManager = LinearLayoutManager(activity)
            rv_my_product_items.setHasFixedSize(true)

            val adapterProducts = MyProductsListAdapter(requireActivity(), productsList)
            rv_my_product_items.adapter = adapterProducts
        } else {
            rv_my_product_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id){

            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))

                return true
            }
            R.id.action_perfil_edit -> {
                startActivity(Intent(activity, SettingsActivity::class.java))

                return true
            }


        }
        

        return super.onOptionsItemSelected(item)
    }







}