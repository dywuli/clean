package com.android.arch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.arch.data.room.dao.AddressDao
import com.android.arch.data.room.dao.GoodsDao
import com.android.arch.data.room.dao.UserDao
import com.android.arch.data.room.entity.AddressEntity
import com.android.arch.data.room.entity.GoodsAddressCrossRef
import com.android.arch.data.room.entity.GoodsEntity
import com.android.arch.data.room.entity.UserEntity


@Database(
    entities = [UserEntity::class, GoodsEntity::class, AddressEntity::class,GoodsAddressCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getGoodsDao(): GoodsDao
    abstract fun getAddressDao(): AddressDao

}