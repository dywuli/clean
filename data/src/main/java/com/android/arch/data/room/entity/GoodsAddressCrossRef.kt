package com.android.arch.data.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["goodsId", "addressId"])
class GoodsAddressCrossRef{
    var goodsId: Long = 0
    var addressId: Long = 0
}