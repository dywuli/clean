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
package com.android.saleplatform.mapper;

import com.android.arch.data.room.entity.AddressEntity;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.data.room.entity.UserEntity;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.AddressWithUserInfos;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * presentation layer.
 */
@PerActivity
public class UserModelDataMapper {

    @Inject
    public UserModelDataMapper() {
    }

    public List<GoodsInfo> GoodsEntities2GoodList(List<GoodsEntity> goodsEntities) {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        if (goodsEntities != null && !goodsEntities.isEmpty()) {
            for (GoodsEntity goodsEntity : goodsEntities) {
                goodsInfoList.add(GoodsEntity2GoodsInfo(goodsEntity));
            }
        }
        return goodsInfoList;
    }

    public GoodsInfo GoodsEntity2GoodsInfo(GoodsEntity goodsEntity) {
        return new GoodsInfo(goodsEntity.getGoodsName(), Double.parseDouble(goodsEntity.getPrice()), goodsEntity.getGoodsId(), goodsEntity.getCreateDate(), goodsEntity.getUpdateDate());
    }

    public List<AddressInfo> AddressEntities2AddressList(List<AddressEntity> addressEntities, GoodsInfo goodsInfo) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        if (addressEntities != null && !addressEntities.isEmpty()) {
            for (AddressEntity addressEntity : addressEntities) {
                addressInfos.add(AddressEntity2AddressInfo(addressEntity, goodsInfo));
            }
        }
        return addressInfos;
    }

    public AddressInfo AddressEntity2AddressInfo(AddressEntity addressEntity, GoodsInfo goodsInfo) {
        return new AddressInfo(addressEntity.getAddressId(), addressEntity.getAddressName(), goodsInfo, addressEntity.getCreateDate(), addressEntity.getUpdateDate());
    }

    public List<UserInfo> UserEntities2UserList(List<UserEntity> userEntities, AddressInfo addressInfo) {
        List<UserInfo> userInfos = new ArrayList<>();
        if (userEntities != null && !userEntities.isEmpty()) {
            for (UserEntity userEntity : userEntities) {
                userInfos.add(UserEntity2UserInfo(userEntity, addressInfo));
            }
        }
        return userInfos;
    }

    public UserInfo UserEntity2UserInfo(UserEntity userEntity, AddressInfo addressInfo) {
        return new UserInfo(userEntity.getUserId(), userEntity.getGoodsId(), userEntity.getUserAddressId(), addressInfo != null ? addressInfo.getGoodsInfo() : null, addressInfo, userEntity.getUserName(),
                userEntity.getRoomAddress(), userEntity.getGoodsCount(), userEntity.getPayStatus(), userEntity.getTakeStatus(), userEntity.getCompletedStatus(),
                userEntity.getCreateDate(), userEntity.getUpdateDate(), userEntity.getTotalMoney(), userEntity.getDesc());

    }

    public UserEntity UserInfo2Entity(UserInfo userInfo) {
        return new UserEntity(userInfo.getId(), userInfo.getAddressId(), userInfo.getGoodsId(), userInfo.getUserName(), userInfo.getRoomAddress(), userInfo.getCount(),
                userInfo.getPayStatus(), userInfo.getTakeStatus(), userInfo.getCompletedStatus(), userInfo.getCreateDate(), userInfo.getUpdateDate(), userInfo.getTotalMoney(), userInfo.getDesc());
    }

    public GoodsEntity GoodInfo2GoodEntity(GoodsInfo goodsInfo) {
        return new GoodsEntity(goodsInfo.getGoodsId(), goodsInfo.getGoodsName(), "" + goodsInfo.getPrice(), goodsInfo.getCreateDate(), goodsInfo.getUpdateDate());
    }

    public AddressEntity AddressInfo2AddressEntity(AddressInfo addressInfo) {
        return new AddressEntity(addressInfo.getAddressId(), addressInfo.getAddressName(), addressInfo.getCreateDate(), addressInfo.getUpdateDate());
    }

    public List<AddressInfo> addressWithGooodsList2AddressInfoList(List<AddressWithGoodsEntity> addressWithGoodsEntities) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        if (addressWithGoodsEntities != null && !addressWithGoodsEntities.isEmpty()) {
            for (AddressWithGoodsEntity addressWithGoodsEntity : addressWithGoodsEntities) {
                addressInfos.addAll(AddressWithGoods2AddressInfo(addressWithGoodsEntity));
            }
        }
        return addressInfos;
    }

    public List<AddressInfo> goodsWithAddressList2AddressInfoList(List<GoodsWithAddressEntity> goodsWithAddressEntities) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        if (goodsWithAddressEntities != null && !goodsWithAddressEntities.isEmpty()) {
            for (GoodsWithAddressEntity addressWithGoodsEntity : goodsWithAddressEntities) {
                addressInfos.addAll(GoodsWithAddress2AddressInfo(addressWithGoodsEntity));
            }
        }
        return addressInfos;
    }

    public List<AddressInfo> GoodsWithAddress2AddressInfo(GoodsWithAddressEntity goodsWithAddressEntity) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        GoodsEntity goods = goodsWithAddressEntity.getGoodsEntity();
        List<AddressEntity> addressEntities = goodsWithAddressEntity.getAddressEntities();

        if (addressEntities != null && !addressEntities.isEmpty()) {
            for (AddressEntity addressEntity : addressEntities) {
                addressInfos.add(AddressEntity2AddressInfo(addressEntity, GoodsEntity2GoodsInfo(goods)));
            }
        }
        return addressInfos;
    }

    public List<AddressInfo> AddressWithGoods2AddressInfo(AddressWithGoodsEntity addressWithGoodsEntity) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        AddressEntity addressEntity = addressWithGoodsEntity.getAddressEntity();
        List<GoodsEntity> goodsEntities = addressWithGoodsEntity.getGoodsEntities();

        if (goodsEntities != null && !goodsEntities.isEmpty()) {
            for (GoodsEntity goods : goodsEntities) {
                addressInfos.add(AddressEntity2AddressInfo(addressEntity, GoodsEntity2GoodsInfo(goods)));
            }
        } else {
            addressInfos.add(AddressEntity2AddressInfo(addressEntity, null));
        }
        return addressInfos;
    }
}
