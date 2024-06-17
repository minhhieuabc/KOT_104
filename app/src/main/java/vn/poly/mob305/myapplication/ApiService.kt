package vn.poly.mob305.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    // Path : api/cats
    // ? tags=cute&skip=0&limit=10 : params , tags , skip , limit
    // tags=cute : query
    @GET("api/cats")
    fun getListCats(@Query("tags") tags:String, @Query("skip") skip:Int, @Query("limit") limit:Int) : Call<List<Cat>>
}