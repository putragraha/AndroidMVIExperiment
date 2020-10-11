package com.nsystem.androidmviexperiment.utils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version utils, v 0.0.1 11/10/20 16.30 by Putra Nugraha
 */
inline fun <T, U, R> Observable<T>.withLatestFrom(
    other: ObservableSource<U>,
    crossinline combiner: (T, U) -> R
): Observable<R> = withLatestFrom(other, BiFunction<T, U, R> { t, u -> combiner.invoke(t, u) })

fun <T> lazyUi(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)