package com.example.foodfiestadelivery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfiestadelivery.Adapter.PendingOrderAdapter
import com.example.foodfiestadelivery.Model.OrderDetails
import com.example.foodfiestadelivery.databinding.ActivityOrdersBinding

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrdersActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {

    private lateinit var binding: ActivityOrdersBinding
    private var listofName: MutableList<String> = mutableListOf()
    private var listofTotalPrice: MutableList<String> = mutableListOf()
    private var listofImageFirstFoodOrder: MutableList<String> = mutableListOf()
    private var quantity: MutableList<Int> = mutableListOf()
    private var listofOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("DispatchedOrder")

        getOrderDetails()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listofOrderItem.clear() // Clear the list to prevent duplicate data

                for (userSnapshot in snapshot.children) { // 🔹 Iterate over each user
                    for (orderSnapshot in userSnapshot.children) { // 🔹 Iterate over each order under a user
                        val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                        orderDetails?.let {
                            listofOrderItem.add(it)
                        }
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OrdersActivity, "Failed to load orders", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun addDataToListForRecyclerView() {
        for (orderItem in listofOrderItem) {
            orderItem.userName?.let { listofName.add(it) }
            orderItem.totalPrice?.let { listofTotalPrice.add(it) }
            orderItem.FoodQuantities?.forEach { quantity.add(it) }
            orderItem.FoodImages?.filterNot { it.isEmpty() }?.forEach {
                listofImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(
            listofName,
            listofImageFirstFoodOrder,
            listofTotalPrice,
            quantity,
            this,
            this
        )
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrdersDetailsActivity::class.java)
        val userOrderDetails = listofOrderItem[position]
        intent.putExtra("userOrderDetails", userOrderDetails)
        startActivity(intent)

    }




}