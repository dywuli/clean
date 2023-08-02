package com.android.arch.data.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "addressId"])
class AddressUserCrossRef{
    var userId: Long = 0
    var addressId: Long = 0
}