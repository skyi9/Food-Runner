package com.learn.foodrunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.learn.foodrunner.R
import com.learn.foodrunner.adapter.HomeRecyclerAdapter
import com.learn.foodrunner.database.ResDatabase
import com.learn.foodrunner.database.RestEntity
import com.learn.foodrunner.models.Restaurants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {

    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var allRestaurantsAdapter: HomeRecyclerAdapter
    private var restaurantList = arrayListOf<Restaurants>()
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlFav: RelativeLayout
    private lateinit var rlNoFav: RelativeLayout


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        rlFav = view.findViewById(R.id.rlFavorites)
        rlNoFav = view.findViewById(R.id.rlNoFavorites)
        rlLoading = view.findViewById(R.id.rlLoading)
        rlLoading.visibility = View.VISIBLE
        setUpRecycler(view)
        return  view
    }

    private fun setUpRecycler(view: View) {
        recyclerRestaurant = view.findViewById(R.id.recyclerRestaurants)

        /*In case of favourites, simply extract all the data from the DB and send to the adapter.
        * Here we can reuse the adapter created for the home fragment. This will save our time and optimize our app as well*/
        val backgroundList = FavouritesAsync(activity as Context).execute().get()
        if (backgroundList.isEmpty()) {
            rlLoading.visibility = View.GONE
            rlFav.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        } else {
            rlFav.visibility = View.VISIBLE
            rlLoading.visibility = View.GONE
            rlNoFav.visibility = View.GONE
            for (i in backgroundList) {
                restaurantList.add(
                    Restaurants(
                        i.res_id.toString(),
                        i.resName,
                        i.resRating,
                        i.resPrice,
                        i.resImgUrl
                    )
                )
            }

            allRestaurantsAdapter = HomeRecyclerAdapter( activity as Context , restaurantList)
            val mLayoutManager = LinearLayoutManager(activity)
            recyclerRestaurant.layoutManager = mLayoutManager
            recyclerRestaurant.itemAnimator = DefaultItemAnimator()
            recyclerRestaurant.adapter = allRestaurantsAdapter
            recyclerRestaurant.setHasFixedSize(true)
        }

    }


    /*A new async class for fetching the data from the DB*/
    class FavouritesAsync(context: Context) : AsyncTask<Void, Void, List<RestEntity>>() {

        val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<RestEntity> {

            return db.resDao().getAllRes()
        }

    }

}