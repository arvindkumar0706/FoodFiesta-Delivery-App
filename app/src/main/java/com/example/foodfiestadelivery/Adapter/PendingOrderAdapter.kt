package com.example.foodfiestadelivery.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.foodfiestadelivery.databinding.OrdersItemBinding

class PendingOrderAdapter(
    private val custName: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val foodPrice: MutableList<String>,
    private val quantity: MutableList<Int>,
    private val context: Context,
    private val itemClicked: OnItemClicked,


) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked {
        fun onItemClickListener(position: Int)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            OrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = custName.size

    inner class PendingOrderViewHolder(
        private val binding: OrdersItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isAccepted = false


        fun bind(position: Int) {
            binding.apply {
                CustomerName.text = custName[position]
                ItemQuantity.text = foodPrice[position]
                var UriString = foodImage[position]
                var uri = Uri.parse(UriString)
                Glide.with(context).load(uri).into(itemImage)


                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }

            }

        }

        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


    }
}