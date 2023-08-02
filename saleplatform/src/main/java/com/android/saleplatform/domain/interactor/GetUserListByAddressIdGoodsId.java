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
public class GetUserListByAddressIdGoodsId extends UseCase<List<UserEntity>, GetUserListByAddressIdGoodsId.Param> {
    private UserDao userDao;

    @Inject
    protected GetUserListByAddressIdGoodsId(UserDao userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userDao = userRepository;
    }

    @Override
    protected Observable<List<UserEntity>> buildUseCaseObservable(Param param) {
        return Observable.create(e1 ->
                e1.onNext(userDao.queryUserByAddressIdAndGoodIs(param.addressId, param.goodId))
        );
    }

    public static final class Param {
        private long addressId = 0;
        private long goodId = 0;

        public Param(long addressId, long goodId) {
            this.addressId = addressId;
            this.goodId = goodId;
        }
    }
}
