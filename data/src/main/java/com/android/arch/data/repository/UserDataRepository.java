/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.arch.data.repository;

import androidx.annotation.NonNull;

import com.android.arch.data.entity.UserEntity;
import com.android.arch.data.entity.mapper.UserEntityDataMapper;
import com.android.arch.data.repository.datasource.DBUserDataStore;
import com.android.arch.data.repository.datasource.UserDataStore;
import com.android.arch.data.repository.datasource.UserDataStoreFactory;
import com.android.arch.data.room.dao.AddressDao;
import com.android.arch.data.room.dao.GoodsDao;
import com.android.arch.data.room.dao.UserDao;
import com.android.arch.data.room.entity.AddressEntity;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.data.room.entity.AddressWithUserEntity;
import com.android.arch.data.room.entity.GoodsAddressCrossRef;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.domain.example.User;
import com.android.arch.domain.example.repository.UserRepository;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository, AddressDao, GoodsDao, UserDao {

    private final UserDataStoreFactory userDataStoreFactory;
    private final UserEntityDataMapper userEntityDataMapper;

    /**
     * Constructs a {@link UserRepository}.
     *
     * @param dataStoreFactory     A factory to construct different data source implementations.
     * @param userEntityDataMapper {@link UserEntityDataMapper}.
     */
    @Inject
    UserDataRepository(UserDataStoreFactory dataStoreFactory,
                       UserEntityDataMapper userEntityDataMapper) {
        this.userDataStoreFactory = dataStoreFactory;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<List<User>> users() {
        //we always get all users from the cloud
        final UserDataStore<UserEntity> userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.userEntityList().map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> user(int userId) {
        final UserDataStore<UserEntity> userDataStore = this.userDataStoreFactory.create(userId);
        return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform);
    }


    @Override
    public long insertAddress(@NonNull AddressEntity address) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.insertAddress(address);
    }

    @Override
    public void updateAddress(@NonNull AddressEntity address) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.updateAddress(address);

    }

    @Override
    public void deleteAddress(@NonNull AddressEntity address) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.deleteAddress(address);

    }

    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsById(long goodIs) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitGoodsById(goodIs);
    }

    @NonNull
    @Override
    public Single<AddressEntity> getAddressById(long goodIs) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressById(goodIs);
    }

    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsByIdList(@NonNull List<Long> goodIs) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitGoodsByIdList(goodIs);
    }

    @Override
    public long insertGoods(@NonNull GoodsEntity goods) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.insertGoods(goods);
    }

    @Override
    public void updateGoods(@NonNull GoodsEntity goods) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.updateGoods(goods);
    }

    @Override
    public void deleteGoods(@NonNull GoodsEntity goods) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.deleteGoods(goods);
    }

    @NonNull
    @Override
    public Single<List<GoodsWithAddressEntity>> getAddressListByGoodsId(long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressListByGoodsId(goodsId);
    }

    @NonNull
    @Override
    public Single<GoodsEntity> getGoodsById(long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getGoodsById(goodsId);
    }


    @NonNull
    @Override
    public Single<List<GoodsEntity>> getGoods() {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getGoods();
    }

    @Override
    public long insertUser(@NonNull com.android.arch.data.room.entity.UserEntity user) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.insertUser(user);
    }

    @Override
    public void updateUser(@NonNull com.android.arch.data.room.entity.UserEntity user) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.insertUser(user);
    }

    @Override
    public void deleteUser(@NonNull com.android.arch.data.room.entity.UserEntity userEntity) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        dbUserDataStore.deleteUser(userEntity);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressId(long addressId, long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressId(addressId, goodsId);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdOrGoodsId(long addressId, long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdOrGoodsId(addressId, goodsId);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdCompleted(long addressId, long goodsId, int completedStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdCompleted(addressId, goodsId, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByCompletedStatus(int completedStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByCompletedStatus(completedStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByCompletedStatus(String name, int completedStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByCompletedStatus(name, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeStatus(long addressId, long goodsId, int takeStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeStatus(addressId, goodsId, takeStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeStatus(long addressId, int takeStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeStatus(addressId, takeStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByTakeStatus(int takeStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByTakeStatus(takeStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdPayStatus(long addressId, long goodsId, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdPayStatus(addressId, goodsId, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdPayStatus(long addressId, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdPayStatus(addressId, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByPayStatus(int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByPayStatus(payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(long addressId, long goodsId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeAndPayStatus(addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(long addressId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeAndPayStatus(addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(long addressId, long goodsId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeOrPayStatus(addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(long addressId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeOrPayStatus(addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByTakeAndPayStatus(int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByTakeAndPayStatus(takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByTakeOrPayStatus(int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByTakeOrPayStatus(takeStatus, payStatus);
    }


    @NonNull
    @Override
    public Single<List<AddressEntity>> getAddressByName(@NonNull String name) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressByName(name);
    }

    @NonNull
    @Override
    public Single<List<AddressWithGoodsEntity>> getAddressWitGoodsByName(@NonNull String name) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitGoodsByName(name);
    }

    @NonNull
    @Override
    public Single<List<GoodsWithAddressEntity>> getAddressListByGoodsName(@NonNull String goodsName) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressListByGoodsName(goodsName);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserByIdList(@NonNull List<Long> addressIds) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitUserByIdList(addressIds);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserById(long addressId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitUserById(addressId);
    }

    @NonNull
    @Override
    public Single<List<AddressWithUserEntity>> getAddressWitUserByName(@NonNull String name) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getAddressWitUserByName(name);
    }

    @NonNull
    @Override
    public Single<List<GoodsEntity>> getGoodsByName(@NonNull String goodsName) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getGoodsByName(goodsName);
    }


    @NonNull
    @Override
    public List<com.android.arch.data.room.entity.UserEntity> queryUser(@NonNull String searchName) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.queryUser(searchName);
    }

    @NonNull
    @Override
    public List<com.android.arch.data.room.entity.UserEntity> queryUserByAddressId(@NonNull String searchName, long addressId, long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.queryUserByAddressId(searchName, addressId, goodsId);
    }

    @NonNull
    @Override
    public List<com.android.arch.data.room.entity.UserEntity> queryUserByAddressIdAndGoodIs(long addressId, long goodsId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.queryUserByAddressIdAndGoodIs(addressId, goodsId);
    }

    @NonNull
    @Override
    public List<com.android.arch.data.room.entity.UserEntity> queryUserByAddressId(long addressId) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.queryUserByAddressId(addressId);
    }

    @Override
    public long insertAddressWithGoods(@NonNull GoodsAddressCrossRef goodsAddressCrossRef) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.insertAddressWithGoods(goodsAddressCrossRef);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(@NonNull String searchName, long addressId, long goodsId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeAndPayStatus(searchName, addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeAndPayStatus(@NonNull String searchName, long addressId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeAndPayStatus(searchName, addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(@NonNull String searchName, long addressId, long goodsId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeOrPayStatus(searchName, addressId, goodsId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdTakeOrPayStatus(@NonNull String searchName, long addressId, int takeStatus, int payStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdTakeOrPayStatus(searchName, addressId, takeStatus, payStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdCompleted(@NonNull String searchName, long addressId, long goodsId, int completedStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdCompleted(searchName, addressId, goodsId, completedStatus);
    }

    @NonNull
    @Override
    public Single<List<com.android.arch.data.room.entity.UserEntity>> getUserDetailByAddressIdCompleted(@NonNull String searchName, long addressId, int completedStatus) {
        DBUserDataStore dbUserDataStore = userDataStoreFactory.createDBStore();
        return dbUserDataStore.getUserDetailByAddressIdCompleted(searchName, addressId, completedStatus);
    }

}
