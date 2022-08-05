package com.example.runifood

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runifood.databinding.ItemRecyclerviewBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation as RoundedCornersTransformation

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvent: FoodEvent) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {

            binding.itemTextSubject.text = data[position].txtSubject
            binding.itemLocationFood.text = data[position].FoodLocation
            binding.itemPriceFood.text = "$" + data[position].txtPrice + " VIP"
            binding.itemFarfromusFood.text = data[position].txtDistance + "  miles from you"
            binding.itemRatingbarFood.rating = data[position].rating
            binding.itemVoteFood.text = data[position].vote.toString() + " Rating"

            Glide
                // to load image in the web
                .with(binding.root.context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4))
                .into(binding.imgMain)

            itemView.setOnClickListener {

                foodEvent.onFoodclicked(data[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {

                foodEvent.onFoodLongclicked(data[adapterPosition], adapterPosition)

                true

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return FoodViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        // this is a fun to hold the data
        holder.bindData(position)

    }
    override fun getItemCount(): Int {
        // this is a fun to know the count of data
        return data.size

    }

    fun addFood(newFood: Food) {

        // add food to list
        data.add(0, newFood)
        notifyItemInserted(0)

    }
    fun removeFood(oldFood: Food, oldPosition: Int) {

        // remove item from list
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)

    }
    fun updateFood(newFood: Food, pos: Int) {
        // update the item
        data.set(pos, newFood)
        notifyItemChanged(pos)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newFood: ArrayList<Food>) {
        // to set new data to list
        data.clear()
        data.addAll(newFood)
        notifyDataSetChanged()
    }

    interface FoodEvent {

        fun onFoodclicked(food: Food, position: Int)
        fun onFoodLongclicked(food: Food, pos: Int)

    }
}