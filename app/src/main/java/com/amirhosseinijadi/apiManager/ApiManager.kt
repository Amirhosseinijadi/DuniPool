package com.amirhosseinijadi.apiManager

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// chart data =>
const val HISTO_MINUTE = "histominute"
const val HISTO_HOUR = "histohour"
const val HISTO_DAY = "histoday"

const val HOUR = "12 hour"
const val HOURS24 = "1 day"
const val WEEK = "1 week"
const val MONTH = "1 month"
const val MONTH3 = "3 month"
const val YEAR = "1 year"
const val ALL = "All"
const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY = "authorization: Apikey 34efc3750aa05495f55d22aa67381bf55211e8c846e20cf70cc59103c78c88e2"
const val APP_NAME = "Dunipool"
class ApiManager {
    private val apiservice:Apiservice
    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiservice = retrofit.create(Apiservice::class.java)




    }



    fun getnews(apicallback:Apicallback<ArrayList<Pair<String,String>>>){
         apiservice.gettopnews().enqueue( object : Callback<NewsData>{
             override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                 val data = response.body()!!
                val datatosend : ArrayList<Pair<String,String>> = arrayListOf()

                 data.data.forEach {
                     datatosend.add(Pair(it.title,it.url))
                 }


                 apicallback.OnSuced(datatosend)





             }

             override fun onFailure(call: Call<NewsData>, t: Throwable) {

                 apicallback.onerror( t.message!! )


             }

         }

         )
    }

    fun getcoinslist (apicallback:Apicallback<List<CoinsData.Data>>){

        apiservice.gettopcoins().enqueue(object : Callback<CoinsData>{
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val data = response.body()!!
                apicallback.OnSuced(data.data as List<CoinsData.Data>)


            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {

            }

        })

    }

    fun getChartData (symbol: String, period:String, apicallback: Apicallback<Pair<List<ChartData.Data>, ChartData.Data?>>){

        var histoperiod = ""
        var limit = 30
        var aggregate = 1
         when(period){
             HOUR -> {
                 histoperiod = HISTO_MINUTE
                 limit = 60
                 aggregate = 12

             }
             HOURS24 -> {
                 histoperiod = HISTO_HOUR
                 limit = 24


             }
             WEEK -> {
                 histoperiod = HISTO_HOUR
                 aggregate = 6



             }
             MONTH -> {
                 histoperiod = HISTO_DAY
                 limit = 30

             }
             MONTH3 -> {

                 histoperiod = HISTO_DAY
                 limit = 90

             }
             YEAR -> {
                 histoperiod = HISTO_DAY
                 aggregate = 13

             }
             ALL -> {
                 histoperiod = HISTO_DAY
                 aggregate = 30
                 limit = 2000

             }



         }


        apiservice.getchartdata(histoperiod ,symbol ,limit ,aggregate  ).enqueue(object:Callback<ChartData>{
            override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {
                val datafull = response.body()!!
                val data1 = datafull.data
                val data2 = datafull.data.maxByOrNull {
                    it.close.toFloat()
                }
               val returningdata = Pair(data1,data2)
               apicallback.OnSuced(returningdata)

            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
               apicallback.onerror(t.message!!)

            }

        })

    }



    interface Apicallback<T>{

        fun OnSuced(data:T)

        fun onerror(errormassage:String)


    }
}