package com.lakue.petdelorder.presenter.barcode

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.lakue.petdelorder.databinding.ActivityBarcodeBinding
import com.lakue.petdelorder.domain.Order
import com.lakue.petdelorder.presenter.main.MainActivity
import com.lakue.petdelorder.presenter.main.MainActivity.Companion.orderTxt
import com.lakue.petdelorder.presenter.order.OrderWebActivity

class BarcodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarcodeBinding

    // 스캐너 설정
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            finish()
        } else {
            val order = findBarcode(result.contents)
            if(order?.url.isNullOrBlank()) {
                Toast.makeText(this, "데이터 없음", Toast.LENGTH_LONG).show()
            } else {
                if(order?.url == "문의") {
                    orderTxt.append("${order.name} x개\n")
                }

                val intent = Intent(this, OrderWebActivity::class.java)
                intent.putExtra("url", order?.url)
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBarcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        barcodeLauncher.launch(ScanOptions())
    }

    private fun findBarcode(barcode: String): Order? {
        MainActivity.orders.forEach {
            if (it.barcode == barcode) {
                return it
            }
        }
        return null
    }

}