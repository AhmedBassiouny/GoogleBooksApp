package com.bassiouny.googlebooks.ui.bookdetails

import com.bassiouny.googlebooks.model.BookDetailsResponse
import com.bassiouny.googlebooks.network.BooksApi
import io.reactivex.Observable
import io.reactivex.Scheduler

class BookDetailsInteractor {
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