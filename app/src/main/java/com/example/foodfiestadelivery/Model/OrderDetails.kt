package com.example.foodfiestadelivery.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

class OrderDetails() : Serializable {

    var orderId: String = ""
    var userUid: String? = null
    var userName: String? = null
    var FoodNames: MutableList<String>? = null
    var FoodImages: MutableList<String>? = null
    var FoodPrices: MutableList<String>? = null
    var FoodQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = false
    var paymentRecieved: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0
    var paymentMethod: String? = null
    var orderStatus: String? = null  // 🔹 Added this field

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentRecieved = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
        paymentMethod = parcel.readString()
        orderStatus = parcel.readString()  // 🔹 Read from Parcel
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemQuantities: ArrayList<Int>,
        address: String,
        totalAmount: String,
        phone: String,
        time: Long,
        itempushKey: String?,
        orderAccepted: Boolean,
        paymentRecieved: Boolean,
        paymentMethod: String,
        orderStatus: String  // 🔹 Added orderStatus to constructor
    ) : this() {
        this.userUid = userId
        this.userName = name
        this.FoodNames = foodItemName
        this.FoodImages = foodItemImage
        this.FoodPrices = foodItemPrice
        this.FoodQuantities = foodItemQuantities
        this.address = address
        this.phoneNumber = phone
        this.totalPrice = totalAmount
        this.currentTime = time
        this.itemPushKey = itempushKey
        this.orderAccepted = orderAccepted
        this.paymentRecieved = paymentRecieved
        this.paymentMethod = paymentMethod
        this.orderStatus = orderStatus  // 🔹 Assign value
    }

    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentRecieved) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
        parcel.writeString(orderStatus)  // 🔹 Write to Parcel
    }

    fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}
