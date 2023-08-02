package com.android.saleplatform.domain.interactor;

import com.android.arch.data.room.dao.UserDao;
import com.android.arch.data.room.entity.UserEntity;
import com.android.arch.domain.executor.PostExecutionThread;
import com.android.arch.domain.executor.ThreadExecutor;
import com.android.arch.domain.interactor.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author wuli
 */
public class GetUserListByStatusAndAddress extends UseCase<List<UserEntity>, GetUserListByStatusAndAddress.Param> {
    private UserDao userDao;
    public static final int TYPE_PAY = 0;
    public static final int TYPE_TAKE = 1;
    public static final int TYPE_COMPLETE = 2;
    public static final int TYPE_TAKEN_PAY = 3;
    public static final int TYPE_TAK_OR_PAY = 4;

    @Inject
    protected GetUserListByStatusAndAddress(UserDao userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userDao = userRepository;
    }

    @Override
    protected Observable<List<UserEntity>> buildUseCaseObservable(Param param) {
        switch (param.type) {
            case TYPE_PAY:
                if (param.goodId == -1) {
                    return userDao.getUserDetailByAddressIdPayStatus(param.addressId, param.status).toObservable();
                } else {
                    return userDao.getUserDetailByAddressIdPayStatus(param.addressId, param.goodId, param.status).toObservable();
                }
            case TYPE_TAKE:
                if (param.goodId == -1) {
                    return userDao.getUserDetailByAddressIdTakeStatus(param.addressId, param.status).toObservable();
                } else {
                    return userDao.getUserDetailByAddressIdTakeStatus(param.addressId, param.goodId, param.status).toObservable();
                }
            case TYPE_COMPLETE:
                if (param.goodId == -1) {
                    return userDao.getUserDetailByAddressIdCompleted(param.searchName, param.addressId, param.status).toObservable();
                } else {
                    return userDao.getUserDetailByAddressIdCompleted(param.searchName, param.addressId, param.goodId, param.status).toObservable();
                }
            case TYPE_TAKEN_PAY:
                if (param.goodId == -1) {
                    return userDao.getUserDetailByAddressIdTakeAndPayStatus(param.searchName, param.addressId, param.status, param.status1).toObservable();
                } else {
                    return userDao.getUserDetailByAddressIdTakeAndPayStatus(param.searchName, param.addressId, param.goodId, param.status, param.status1).toObservable();
                }
            case TYPE_TAK_OR_PAY:
                if (param.goodId == -1) {
                    return userDao.getUserDetailByAddressIdTakeOrPayStatus(param.searchName, param.addressId, param.status, param.status1).toObservable();
                } else {
                    return userDao.getUserDetailByAddressIdTakeOrPayStatus(param.searchName, param.addressId, param.goodId, param.status, param.status1).toObservable();
                }
            default:
                return userDao.getUserDetailByCompletedStatus(0).toObservable();
        }

    }

    public static final class Param {
        private int type = 0;
        private long addressId = 0;
        private long goodId = 0;
        private int status = 0;
        private int status1 = 0;
        private String searchName = "";

        public Param(int type, long addressId, int status, long goodId) {
            this.type = type;
            this.addressId = addressId;
            this.status = status;
            this.goodId = goodId;
        }

        public Param(int type, long addressId, int status, int status1, long goodId) {
            this.type = type;
            this.addressId = addressId;
            this.status = status;
            this.status1 = status1;
            this.goodId = goodId;
        }

        public Param(int type, long addressId, int status, int status1, long goodId, String name) {
            this.type = type;
            this.addressId = addressId;
            this.status = status;
            this.status1 = status1;
            this.goodId = goodId;
            this.searchName = name;
        }

    }
}
