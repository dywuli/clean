package com.android.arch.data.room.dao

import androidx.room.*
import com.android.arch.data.room.entity.UserEntity
import io.reactivex.Single

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long


    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)


    @Transaction
    @Query("select * from user where userAddressId = :addressId and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressId(addressId: Long, goodsId: Long): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where (userAddressId = :addressId or goodsId=:goodsId) and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdOrGoodsId(addressId: Long, goodsId: Long): Single<List<UserEntity>>

    @Transaction
    @Query(
        "select * from user where userAddressId = :addressId and completedStatus = :completedStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc"
    )
    fun getUserDetailByAddressIdCompleted(
        addressId: Long, goodsId: Long,
        completedStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where completedStatus = :completedStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByCompletedStatus(
        completedStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query(
        "select * from user where (userName LIKE '%' || :searchName || '%') and  completedStatus = :completedStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc"
    )
    fun getUserDetailByCompletedStatus(
        searchName: String,
        completedStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and takeStatus = :takeStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeStatus(
        addressId: Long, goodsId: Long,
        takeStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and takeStatus = :takeStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeStatus(
        addressId: Long,
        takeStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and takeStatus = :takeStatus and payStatus=:payStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeAndPayStatus(
        addressId: Long, goodsId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and takeStatus = :takeStatus and payStatus=:payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeAndPayStatus(
        addressId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and (takeStatus = :takeStatus or payStatus=:payStatus) and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeOrPayStatus(
        addressId: Long, goodsId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and (takeStatus = :takeStatus or payStatus=:payStatus) and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeOrPayStatus(
        addressId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where takeStatus = :takeStatus and payStatus=:payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByTakeAndPayStatus(
        takeStatus: Int,
        payStatus: Int
    ): Single<List<UserEntity>>

    @Query("select * from user where takeStatus = :takeStatus or payStatus=:payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByTakeOrPayStatus(
        takeStatus: Int,
        payStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where takeStatus = :takeStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByTakeStatus(
        takeStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and payStatus = :payStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdPayStatus(
        addressId: Long, goodsId: Long,
        payStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where userAddressId = :addressId and payStatus = :payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdPayStatus(
        addressId: Long,
        payStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where payStatus = :payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByPayStatus(payStatus: Int): Single<List<UserEntity>>

    @Transaction
    @Query("SELECT * FROM user WHERE userName LIKE '%' || :searchName || '%'  and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun queryUser(searchName: String): List<UserEntity>

    @Transaction
    @Query("SELECT * FROM user WHERE (userName LIKE '%' || :searchName || '%') and userAddressId=:addressId and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun queryUserByAddressId(searchName: String, addressId: Long, goodsId: Long): List<UserEntity>

    @Transaction
    @Query("SELECT * FROM user WHERE userAddressId=:addressId and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun queryUserByAddressIdAndGoodIs(addressId: Long, goodsId: Long): List<UserEntity>

    @Transaction
    @Query("SELECT * FROM user WHERE userAddressId=:addressId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun queryUserByAddressId(addressId: Long): List<UserEntity>

    @Transaction
    @Query("select * from user where  (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and takeStatus = :takeStatus and payStatus=:payStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeAndPayStatus(
        searchName: String,
        addressId: Long, goodsId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where  (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and takeStatus = :takeStatus and payStatus=:payStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeAndPayStatus(
        searchName: String,
        addressId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where  (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and (takeStatus = :takeStatus or payStatus=:payStatus) and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeOrPayStatus(
        searchName: String,
        addressId: Long, goodsId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query("select * from user where  (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and (takeStatus = :takeStatus or payStatus=:payStatus) and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc")
    fun getUserDetailByAddressIdTakeOrPayStatus(
        searchName: String,
        addressId: Long,
        takeStatus: Int,
        payStatus: Int,
    ): Single<List<UserEntity>>

    @Transaction
    @Query(
        "select * from user where (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and completedStatus = :completedStatus and goodsId=:goodsId and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc"
    )
    fun getUserDetailByAddressIdCompleted(
        searchName: String,
        addressId: Long, goodsId: Long,
        completedStatus: Int
    ): Single<List<UserEntity>>

    @Transaction
    @Query(
        "select * from user where (userName LIKE '%' || :searchName || '%') and userAddressId = :addressId and completedStatus = :completedStatus and updateDate >= date('now', 'localtime', '-5 day') order by updateDate desc"
    )
    fun getUserDetailByAddressIdCompleted(
        searchName: String,
        addressId: Long,
        completedStatus: Int
    ): Single<List<UserEntity>>
}
