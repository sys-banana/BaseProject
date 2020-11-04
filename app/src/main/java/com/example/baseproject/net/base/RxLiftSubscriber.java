package com.example.baseproject.net.base;

import com.example.baseproject.model.base.BaseResponse;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxLiftSubscriber {
    public static <R> ObservableOperator<R, BaseResponse<R>> beanTransformer(){
        return new ObservableOperator<R, BaseResponse<R>>() {
            @Override
            public Observer<? super BaseResponse<R>> apply(final Observer<? super R> observer) throws Exception {
                return new Observer<BaseResponse<R>>() {
                    private Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        observer.onSubscribe(d);
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(BaseResponse<R> rBaseResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                        mDisposable.dispose();
                    }
                };
            }
        };
    }
}
