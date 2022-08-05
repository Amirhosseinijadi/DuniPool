package com.amirhosseinijadi.dunipool

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amirhosseinijadi.apiManager.*
import com.amirhosseinijadi.dunipool.databinding.ActivityCoinBinding


class ActivityCoin : AppCompatActivity() {
    lateinit var binding:ActivityCoinBinding
    lateinit var datathiscoin:CoinsData.Data
    val apimanager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

         datathiscoin = intent.getParcelableExtra<CoinsData.Data>("dataToSend")!!
        binding.layoutToolbar.toolbar.title = datathiscoin.coinInfo!!.fullName


        initui()


    }

    private fun initui() {
        //initChartui()
        initStatisticsui()
        initAboutui()
    }

    private fun initAboutui() {

    }

    @SuppressLint("SetTextI18n")
    private fun initStatisticsui() {


        binding.layoutStatictics.txtOpenamount.text =  datathiscoin.dISPLAY!!.uSD!!.oPEN24HOUR
        binding.layoutStatictics.tvTodayHigh.text =   datathiscoin.dISPLAY!!.uSD!!.hIGH24HOUR
        binding.layoutStatictics.tvTodayLow.text =   datathiscoin.dISPLAY!!.uSD!!.lOW24HOUR
        binding.layoutStatictics.tvChangeToday.text =  datathiscoin.dISPLAY!!.uSD!!.cHANGE24HOUR
        binding.layoutStatictics.txtAlgoritm.text =  datathiscoin.coinInfo!!.algorithm
        binding.layoutStatictics.txtTotalvolume.text =  datathiscoin.dISPLAY!!.uSD!!.tOTALVOLUME24H
        binding.layoutStatictics.txtMarketCap.text =  datathiscoin.dISPLAY!!.uSD!!.mKTCAP
        binding.layoutStatictics.txtSupply.text =  datathiscoin.dISPLAY!!.uSD!!.sUPPLY

    }

    private fun initChartui() {

       apimanager.getChartData("BTC", HOUR, object : ApiManager.Apicallback<Pair<List<ChartData.Data>,ChartData.Data?>>{
           override fun OnSuced(data: Pair<List<ChartData.Data>, ChartData.Data?>) {

               val chartadapter = ChartAdapter(data.first,data.second?.open.toString())
               binding.layoutChart.sparkViewMain.adapter = chartadapter

           }

           override fun onerror(errormassage: String) {

               Toast.makeText(this@ActivityCoin, "error => $errormassage", Toast.LENGTH_SHORT).show()

           }

       }

        )






}}