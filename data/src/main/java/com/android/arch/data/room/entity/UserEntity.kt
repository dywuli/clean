package com.android.arch.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0,
    var userAddressId: Long = 0,
    var goodsId: Long = 0,
    @ColumnInfo(name = "userName")
    var userName: String? = "",
    @ColumnInfo(name = "roomAddress")
    var roomAddress: String? = "",
    @ColumnInfo(name = "goodsCount")
    var goodsCount: Int = 0,
    @ColumnInfo(name = "payStatus")
    var payStatus: Int = 0, //0为未付;1已付
    @ColumnInfo(name = "takeStatus")
    var takeStatus: Int = 0, //0为未送;1已送
    @ColumnInfo(name = "completedStatus")
    var completedStatus: Int = 0, //0为未;1已
    @ColumnInfo(name = "createDate")
    var createDate: String? = null,
    @ColumnInfo(name = "updateDate")
    var updateDate: String? = null,
    @ColumnInfo(name = "totalMoney")
    var totalMoney: String? = "",
    @ColumnInfo(name = "desc")
    var desc: String? = ""
)