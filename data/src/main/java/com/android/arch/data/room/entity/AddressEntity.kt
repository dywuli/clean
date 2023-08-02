package com.android.arch.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    var addressId: Long,
    @ColumnInfo(name = "addressName")
    var addressName: String? = "",
    @ColumnInfo(name = "createDate")
    var createDate: String? = null,
    @ColumnInfo(name = "updateDate")
    var updateDate: String? = null
) {

}