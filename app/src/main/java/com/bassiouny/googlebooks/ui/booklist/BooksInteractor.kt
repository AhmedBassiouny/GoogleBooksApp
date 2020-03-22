package com.bassiouny.googlebooks.ui.booklist

import com.bassiouny.googlebooks.model.BooksResponse
import com.bassiouny.googlebooks.network.BooksApi
import io.reactivex.Observable
import io.reactivex.Scheduler

class BooksInteractor {
    fun getBooks(
        mainThread: Scheduler,
        io: Scheduler,
        keyword: String,
        api: BooksApi
    ): Observable<BooksResponse>? {

        return api.search(keyword)
            .observeOn(mainThread)
            .subscribeOn(io)
    }
}