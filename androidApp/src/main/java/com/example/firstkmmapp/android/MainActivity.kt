package com.example.firstkmmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Container()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Container() {
    val viewModel = viewModel<MainViewModel>()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Querize",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_menu_24),
                        contentDescription = ""
                    )
                }
            )
        },
        containerColor = (Color(0xffF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_realm_logo),
                    contentScale = ContentScale.Fit,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .defaultMinSize(minHeight = 200.dp)
                        .padding(bottom = 20.dp),
                )
            }

            AddQuery(viewModel)

            Text(
                "Queries",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            QueriesList(viewModel)
        }
    }
}


@Composable
fun AddQuery(viewModel: MainViewModel) {

    val queryText = remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = "Enter your query here") },
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_baseline_send_24),
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.saveQuery(queryText.value)
                    queryText.value = ""
                })
        },
        value = queryText.value,
        onValueChange = {
            queryText.value = it
        })
}

@Composable
fun QueriesList(viewModel: MainViewModel) {

    val queries = viewModel.queries.observeAsState(initial = emptyList()).value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(8.dp),
        content = {
            items(items = queries, itemContent = { item: String ->
                QueryItem(query = item)
            })
        })
}

@Preview
@Composable
fun QueryPreview() {
    QueryItem(query = "Sample text")
}

@Composable
fun QueryItem(query: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(text = query, modifier = Modifier.fillMaxWidth())
    }
}
