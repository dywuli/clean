package com.android.arch.data.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AddressWithGoodsEntity(
    @Embedded val addressEntity: AddressEntity,
    @Relation(
        parentColumn = "addressId",
        entityColumn = "goodsId",
        associateBy = Junction(GoodsAddressCrossRef::class)
    )
    val goodsEntities: List<GoodsEntity>
)