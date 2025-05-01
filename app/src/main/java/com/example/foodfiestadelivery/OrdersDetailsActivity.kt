package com.example.foodfiestadelivery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfiestadelivery.Adapter.OrderDetailsAdapter
import com.example.foodfiestadelivery.Model.OrderDetails
import com.example.foodfiestadelivery.databinding.ActivityOrdersDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class OrdersDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdersDetailsBinding

    private var userName: String? = null
    private var address: String? = null
    private var phoneNum: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var payment:String?=null
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getDataFromIntent()

        binding.deliveryBtn.setOnClickListener {
            markOrderAsCompleted()
        }

    }

    private fun markOrderAsCompleted() {
        val databaseRef = FirebaseDatabase.getInstance().reference
        val receivedOrderDetails = intent.getSerializableExtra("userOrderDetails") as OrderDetails
        val orderId = receivedOrderDetails.itemPushKey
        val userId = receivedOrderDetails.userUid

        if (userId != null && orderId != null) {
            val dispatchedRef = databaseRef.child("DispatchedOrder").child(userId).child(orderId)
            val completedRef = databaseRef.child("CompletedOrder").child(userId).child(orderId)

            val orderRef = database.reference.child("CustomersUser").child(userId)
                .child("BuyHistory").child(orderId)



            orderRef.child("orderStatus").setValue("Accepted")
            databaseOrderDetails.child(orderId).child("orderStatus").setValue("Completed")

            dispatchedRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val orderData = snapshot.value as MutableMap<String, Any>?
                    orderData?.set("orderStatus", "Completed")

                    val paymentRes=snapshot.value as MutableMap<String,Any>?
                    orderData?.set("paymentRecieved",true)



                    completedRef.setValue(orderData).addOnSuccessListener {
                        dispatchedRef.removeValue().addOnSuccessListener {
                            runOnUiThread {
                                binding.deliveryBtn.isEnabled = false
                                binding.deliveryBtn.text = "Delivered"
                                Toast.makeText(this, "Order delivered!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }






    private fun getDataFromIntent() {
        val recievceOrderDetails=intent.getSerializableExtra("userOrderDetails") as OrderDetails

        recievceOrderDetails?.let {orderDetails ->
            userName=recievceOrderDetails.userName
            foodNames=recievceOrderDetails.FoodNames as ArrayList<String>
            foodImages=recievceOrderDetails.FoodImages as ArrayList<String>
            foodQuantity=recievceOrderDetails.FoodQuantities as ArrayList<Int>
            address=recievceOrderDetails.address
            phoneNum=recievceOrderDetails.phoneNumber
            foodPrices=recievceOrderDetails.FoodPrices as ArrayList<String>
            totalPrice=recievceOrderDetails.totalPrice

            if (recievceOrderDetails.paymentMethod != "Online Payment"){
                payment="Collect Payment"
            }


            setUserDetails()
            setAdapter()
        }



    }

    private fun setAdapter() {
        binding.OrderDetailsRV.layoutManager=LinearLayoutManager(this)
        val adapter= OrderDetailsAdapter(this,foodNames,foodImages,foodQuantity,foodPrices)
        binding.OrderDetailsRV.adapter=adapter
    }

    private fun setUserDetails() {
        binding.name.text=userName
        binding.address.text=address
        binding.phone.text=phoneNum
        binding.totalprice.text=totalPrice
        binding.paymentType.text=payment




    }
}