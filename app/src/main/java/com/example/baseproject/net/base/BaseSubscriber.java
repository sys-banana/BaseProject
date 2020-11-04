package com.example.baseproject.net.base;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseSubscriber<T> implements Observer<T> {
    private CompositeDisposable compositeDisposable;
    private Disposable mDisposable;

    public BaseSubscriber(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        compositeDisposable.add(mDisposable);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        onError(ExceptionHandler.handleException(e));
        compositeDisposable.remove(mDisposable);
    }

    @Override
    public void onComplete() {
        compositeDisposable.remove(mDisposable);
        mDisposable.dispose();
        mDisposable = null;
    }

    public abstract void onError(ResponThrowable e);

    public Disposable getmDisposable() {
        return mDisposable;
    }
}

