package com.android.saleplatform.presenter;

import android.os.Build;
import android.util.Log;

import com.android.arch.data.repository.UserDataRepository;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.domain.example.repository.UserRepository;
import com.android.arch.domain.interactor.DefaultObserver;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.AddressWithUserInfos;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.domain.interactor.GetAddressListByGoodsId;
import com.android.saleplatform.domain.interactor.GetUserListGroupByAddrByAddressName;
import com.android.saleplatform.domain.interactor.GoodsListReq;
import com.android.saleplatform.internal.di.PerActivity;
import com.android.saleplatform.mapper.UserModelDataMapper;
import com.android.saleplatform.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author wuli
 */
@PerActivity
public class HomePresenter extends Presenter<HomeViewModel> {

    private final UserModelDataMapper userModelDataMapper;
    private final GetAddressListByGoodsId getAddressListByGoodsIdUseCase;
    private final GoodsListReq goodsListReqUseCase;
    private final GetUserListGroupByAddrByAddressName getUserListGroupByAddrByAddressName;
    private GoodsInfo curGoodInfo;

    private UserDataRepository userDataRepository;
    private List<AddressInfo> addressInfoList = new ArrayList<>();

    @Inject
    public HomePresenter(UserModelDataMapper userModelDataMapper, GetAddressListByGoodsId getAddressListByGoodsIdUseCase, GoodsListReq goodsListReqUseCase, GetUserListGroupByAddrByAddressName getUserListGroupByAddrByGoodNameUseCase, UserRepository userDataRepository) {
        this.userModelDataMapper = userModelDataMapper;
        this.getAddressListByGoodsIdUseCase = getAddressListByGoodsIdUseCase;
        this.goodsListReqUseCase = goodsListReqUseCase;
        this.getUserListGroupByAddrByAddressName = getUserListGroupByAddrByGoodNameUseCase;
        this.userDataRepository = (UserDataRepository) userDataRepository;
    }


    public void getGoodList() {
        goodsListReqUseCase.execute(new GoodsListObserver(), null);

    }

    @Deprecated
    public void getAddressWithUserByGoodsName(String name) {
        userDataRepository.getAddressListByGoodsName(name).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    if (list != null && !list.isEmpty()) {
                        addressInfoList = userModelDataMapper.goodsWithAddressList2AddressInfoList(list);
                        List<AddressWithUserInfos> list1 = new ArrayList<>();
                        for (AddressInfo info : addressInfoList) {
                            list1.add(new AddressWithUserInfos(info, null));
                        }
                        showAddressForUsersEntities(list1);
                    }
                }, throwable -> Log.e("wuli", "" + throwable));
    }

    public void getUserListGroupByAddrByAddressName(String name) {
        userDataRepository.getAddressWitUserByName(name).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(addressWithUserEntities -> {
                    List<AddressWithUserInfos> list = new ArrayList<>();
                    if (addressWithUserEntities != null && !addressWithUserEntities.isEmpty()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            addressWithUserEntities.forEach(addressWithUserEntity -> {
                                AddressInfo addressInfo = userModelDataMapper.AddressEntity2AddressInfo(addressWithUserEntity.getAddressEntity(), null);
                                list.add(new AddressWithUserInfos(addressInfo, userModelDataMapper.UserEntities2UserList(addressWithUserEntity.getUserEntityList(), addressInfo)));
                            });
                        }
                        showAddressForUsersEntities(list);
                    }
                }, throwable -> {
                    Log.e("wuli", "getUserListGroupByAddrByAddressName::" + throwable);
                });
    }

    private void showGoodsList(List<GoodsEntity> goodsEntities) {
        viewModel.getGoodsInfos().postValue(userModelDataMapper.GoodsEntities2GoodList(goodsEntities));

    }

    private final class GoodsListObserver extends DefaultObserver<List<GoodsEntity>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<GoodsEntity> goodsEntities) {
            HomePresenter.this.showGoodsList(goodsEntities);
        }
    }

    private void showAddressForUsersEntities(List<AddressWithUserInfos> addressWithUserInfos) {
        if (!addressWithUserInfos.isEmpty()) {
            viewModel.getAddressInfoWithUsersList().postValue(addressWithUserInfos);
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setViewModel(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
