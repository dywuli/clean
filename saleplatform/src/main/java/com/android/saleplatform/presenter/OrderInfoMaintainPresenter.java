package com.android.saleplatform.presenter;

import com.android.arch.data.repository.UserDataRepository;
import com.android.arch.data.room.entity.AddressWithGoodsEntity;
import com.android.arch.data.room.entity.GoodsEntity;
import com.android.arch.data.room.entity.GoodsWithAddressEntity;
import com.android.arch.domain.example.repository.UserRepository;
import com.android.arch.domain.interactor.DefaultObserver;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.AddressWithUserInfos;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.domain.interactor.GetAddressListByGoodsId;
import com.android.saleplatform.domain.interactor.GetAddressWithGoodsById;
import com.android.saleplatform.domain.interactor.GoodsListReq;
import com.android.saleplatform.internal.di.PerActivity;
import com.android.saleplatform.mapper.UserModelDataMapper;
import com.android.saleplatform.ui.infos.InfoViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author wuli
 */
@PerActivity
public class OrderInfoMaintainPresenter extends Presenter<InfoViewModel> {
    private final UserModelDataMapper userModelDataMapper;
    private final GetAddressListByGoodsId getAddressListByGoodsIdUseCase;
    private final GoodsListReq goodsListReqUseCase;
    private final GetAddressWithGoodsById getAddressWithGoodsById;
    private GoodsInfo curGoodInfo;
    private UserDataRepository userDataRepository;

    @Inject
    public OrderInfoMaintainPresenter(UserModelDataMapper userModelDataMapper, GetAddressListByGoodsId getAddressListByGoodsIdUseCase, GoodsListReq goodsListReqUseCase, GetAddressWithGoodsById getAddressWithGoodsById, UserRepository userDataRepository) {
        this.userModelDataMapper = userModelDataMapper;
        this.getAddressListByGoodsIdUseCase = getAddressListByGoodsIdUseCase;
        this.goodsListReqUseCase = goodsListReqUseCase;
        this.getAddressWithGoodsById = getAddressWithGoodsById;
        this.userDataRepository = (UserDataRepository) userDataRepository;
    }


    public long insertGoods(GoodsInfo goodsInfo) {
        return userDataRepository.insertGoods(userModelDataMapper.GoodInfo2GoodEntity(goodsInfo));
    }

    public long insertAddress(AddressInfo addressInfo) {
        return userDataRepository.insertAddress(userModelDataMapper.AddressInfo2AddressEntity(addressInfo));
    }

    public long insertUserInfo(UserInfo userInfo) {
        return userDataRepository.insertUser(userModelDataMapper.UserInfo2Entity(userInfo));
    }

    public void getGoodList() {
        goodsListReqUseCase.execute(new OrderInfoMaintainPresenter.GoodsListObserver(), null);

    }

    public void getUserListGroupByAddrByGoodId(GoodsInfo goodsInfo) {
        this.curGoodInfo = goodsInfo;
        getAddressWithGoodsById.execute(new OrderInfoMaintainPresenter.UserListGroupByAddrByGoodIdObserver(), goodsInfo.getGoodsId());
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
            OrderInfoMaintainPresenter.this.showGoodsList(goodsEntities);
        }
    }

    private void showAddressForUsersEntities(List<AddressWithUserInfos> addressWithUserInfos) {
        if (!addressWithUserInfos.isEmpty()) {
            viewModel.getAddressInfoWithUsersList().postValue(addressWithUserInfos);
        }
    }

    private final class UserListGroupByAddrByGoodIdObserver extends DefaultObserver<List<GoodsWithAddressEntity>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<GoodsWithAddressEntity> addressForUsersEntities) {
            if (addressForUsersEntities != null && !addressForUsersEntities.isEmpty()) {
                List<AddressInfo> addressInfos = userModelDataMapper.goodsWithAddressList2AddressInfoList(addressForUsersEntities);
                List<AddressWithUserInfos> list1 = new ArrayList<>();
                for (AddressInfo info : addressInfos) {
                    list1.add(new AddressWithUserInfos(info, null));
                }
                showAddressForUsersEntities(list1);
            }
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
    public void setViewModel(InfoViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
