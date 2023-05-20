package com.lakue.petdelorder.presenter.main

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.lakue.petdelorder.databinding.ActivityMainBinding
import com.lakue.petdelorder.domain.Order
import com.lakue.petdelorder.presenter.barcode.BarcodeActivity
import com.lakue.petdelorder.presenter.order.OrderWebActivity
import com.lakue.petdelorder.presenter.search.SearchActivity
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()

        binding.tvBarcode.setOnClickListener {
            startActivity(Intent(this, BarcodeActivity::class.java))
        }
        binding.tvSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    @Throws(IOException::class, CsvException::class)
    private fun loadData() {
        val assetManager: AssetManager = getAssets()
        val inputStream: InputStream = assetManager.open("petdel_order.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "UTF-8"))
        val allContent = csvReader.readAll() as List<Array<String>>
        for (content in allContent) {
            orders.add(
                Order(
                    name = content[0],
                    barcode = content[2],
                    url = content[3]
                )
            )
        }
    }

    companion object {
        val orders = arrayListOf<Order>()
        val orderTxt = StringBuilder()
    }
}