package com.amirhosseinijadi.apiManager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amirhosseinijadi.apiManager.MarketAdapter.Marketviewholder
import com.amirhosseinijadi.dunipool.R
import com.amirhosseinijadi.dunipool.databinding.ItemRecyclerMarketBinding
import com.bumptech.glide.Glide
import kotlinx.parcelize.Parcelize


class MarketAdapter(private val data:ArrayList<CoinsData.Data>,private val recyclercallback:RecyclerCallback ):RecyclerView.Adapter<Marketviewholder>() {
    lateinit var binding:ItemRecyclerMarketBinding

    inner class Marketviewholder(itemview:View):RecyclerView.ViewHolder(itemview){

         @SuppressLint("SetTextI18n")
         fun bindviews(datacoin:CoinsData.Data){
             binding.txtCoinname.text = datacoin.coinInfo!!.fullName
             binding.txtPrice.text = datacoin.dISPLAY!!.uSD!!.pRICE.toString()
             val taghir =  datacoin.rAW!!.uSD!!.cHANGE24HOUR
             if(taghir !! > 0){

                 binding.txtTaghir.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorGain))
                 binding.txtTaghir.text = datacoin.rAW.uSD!!.cHANGE24HOUR.toString().substring(0,4) + "%"






             }else if(taghir < 0){

                 binding.txtTaghir.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorLoss))
                 binding.txtTaghir.text = datacoin.rAW.uSD!!.cHANGE24HOUR.toString().substring(0, 5)+ "%"

             }else{
                 binding.txtTaghir.text = "0%"
             }
            val marketcap = datacoin.rAW.uSD!!.mKTCAP
             val indexdot = marketcap.toString().indexOf('.')
             binding.txtMarketcap.text = "$" + marketcap.toString().substring(0,indexdot + 3) + " B"


             Glide
                 .with(itemView)
                 .load(BASE_URL_IMAGE + datacoin.coinInfo.imageUrl)
                 .into(binding.imgCoin)

             itemView.setOnClickListener {


                 recyclercallback.onItemcoinClicked(datacoin)



             }




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Marketviewholder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerMarketBinding.inflate(inflater , parent , false)

        return Marketviewholder(binding.root)

    }

    override fun onBindViewHolder(holder: Marketviewholder, position: Int) {
        holder.bindviews(data[position])

    }

    override fun getItemCount(): Int {

        return data.size

    }

    interface RecyclerCallback {
         fun onItemcoinClicked(datacoin:CoinsData.Data)
    }

}