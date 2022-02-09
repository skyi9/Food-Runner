package com.learn.foodrunner.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.learn.foodrunner.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private lateinit var txtUserName: TextView
    private lateinit var txtPhone: TextView
    private lateinit var txtAddress: TextView
    private lateinit var txtEmail: TextView
    private lateinit var sharedPrefs: SharedPreferences

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        sharedPrefs = (activity as FragmentActivity).getSharedPreferences("FoodApp", Context.MODE_PRIVATE)
        txtUserName = view.findViewById(R.id.name)
        txtPhone = view.findViewById(R.id.mobile)
        txtEmail = view.findViewById(R.id.gmail)
        txtAddress = view.findViewById(R.id.address)
        txtUserName.text = sharedPrefs.getString("user_name", null)
        val phoneText = "+91-${sharedPrefs.getString("user_mobile_number", null)}"
        txtPhone.text = phoneText
        txtEmail.text = sharedPrefs.getString("user_email", null)
        val address = sharedPrefs.getString("user_address", null)
        txtAddress.text = address
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}