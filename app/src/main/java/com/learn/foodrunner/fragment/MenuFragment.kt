package com.learn.foodrunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.learn.foodrunner.R
import com.learn.foodrunner.adapter.MenuAdapter
import com.learn.foodrunner.database.ResDatabase
import com.learn.foodrunner.database.RestEntity
import com.learn.foodrunner.models.MenuItems

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    lateinit var recyclerMenu : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var menuAdapter: MenuAdapter

    lateinit var txtResName : TextView

    //private val itemList = arrayListOf<MenuItems>()
    private val itemList =  arrayListOf(MenuItems("1", "daal" , "100" )
    , MenuItems("2","fry","110")
    ,MenuItems("3","hogyi","120")
    ,MenuItems("4","hai","130"))
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
        var resId : Int? = 0
        var resName : String? = ""
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerMenu = view.findViewById(R.id.menuRecycler)
        layoutManager = LinearLayoutManager(activity)
        menuAdapter = MenuAdapter(activity as Context , itemList)

        recyclerMenu.layoutManager = layoutManager
        recyclerMenu.adapter = menuAdapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}