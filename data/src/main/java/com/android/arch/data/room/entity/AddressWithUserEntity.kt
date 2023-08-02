package com.android.arch.data.room.entity

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation

class AddressWithUserEntity (
    @Embedded
    val addressEntity:AddressEntity,
    @Relation(parentColumn = "addressId", entityColumn = "userAddressId")
    val userEntityList: List<UserEntity>
)