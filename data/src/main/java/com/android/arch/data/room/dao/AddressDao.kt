package com.android.arch.data.room.dao

import androidx.room.*
import com.android.arch.data.room.entity.*
import io.reactivex.Single

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(address: AddressEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddressWithGoods(goodsAddressCrossRef: GoodsAddressCrossRef): Long

    @Update
    fun updateAddress(address: AddressEntity)

    @Delete
    fun deleteAddress(entity: AddressEntity)

    @Transaction
    @Query("select * from address WHERE addressName LIKE '%' || :name || '%'  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressByName(name: String): Single<List<AddressEntity>>

    @Transaction
    @Query("select * from address WHERE addressName LIKE '%' || :name || '%'  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitGoodsByName(name: String): Single<List<AddressWithGoodsEntity>>

    @Transaction
    @Query("select * from address WHERE addressId=:addressId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitGoodsById(addressId: Long): Single<List<AddressWithGoodsEntity>>

    @Transaction
    @Query("select * from address WHERE addressId=:addressId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressById(addressId: Long): Single<AddressEntity>


    @Transaction
    @Query("select * from address WHERE addressId in (:addressIds) and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitGoodsByIdList(addressIds: List<Long>): Single<List<AddressWithGoodsEntity>>

    @Transaction
    @Query("select * from address WHERE addressId in (:addressIds) and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitUserByIdList(addressIds: List<Long>): Single<List<AddressWithUserEntity>>

    @Transaction
    @Query("select * from address WHERE addressId=:addressId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitUserById(addressId: Long): Single<List<AddressWithUserEntity>>
    @Transaction
    @Query("select * from address WHERE addressName LIKE '%' || :name || '%'  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressWitUserByName(name: String): Single<List<AddressWithUserEntity>>

}
