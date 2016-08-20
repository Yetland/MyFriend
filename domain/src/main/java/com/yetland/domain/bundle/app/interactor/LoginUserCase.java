package com.yetland.domain.bundle.app.interactor;

import com.yetland.domain.bundle.app.repository.UserRepository;
import com.yetland.domain.core.UseCase;
import com.yetland.domain.core.executor.PostExecutionThread;
import com.yetland.domain.core.executor.ThreadExecutor;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author yeliang
 * @data 2016/6/29.
 */
public class LoginUserCase extends UseCase {

    private UserRepository userRepository;

    @Inject
    protected LoginUserCase(UserRepository userRepository,ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... params) {
        if (params.length != 2){
            Observable.error(new IllegalArgumentException("The function's params count is incorrect."));

        }
        return userRepository.login((String)params[0],(String)params[1]);
    }
}
