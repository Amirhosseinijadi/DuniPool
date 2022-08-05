package com.amirhosseinijadi.apiManager

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Apiservice {
    @Headers(API_KEY)
    @GET("v2/news/")
    fun gettopnews(
        @Query("lang") lang:String = "EN",
        @Query("sortOrder") sortOrder:String = "popular"

    ) : Call<NewsData>
    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun gettopcoins (
        @Query("tsym") to_symbol:String = "USD",
        @Query("limit") limit_data:Int = 10

    ):Call<CoinsData>
    @Headers(API_KEY)
    @GET("{period}")
    fun getchartdata(
        @Path("period") Period:String ,
        @Query("fsym") fromsymbol:String,
        @Query("limit")limit:Int,
        @Query("aggregate")aggregate:Int,
        @Query("tsym") tosymbol:String = "USD"
    ):Call<ChartData>



}