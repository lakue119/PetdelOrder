package com.lakue.petdelorder

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lakue.petdelorder.databinding.ActivityMainBinding
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
    }

    @Throws(IOException::class, CsvException::class)
    private fun loadData() {
        val assetManager: AssetManager = getAssets()
        val inputStream: InputStream = assetManager.open("petdel_order.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "UTF-8"))
        val allContent = csvReader.readAll() as List<Array<String>>
        for (content in allContent) {
            val sb = StringBuilder("")
            Log.d(
                "csv",
                "이름 : " + content[0] + " 가격: " + content[1] + " 바코드: " + content[2] + " 링크: " + content[3]
            )
        }
    }
}