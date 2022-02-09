package com.learn.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.foodrunner.R
import com.learn.foodrunner.models.MenuItems

class MenuAdapter(val context: Context, private val itemList : ArrayList<MenuItems>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    class MenuViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val txtSno : TextView = view.findViewById(R.id.txtSNo)
        val itemName : TextView = view.findViewById(R.id.txtItemName)
        val itemCost : TextView = view.findViewById(R.id.txtItemCost)
        val btnAddToCart : Button = view.findViewById(R.id.btnAddToCart)
        val btnRemoveFromCart : Button = view.findViewById(R.id.btnRemoveFromCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_menu , parent , false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = itemList[position]
        holder.txtSno.text = menuItem.id
        holder.itemName.text = menuItem.name
        holder.itemCost.text = menuItem.cost
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}