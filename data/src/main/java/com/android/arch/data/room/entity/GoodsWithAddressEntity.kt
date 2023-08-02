package com.android.arch.data.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GoodsWithAddressEntity(
    @Embedded val goodsEntity: GoodsEntity,
    @Relation(
        parentColumn = "goodsId",
        entityColumn = "addressId",
        associateBy = Junction(GoodsAddressCrossRef::class)
    )
    val addressEntities: List<AddressEntity>
)