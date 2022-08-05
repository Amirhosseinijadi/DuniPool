package com.amirhosseinijadi.apiManager

import com.robinhood.spark.SparkAdapter

class ChartAdapter(private val historicaldata:List<ChartData.Data>,private val baseline:String?):SparkAdapter() {
    override fun getCount(): Int {

        return historicaldata.size

    }

    override fun getItem(index: Int): ChartData.Data {

        return historicaldata[index]


    }

    override fun getY(index: Int): Float {
        return historicaldata[index].close.toFloat()

    }

    override fun hasBaseLine(): Boolean {
        return true
    }

    override fun getBaseLine(): Float {
        return baseLine?.toFloat() ?:super.getBaseLine()
    }

}