package com.bassiouny.googlebooks.ui.booklist

import com.bassiouny.googlebooks.model.BookDetailsResponse
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

    fun getBookDetails(
        mainThread: Scheduler,
        io: Scheduler,
        id: String,
        api: BooksApi
    ): Observable<BookDetailsResponse>? {

        return api.getBookDetails(id)
            .observeOn(mainThread)
            .subscribeOn(io)
    }
}