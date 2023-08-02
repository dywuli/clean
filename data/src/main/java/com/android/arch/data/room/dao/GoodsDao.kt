package com.android.arch.data.room.dao

import androidx.room.*
import com.android.arch.data.room.entity.GoodsEntity
import com.android.arch.data.room.entity.GoodsWithAddressEntity
import io.reactivex.Single

@Dao
interface GoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(goods: GoodsEntity): Long

    @Update
    fun updateGoods(goods: GoodsEntity)

    @Delete
    fun deleteGoods(goods: GoodsEntity)

    @Transaction
    @Query("select * from goods where goodsId =:goodsId  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressListByGoodsId(goodsId: Long): Single<List<GoodsWithAddressEntity>>

    @Transaction
    @Query("select * from goods where goodsId =:goodsId  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getGoodsById(goodsId: Long): Single<GoodsEntity>

    @Transaction
    @Query("select * from goods  WHERE goodsName LIKE '%' || :goodsName || '%'  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getAddressListByGoodsName(goodsName: String): Single<List<GoodsWithAddressEntity>>

    @Transaction
    @Query("select * from goods where updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getGoods(): Single<List<GoodsEntity>>

    @Transaction
    @Query("select * from goods WHERE goodsName LIKE '%' || :goodsName || '%' and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getGoodsByName(goodsName: String): Single<List<GoodsEntity>>


}
