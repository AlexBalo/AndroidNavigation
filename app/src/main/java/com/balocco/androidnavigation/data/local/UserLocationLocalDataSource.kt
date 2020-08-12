package com.balocco.androidnavigation.data.local

import com.balocco.androidnavigation.data.model.Location
import io.reactivex.rxjava3.core.Observable

interface UserLocationLocalDataSource {
    fun locationObservable(): Observable<Location?>
}