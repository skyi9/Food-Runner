package com.learn.foodrunner.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.learn.foodrunner.R
import com.learn.foodrunner.database.ResDatabase
import com.learn.foodrunner.database.RestEntity
import com.learn.foodrunner.fragment.MenuFragment
import com.learn.foodrunner.models.Restaurants
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(val context:Context, private var itemList : ArrayList<Restaurants>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>(){
    class HomeViewHolder(view:View): RecyclerView.ViewHolder(view){
        val txtResName : TextView = view.findViewById(R.id.txtResName)
        val txtResRating : TextView = view.findViewById(R.id.txtResRating)
        val txtResPrice : TextView = view.findViewById(R.id.txtResPrice)
        val txtResImage : ImageView = view.findViewById(R.id.imgResImage)
        val llContent : CardView = view.findViewById(R.id.llContent)
        val favImage : ImageView = view.findViewById(R.id.imgIsFav)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row , parent , false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val food = itemList[position]
        holder.txtResName.text = food.resName
        holder.txtResRating.text = food.resRating
        holder.txtResPrice.text = food.resPrice
        //holder.txtFoodImage.setImageResource(food.foodImage)
        Picasso.get().load(food.foodImage).error(R.drawable.default_book_cover).into(holder.txtResImage)

        holder.llContent.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame , MenuFragment())
                .commit()
            (context as AppCompatActivity).supportActionBar?.title = holder.txtResName.text.toString()
        }

        val listOfFavourites = GetAllFavAsyncTask(context).execute().get()

        if (listOfFavourites.isNotEmpty() && listOfFavourites.contains(food.resId)) {
            holder.favImage.setImageResource(R.drawable.ic_favourite)

        } else {
            holder.favImage.setImageResource(R.drawable.ic_base_fav)

        }

        holder.favImage.setOnClickListener {
            val restEntity = RestEntity(
                food.resId.toInt(),
                food.resName,
                food.resRating,
                food.resPrice,
                food.foodImage
            )
            if (!DBAsyncTask(context, restEntity, 1).execute().get()) {
                val async =
                    DBAsyncTask(context, restEntity, 2).execute()
                val result = async.get()
                if (result) {
                    holder.favImage.setImageResource(R.drawable.ic_favourite)
                }
            } else {
                val async = DBAsyncTask(context, restEntity, 3).execute()
                val result = async.get()

                if (result) {
                    holder.favImage.setImageResource(R.drawable.ic_base_fav)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class DBAsyncTask (val context: Context, val restEntities: RestEntity, val mode : Int)
        : AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            when (mode) {
                1 -> {
                    //check db if the rest. is fav. or not
                    val res: RestEntity? = db.resDao().getResById(restEntities.res_id.toString())
                    db.close()
                    return res != null
                }
                2 -> {
                    //save the res. into db as fav
                    db.resDao().insertRes(restEntities)
                    db.close()
                    return true
                }
                3 -> {
                    //remove the res. from the db as fav
                    db.resDao().deleteRes(restEntities)
                    db.close()
                    return true
                }
            }
            return false
        }
    }
    class GetAllFavAsyncTask(
        context: Context
    ) :
        AsyncTask<Void, Void, List<String>>() {

        val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()
        override fun doInBackground(vararg params: Void?): List<String> {

            val list = db.resDao().getAllRes()
            val listOfIds = arrayListOf<String>()
            for (i in list) {
                listOfIds.add(i.res_id.toString())
            }
            return listOfIds
        }
    }

}