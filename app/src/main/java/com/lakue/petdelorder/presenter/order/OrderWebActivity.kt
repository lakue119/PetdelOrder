package com.lakue.petdelorder.presenter.order

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.lakue.petdelorder.databinding.ActivityOrderWebBinding

class OrderWebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderUrl = intent.getStringExtra("url") ?: ""

        binding = ActivityOrderWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.apply {
            webViewClient = WebViewClient()  // 새 창 띄우기 않기
            webChromeClient = WebChromeClient()

            settings.loadWithOverviewMode = true  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
            settings.useWideViewPort = true  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

            settings.setSupportZoom(false)  // 줌 설정 여부
            settings.builtInZoomControls = false  // 줌 확대/축소 버튼 여부

            settings.javaScriptEnabled = true // 자바스크립트 사용여부
//        webview.addJavascriptInterface(new AndroidBridge(), "android");
            settings.javaScriptCanOpenWindowsAutomatically = true // javascript가 window.open()을 사용할 수 있도록 설정
            settings.setSupportMultipleWindows(true) // 멀티 윈도우 사용 여부

            settings.domStorageEnabled = true  // 로컬 스토리지 (localStorage) 사용여부

            loadUrl(orderUrl)
        }
    }
}