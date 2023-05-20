package com.lakue.petdelorder.presenter.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lakue.petdelorder.domain.Order
import com.lakue.petdelorder.presenter.main.MainActivity.Companion.orders
import com.lakue.petdelorder.presenter.order.OrderWebActivity
import com.lakue.petdelorder.presenter.search.ui.theme.PetdelOrderTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetdelOrderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SearchOrderScreen(orders)
                }
            }
        }
    }

    @Composable
    fun SearchOrderScreen(orderList: List<Order>) {
        val searchQuery = remember { mutableStateOf("") }

        val filteredList = orderList.filter { order ->
            searchQuery.value.split(" ").all { keyword ->
                order.name.contains(keyword, ignoreCase = true)
            }
        }

        Column {
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("검색") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(filteredList) { order ->
                    OrderItem(order)
                }
            }
        }
    }

    @Composable
    fun OrderItem(order: Order) {
        Text(
            text = order.name,
            modifier = Modifier.padding(vertical = 8.dp)
                .clickable {
                    val intent = Intent(this@SearchActivity, OrderWebActivity::class.java)
                    intent.putExtra("url", order.url)
                    startActivity(intent)
                },
        )
    }
}
