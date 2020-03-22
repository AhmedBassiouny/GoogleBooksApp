package com.bassiouny.googlebooks.network

import com.bassiouny.googlebooks.model.BookDetailsResponse
import com.bassiouny.googlebooks.model.BooksResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes")
    fun search(@Query("q") key: String): Observable<BooksResponse>

    @GET("/books/v1/volumes/{id}")
    fun getBookDetails(@Path("id") id: String): Observable<BookDetailsResponse>
}
