package com.android.arch.data.repository.datasource;

import androidx.annotation.NonNull;

import com.android.arch.data.cache.UserCache;
import com.android.arch.data.cache.UserCacheImpl;
import com.android.arch.data.room.RoomDB;
import com.android.arch.data.room.dao.AddressDao;
import com.android.arch.data.room.dao.GoodsDao;
import com.android.arch.data.room.dao.UserDao;
import com.android.arch.data.room.entity.AddressEntity;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.data.room.entity.AddressWithUserEntity;
import com.android.arch.data.room.entity.GoodsAddressCrossRef;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.data.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class DBUserDataStore implements UserDataStore<UserEntity>, AddressDao, GoodsDao, UserDao {

    private final UserCache userCache;
    private final RoomDB db;

    public DBUserDataStore(UserCache userCache) {
        this.userCache = userCache;
        db = ((UserCacheImpl) userCache).getRoomDB();
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return null;
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return null;
    }

    ////addressDao
    @Override
    public long insertAddress(@NonNull AddressEntity address) {
        return db.getAddressDao().insertAddress(address);
    }

    @Override
    public void updateAddress(@NonNull AddressEntity address) {
        db.getAddressDao().updateAddress(address);
    }

    @Override
    public void deleteAddress(@NonNull AddressEntity address) {
        db.getAddressDao().deleteAddress(address);
    }


    @NonNull
    @Override
    public Single<List<AddressEntity>> getAddressByName(@NonNull String name) {
        return db.getAddressDao().getAddressByName(name);
    }

    @NonNull
    @Override
    public Single<AddressEntity> getAddressById(long addressId) {
        return db.getAddressDao().getAddressById(addressId);
    }


    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsByName(@NonNull String name) {
        return db.getAddressDao().getAddressWitGoodsByName(name);
    }

    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsById(long goodIs) {
        return db.getAddressDao().getAddressWitGoodsById(goodIs);
    }

    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsByIdList(@NonNull List<Long> goodIs) {
        return db.getAddressDao().getAddressWitGoodsByIdList(goodIs);
    }

    @Override
    public long insertAddressWithGoods(@NonNull GoodsAddressCrossRef goodsAddressCrossRef) {
        return db.getAddressDao().insertAddressWithGoods(goodsAddressCrossRef);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserByIdList(@NonNull List<Long> addressIds) {
        return db.getAddressDao().getAddressWitUserByIdList(addressIds);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserById(long addressId) {
        return db.getAddressDao().getAddressWitUserById(addressId);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserByName(@NonNull String name) {
        return db.getAddressDao().getAddressWitUserByName(name);
    }

    ////goodsDao
    @Override
    public long insertGoods(@NonNull GoodsEntity goods) {
        return db.getGoodsDao().insertGoods(goods);
    }

    @Override
    public void updateGoods(@NonNull GoodsEntity goods) {
        db.getGoodsDao().updateGoods(goods);
    }

    @Override
    public void deleteGoods(@NonNull GoodsEntity goods) {
        db.getGoodsDao().deleteGoods(goods);
    }

    @NonNull
    @Override
    public Single<GoodsEntity> getGoodsById(long goodsId) {
        return db.getGoodsDao().getGoodsById(goodsId);
    }

    @NonNull
    @Override
    public Single<List<GoodsWithAddressEntity>> getAddressListByGoodsId(long goodsId) {
        return db.getGoodsDao().getAddressListByGoodsId(goodsId);
    }

    @NonNull
    @Override
    public Single<List<GoodsEntity>> getGoods() {
        return db.getGoodsDao().getGoods();
    }

    @NonNull
    @Override
    public Single<List<GoodsWithAddressEntity>> getAddressListByGoodsName(@NonNull String goodsName) {
        return db.getGoodsDao().getAddressListByGoodsName(goodsName);
    }

    @NonNull
    @Override
    public Single<List<GoodsEntity>> getGoodsByName(@NonNull String goodsName) {
        return db.getGoodsDao().getGoodsByName(goodsName);
    }

    ///UserDao
    @Override
    public long insertUser(@NonNull UserEntity user) {
        return db.getUserDao().insertUser(user);
    }

    @Override
    public void updateUser(@NonNull UserEntity user) {
        db.getUserDao().updateUser(user);
    }

    @Override
    public void deleteUser(@NonNull UserEntity userEntity) {
        db.getUserDao().deleteUser(userEntity);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressId(long addressId, long goodsId) {
        return db.getUserDao().getUserDetailByAddressId(addressId, goodsId);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdOrGoodsId(long addressId, long goodsId) {
        return db.getUserDao().getUserDetailByAddressIdOrGoodsId(addressId, goodsId);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdCompleted(long addressId, long goodsId, int completedStatus) {
        return db.getUserDao().getUserDetailByAddressIdCompleted(addressId, goodsId, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByCompletedStatus(int completedStatus) {
        return db.getUserDao().getUserDetailByCompletedStatus(completedStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByCompletedStatus(String name, int completedStatus) {
        return db.getUserDao().getUserDetailByCompletedStatus(name, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeStatus(long addressId, long goodsId, int takeStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeStatus(addressId, goodsId, takeStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeStatus(long addressId, int takeStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeStatus(addressId, takeStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByTakeStatus(int takeStatus) {
        return db.getUserDao().getUserDetailByTakeStatus(takeStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdPayStatus(long addressId, long goodsId, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdPayStatus(addressId, goodsId, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdPayStatus(long addressId, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdPayStatus(addressId, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByPayStatus(int payStatus) {
        return db.getUserDao().getUserDetailByPayStatus(payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByTakeAndPayStatus(int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByTakeAndPayStatus(takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByTakeOrPayStatus(int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByTakeOrPayStatus(takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(long addressId, long goodsId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeAndPayStatus(addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(long addressId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeAndPayStatus(addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(long addressId, long goodsId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeOrPayStatus(addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(long addressId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeOrPayStatus(addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public List<UserEntity> queryUser(@NonNull String searchName) {
        return db.getUserDao().queryUser(searchName);
    }

    @NonNull
    @Override
    public List<UserEntity> queryUserByAddressId(@NonNull String searchName, long addressId, long goodsId) {
        return db.getUserDao().queryUserByAddressId(searchName, addressId, goodsId);
    }

    @NonNull
    @Override
    public List<UserEntity> queryUserByAddressIdAndGoodIs(long addressId, long goodsId) {
        return db.getUserDao().queryUserByAddressIdAndGoodIs(addressId, goodsId);
    }

    @NonNull
    @Override
    public List<UserEntity> queryUserByAddressId(long addressId) {
        return db.getUserDao().queryUserByAddressId(addressId);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(@NonNull String searchName, long addressId, long goodsId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeAndPayStatus(searchName, addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(@NonNull String searchName, long addressId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeAndPayStatus(searchName, addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(@NonNull String searchName, long addressId, long goodsId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeOrPayStatus(searchName, addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(@NonNull String searchName, long addressId, int takeStatus, int payStatus) {
        return db.getUserDao().getUserDetailByAddressIdTakeOrPayStatus(searchName, addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdCompleted(@NonNull String searchName, long addressId, long goodsId, int completedStatus) {
        return db.getUserDao().getUserDetailByAddressIdCompleted(searchName, addressId, goodsId, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<UserEntity>> getUserDetailByAddressIdCompleted(@NonNull String searchName, long addressId, int completedStatus) {
        return db.getUserDao().getUserDetailByAddressIdCompleted(searchName, addressId, completedStatus);
    }
}
