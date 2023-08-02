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
public class GetUserListByStatus extends UseCase<List<UserEntity>, GetUserListByStatus.Param> {
    private UserDao userDao;
    public static final int TYPE_PAY = 0;
    public static final int TYPE_TAKE = 1;
    public static final int TYPE_COMPLETE = 2;
    public static final int TYPE_TAK_AND_PAY = 3;
    public static final int TYPE_TAK_OR_PAY = 4;

    @Inject
    protected GetUserListByStatus(UserDao userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userDao = userRepository;
    }

    @Override
    protected Observable<List<UserEntity>> buildUseCaseObservable(Param param) {
        switch (param.type) {
            case TYPE_PAY:
                return userDao.getUserDetailByPayStatus(param.status).toObservable();
            case TYPE_TAKE:
                return userDao.getUserDetailByTakeStatus(param.status).toObservable();
            case TYPE_TAK_AND_PAY:
                return userDao.getUserDetailByTakeAndPayStatus(param.status, param.status1).toObservable();
            case TYPE_TAK_OR_PAY:
                return userDao.getUserDetailByTakeOrPayStatus(param.status, param.status1).toObservable();
            case TYPE_COMPLETE:
                return userDao.getUserDetailByCompletedStatus(param.status).toObservable();
            default:
                return userDao.getUserDetailByCompletedStatus(0).toObservable();
        }

    }

    public static final class Param {
        private int type = 0;
        private int status = 0;
        private int status1 = 0;

        public Param(int type, int status, int status1) {
            this.type = type;
            this.status = status;
            this.status1 = status1;
        }
    }
}
