package com.android.saleplatform.presenter;

import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_COMPLETE;

import android.util.Log;

import com.android.arch.data.repository.UserDataRepository;
import com.android.arch.data.room.entity.UserEntity;
import com.android.arch.domain.example.repository.UserRepository;
import com.android.arch.domain.interactor.DefaultObserver;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.domain.interactor.GetUserListByAddressId;
import com.android.saleplatform.domain.interactor.GetUserListByAddressIdGoodsId;
import com.android.saleplatform.domain.interactor.GetUserListByStatusAndAddress;
import com.android.saleplatform.domain.interactor.UserCUDOper;
import com.android.saleplatform.internal.di.PerActivity;
import com.android.saleplatform.mapper.UserModelDataMapper;
import com.android.saleplatform.ui.home.UserInfoViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wuli
 */
@PerActivity
public class UserInfoPresenter extends Presenter<UserInfoViewModel> {
    private final UserModelDataMapper userModelDataMapper;
    private final GetUserListByStatusAndAddress getUserListByStatusAndAddress;
    private final UserCUDOper userCUDOper;
    private AddressInfo addressInfo;
    private GetUserListByAddressId getUserListByAddressId;
    private UserDataRepository userDataRepository;

    @Inject
    public UserInfoPresenter(UserModelDataMapper userModelDataMapper, GetUserListByStatusAndAddress getUserListByStatusAndAddress, UserCUDOper userCUDOper, GetUserListByAddressId getUserListByAddressId, UserRepository userDataRepository) {
        this.userModelDataMapper = userModelDataMapper;
        this.getUserListByStatusAndAddress = getUserListByStatusAndAddress;
        this.userCUDOper = userCUDOper;
        this.getUserListByAddressId = getUserListByAddressId;
        this.userDataRepository = (UserDataRepository) userDataRepository;
    }

    public void getGetUserListByStatusAndAddress(AddressInfo addressInfo, int type, int value, int value2) {
        this.addressInfo = addressInfo;
        getUserListByStatusAndAddress.execute(new GetGetUserListByStatusAndAddressObserver(), new GetUserListByStatusAndAddress.Param(type, addressInfo.getAddressId(), value, value2, -1));
    }

    public void getGetUserListByStatusAndAddress(String searchName, AddressInfo addressInfo, int type, int value, int value2) {
        this.addressInfo = addressInfo;
        getUserListByStatusAndAddress.execute(new GetGetUserListByStatusAndAddressObserver(), new GetUserListByStatusAndAddress.Param(type, addressInfo.getAddressId(), value, value2, -1, searchName));
    }

    public void getGetUserListByStatus(String searchName, int type, int value, int value2) {
        if (type == TYPE_COMPLETE) {
            userDataRepository.getUserDetailByCompletedStatus(searchName, value).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        showAddressForUsersEntities(list);
                    }, error -> {
                        Log.e(UserInfoPresenter.class.getSimpleName(), " getGetUserListByStatus::" + error);
                    });
        } else {
            Schedulers.io().scheduleDirect(() -> {
                showAddressForUsersEntities(userDataRepository.queryUser(""));
            });
        }
//        userDataRepository.getUserDetailByCompletedStatus()
    }

    public void getGetUserListByStatus(int type, int value, int value2) {
        if (type == TYPE_COMPLETE) {
            userDataRepository.getUserDetailByCompletedStatus(value).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        showAddressForUsersEntities(list);
                    }, error -> {
                        Log.e(UserInfoPresenter.class.getSimpleName(), " getGetUserListByStatus2::" + error);
                    });
        } else {
            Schedulers.io().scheduleDirect(() -> {
                showAddressForUsersEntities(userDataRepository.queryUser(""));
            });
        }
    }

    public void showAddressForUsersEntities(List<UserEntity> userEntityList) {
        viewModel.getUserInfos().postValue(userModelDataMapper.UserEntities2UserList(userEntityList, addressInfo));
    }

    public long insertUser(UserInfo userInfo) {
        return userCUDOper.insert(userModelDataMapper.UserInfo2Entity(userInfo));
//        userCUDOper.execute(new InsertUserObserver(), new UserCUDOper.Param(UserCUDOper.TYPE_INSERT, userEntity));
    }

    public void updateUser(UserInfo userInfo) {
        userCUDOper.execute(new UpdateUserObserver(), new UserCUDOper.Param(UserCUDOper.TYPE_UPDATE, userModelDataMapper.UserInfo2Entity(userInfo)));
    }

    public void deleteUser(UserInfo userInfo) {
        userCUDOper.execute(new delUserObserver(), new UserCUDOper.Param(UserCUDOper.TYPE_DEL, userModelDataMapper.UserInfo2Entity(userInfo)));
    }

    public void getGetUserListByAddressIdGoodsId(long addrId) {
        getUserListByAddressId.execute(new DefaultObserver<List<UserEntity>>() {
            @Override
            public void onNext(List<UserEntity> userEntities) {
                super.onNext(userEntities);
                viewModel.getAllUses().setValue(userModelDataMapper.UserEntities2UserList(userEntities, addressInfo));
            }
        }, new GetUserListByAddressId.Param(addrId));
    }

    private final class GetGetUserListByStatusAndAddressObserver extends DefaultObserver<List<UserEntity>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<UserEntity> addressForUsersEntities) {
            UserInfoPresenter.this.showAddressForUsersEntities(addressForUsersEntities);
        }
    }

    private final class InsertUserObserver extends DefaultObserver<Long> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Long l) {
        }
    }

    private final class UpdateUserObserver extends DefaultObserver<Long> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Long l) {
        }
    }

    private final class delUserObserver extends DefaultObserver<Long> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Long l) {
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
    public void setViewModel(UserInfoViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
