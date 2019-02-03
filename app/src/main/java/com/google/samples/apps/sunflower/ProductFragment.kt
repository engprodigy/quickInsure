package com.google.samples.apps.sunflower

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.adapters.ProductAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.google.samples.apps.sunflower.Users
import com.google.samples.apps.sunflower.utilities.UserProductAPI
import com.google.samples.apps.sunflower.viewmodels.UserProductViewModel
import com.google.samples.apps.sunflower.workers.UsersProductList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response


/*import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel*/



class ProductFragment : Fragment() {

    private val DATASET_COUNT = 60
    //val BASE_URL = "https://api.github.com/search/"
    val BASE_URL = "https://mbadigital-admin.safamdigital.com/"
    var str:String = ""

    var listOfusers: ArrayList<Users> = ArrayList()

    private lateinit var userProductModel: UserProductViewModel

   // var adapter = ProductAdapter(listOfusers)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        getUsers()

        var adapter = ProductAdapter(listOfusers)
        val view: View = inflater!!.inflate(R.layout.product_list, container,
                false)
        val activity = activity
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView


        var colors: Array<String> = arrayOf("green", "red", "blue")
        //adding items in list
        /*for (i in 0..20) {
            val user = Users()
            user.id = i
            user.login = "Eyehunt $i"
            listOfusers!!.add(user)
        }*/
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        // adapter = ProductAdapter(listOfusers)
       // recyclerView.adapter = adapter

        // Get the ViewModel.
        userProductModel = ViewModelProviders.of(this).get(UserProductViewModel::class.java)

        // Create the observer which updates the UI.
        val userProductModelObserver = Observer<Users> { newUsers ->
            // Update the UI, in this case, a TextView.
            // mNameTextView.text = newName
            listOfusers!!.add(newUsers)
            adapter = ProductAdapter(listOfusers)
            recyclerView.adapter = adapter
        }

        //adapter = ProductAdapter(listOfusers)
       // recyclerView.adapter = adapter
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        userProductModel.users.observe(this, userProductModelObserver)
        return view
    }

    private fun getUsers() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var api = retrofit.create(UserProductAPI::class.java)
        var call = api.users
        call.enqueue(object : Callback<UsersProductList> {

            override fun onResponse(call: Call<UsersProductList>?, response: Response<UsersProductList>?) {
                var usres = response?.body()
                var user = usres?.users
                var length = user!!.size

                for (i in 0 until length) {
                   // str = str + "\n" + user.get(i).id + " " + user.get(i).login

                   // listOfusers!!.add(user.get(i))
                    userProductModel.users.setValue(user[i])
                }

                // tv_user!!.text = str

            }

            override   fun onFailure(call: Call<UsersProductList>?, t: Throwable?) {
                Log.v("Error", t.toString())
            }
        })
    }
}

/*class ProductFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.product_list, container, false)
        val binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }*/

    /*private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        val factory = InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
                .get(GardenPlantingListViewModel::class.java)

        viewModel.gardenPlantings.observe(viewLifecycleOwner, Observer { plantings ->
            binding.hasPlantings = (plantings != null && plantings.isNotEmpty())
        })

        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner, Observer { result ->
            if (result != null && result.isNotEmpty())
                adapter.submitList(result)
        })
    }
}*/
