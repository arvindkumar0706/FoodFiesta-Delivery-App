package com.example.foodfiestadelivery.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodfiestadelivery.databinding.OrderDetailsItemBinding

class OrderDetailsAdapter(
    private var context: Context,
    private var foodNames: ArrayList<String>,
    private var foodImages: ArrayList<String>,
    private var foodQuantity: ArrayList<Int>,
    private var foodPrices: ArrayList<String>
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding=
            OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  OrderDetailsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodNames.size



    inner class OrderDetailsViewHolder(
        private val binding: OrderDetailsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text=foodNames[position]
                ItemQuantity.text=foodQuantity[position].toString()
                val uristring=foodImages[position]
                val uri=Uri.parse(uristring)
                Glide.with(context).load(uri).into(foodImage)
                foodPrice.text=foodPrices[position]
            }
        }

    }
}