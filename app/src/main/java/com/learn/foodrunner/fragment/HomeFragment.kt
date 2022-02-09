package com.learn.foodrunner.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.learn.foodrunner.R
import com.learn.foodrunner.adapter.HomeRecyclerAdapter
import com.learn.foodrunner.database.ResDatabase
import com.learn.foodrunner.database.RestEntity
import com.learn.foodrunner.models.Restaurants
import com.learn.foodrunner.util.ConnectionManager
import org.json.JSONException
import java.util.*
import java.util.zip.Inflater
import kotlin.Comparator
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerHome: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter : HomeRecyclerAdapter

    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar

    val foodInfoList = arrayListOf<Restaurants>()

    var ratingComparator = Comparator<Restaurants>{res1 , res2 ->
        res1.resRating.compareTo(res2.resRating , true)
    }

    //private val foodInfoList = arrayListOf(Food("meggie" , "244","5",R.drawable.ic_faqs))
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
        val view  = inflater.inflate(R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        recyclerHome = view.findViewById(R.id.recyclerHome) as RecyclerView
        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

if (ConnectionManager().isNetworkAvailable(activity as Context)) {
    val jsonObjectRequest =
        object : JsonObjectRequest(
            Method.GET, url,
            null, Response.Listener {
                progressLayout.visibility = View.GONE
            try {
                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")
                if (success) {
                    val resArray = data.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val foodJsonObject = resArray.getJSONObject(i)
                        val foodObject = Restaurants(
                            foodJsonObject.getString("id"),
                            foodJsonObject.getString("name"),
                            foodJsonObject.getString("rating"),
                            foodJsonObject.getString("cost_for_one"),
                            foodJsonObject.getString("image_url")
                        )
                        foodInfoList.add(foodObject)
                        recyclerAdapter = HomeRecyclerAdapter(activity as Context, foodInfoList)

                        recyclerHome.itemAnimator = DefaultItemAnimator()
                        recyclerHome.adapter = recyclerAdapter
                        recyclerHome.layoutManager = layoutManager
                        recyclerHome.setHasFixedSize(true)
                    }

                }
            }catch (e:JSONException){
                e.printStackTrace()
            }

        },
            Response.ErrorListener {
            Toast.makeText(activity as Context, "Volley error occurred", Toast.LENGTH_SHORT).show()
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "61b50390896afd"
                return headers
            }
        }
    queue.add(jsonObjectRequest)
}else{
    val builder = AlertDialog.Builder(activity as Context)
    builder.setTitle("Error")
    builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
    builder.setCancelable(false)
    builder.setPositiveButton("Ok") { _, _ ->
        ActivityCompat.finishAffinity(activity as Activity)
    }
    builder.create()
    builder.show()
}
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> Collections.sort(foodInfoList , ratingComparator)
        }
        return super.onOptionsItemSelected(item)
    }


}