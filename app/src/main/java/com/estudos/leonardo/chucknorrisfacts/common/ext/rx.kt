package com.estudos.leonardo.chucknorrisfacts.common.ext

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


fun <T : Any> Observable<T>.setSubscriber(): Observable<T>? {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
