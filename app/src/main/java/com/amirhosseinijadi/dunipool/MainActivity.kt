package com.amirhosseinijadi.dunipool

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirhosseinijadi.apiManager.ApiManager
import com.amirhosseinijadi.apiManager.ChartData
import com.amirhosseinijadi.apiManager.CoinsData
import com.amirhosseinijadi.apiManager.MarketAdapter
import com.amirhosseinijadi.dunipool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),MarketAdapter.RecyclerCallback {

    lateinit var binding:ActivityMainBinding
    val apiManager = ApiManager()
    lateinit var datanews:ArrayList<Pair<String,String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutToolbar.toolbar.title = "Market"
        initUi()
        binding.layoutWatchlist.btnShowmore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            startActivity(intent)
        }

        binding.swipeMain.setOnRefreshListener {

            initUi()

            Handler(Looper.getMainLooper()).postDelayed({binding.swipeMain.isRefreshing = false}
                ,1500)

        }
    }



    private fun initUi() {

        getnewsfromApi()
        gettopcoinsfromApi()

    }



    private fun getnewsfromApi() {

        apiManager.getnews( object : ApiManager.Apicallback<ArrayList<Pair<String,String>>>{


            override fun onerror(errormassage: String) {

                Toast.makeText(this@MainActivity, errormassage, Toast.LENGTH_SHORT).show()
                Log.v("tagx",errormassage)

            }

            override fun OnSuced(data: ArrayList<Pair<String, String>>) {
                datanews = data
                refreshnews()
            }


        })

    }
    private fun refreshnews(){

        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = datanews[randomAccess].first
        binding.layoutNews.imgNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(datanews[randomAccess].second))
            startActivity(intent)
        }
        binding.layoutNews.txtNews.setOnClickListener {
            refreshnews()
        }

    }
    private fun gettopcoinsfromApi() {
        apiManager.getcoinslist(object:ApiManager.Apicallback<List<CoinsData.Data>>{

            override fun OnSuced(data: List<CoinsData.Data>) {
                showdatainrecycler(data)
            }


            override fun onerror(errormassage: String) {
                Toast.makeText(this@MainActivity, "error =>$errormassage", Toast.LENGTH_SHORT).show()
                Log.v("tagx",errormassage)

            }



        })

    }
    private fun showdatainrecycler(data:List<CoinsData.Data>){

        val adapter = MarketAdapter(ArrayList(data),this)
        binding.layoutWatchlist.recyclerMain.adapter = adapter
        binding.layoutWatchlist.recyclerMain.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL,false)

    }

    override fun onItemcoinClicked(datacoin: CoinsData.Data) {

        val intent = Intent(this,ActivityCoin::class.java)
        intent.putExtra("dataToSend",datacoin)
        startActivity(intent)

    }
}