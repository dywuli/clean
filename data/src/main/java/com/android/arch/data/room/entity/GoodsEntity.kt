package com.android.arch.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "goods")
data class GoodsEntity(
    @PrimaryKey(autoGenerate = true)
    var goodsId: Long = 0,
    @ColumnInfo(name = "goodsName")
    var goodsName: String? = "",
    @ColumnInfo(name = "price")
    var price: String? = "",
    @ColumnInfo(name = "createDate")
    var createDate: String? = null,

    @ColumnInfo(name = "updateDate")
    var updateDate: String? = null
)