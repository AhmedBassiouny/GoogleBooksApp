package com.bassiouny.googlebooks.network

import com.bassiouny.googlebooks.model.BooksResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes")
    fun search(@Query("q") key: String): Observable<BooksResponse>

}
