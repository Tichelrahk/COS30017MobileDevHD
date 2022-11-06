package com.example.mealstoeat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cloudinary.android.MediaManager
import com.squareup.picasso.Picasso

class MealRowAdapter (private val data: List<Meal>) : RecyclerView.Adapter<MealRowAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealRowAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MealRowAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val mealName: TextView = v.findViewById(R.id.mealName)
        private val mealImg: ImageView = v.findViewById(R.id.mealImg)
        private val row: LinearLayout = v.findViewById(R.id.row)
        private val mealRating: TextView = v.findViewById(R.id.mealRating)

        fun bind(item: Meal) {
            mealName.text = item.meal_name
            mealRating.text = item.rating.toString()
            Picasso.get().load(MediaManager.get().url().generate(item.image)).into(mealImg)

            v.setOnClickListener{
                val i = Intent(it.context, DetailActivity::class.java)
                i.putExtra("record", item.id)
                it.context.startActivity(i)
            }
        }
    }

}