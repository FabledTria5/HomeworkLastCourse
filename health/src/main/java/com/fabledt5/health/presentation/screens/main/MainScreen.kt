package com.fabledt5.health.presentation.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fabledt5.health.R
import com.fabledt5.health.domain.model.HealthItem
import com.fabledt5.health.domain.model.Resource
import com.fabledt5.health.presentation.components.AddDataDialog
import com.fabledt5.health.presentation.components.AddDataFab
import com.fabledt5.health.presentation.theme.HighPressure
import com.fabledt5.health.presentation.theme.MediumPressure
import com.fabledt5.health.presentation.theme.NormalPressure
import java.lang.NumberFormatException

@ExperimentalFoundationApi
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    var isDialogEnabled by remember { mutableStateOf(false) }

    val healthData by mainViewModel.healthItems.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AddDataFab(onAddDataCLicked = { isDialogEnabled = true })
        }) {
        AddDataDialog(
            enabled = isDialogEnabled,
            onDismiss = { isDialogEnabled = false },
            onAddDataClick = { lowPressure, highPressure, pulse ->
                isDialogEnabled = false
                mainViewModel.saveHealthData(lowPressure, highPressure, pulse)
            })
        HandleMainScreenContent(healthData)
    }
}

@ExperimentalFoundationApi
@Composable
fun HandleMainScreenContent(healthData: Resource<List<HealthItem>>) {
    when (healthData) {
        is Resource.Error -> ErrorContent()
        Resource.Loading -> LoadingContent()
        is Resource.Success -> {
            if (healthData.data.isNotEmpty())
                ListContent(healthData = healthData.data)
            else ErrorContent()
        }
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = listOf(NormalPressure, MediumPressure, HighPressure).random()
        )
    }
}

@Composable
fun ErrorContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "We could not load your information\nor you have not created anything yet",
            color = Color.Black,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun ListContent(healthData: List<HealthItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val groupedList = healthData.groupBy { it.dateAdded }

        for (entry in groupedList) {
            stickyHeader { HealthItemHeader(headerValue = entry.key) }
            entry.value.forEach { item ->
                item { HealthItemView(healthItem = item) }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
            }
        }
    }
}

@Composable
fun HealthItemView(healthItem: HealthItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.White,
                        chooseGradientColor(healthItem.lowPressure, healthItem.highPressure),
                        Color.White
                    )
                )
            )
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = healthItem.timeAdded, color = Color.Gray, fontSize = 14.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = healthItem.highPressure, color = Color.Black, fontSize = 25.sp)
            Icon(
                painter = painterResource(id = R.drawable.ic_slash),
                contentDescription = stringResource(R.string.icon_splash),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .height(18.dp)
                    .rotate(-15f),
                tint = Color.DarkGray
            )
            Text(text = healthItem.lowPressure, color = Color.Black, fontSize = 25.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.heart_beat),
                modifier = Modifier.size(14.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = healthItem.pulse, color = Color.Gray, fontSize = 25.sp)
        }
    }
}

@Composable
fun HealthItemHeader(headerValue: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color(0xFFF1F1F1))
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = size.width, y = 0f),
                    strokeWidth = 2f
                )
                drawLine(
                    color = Color.Gray,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 2f
                )
            }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = headerValue, color = Color.Gray, fontSize = 14.sp)
    }
}

fun chooseGradientColor(lowPressure: String, highPressure: String) = try {
    when {
        highPressure.toInt() in 100..129 && lowPressure.toInt() in 60..84 -> NormalPressure
        highPressure.toInt() in 130..159 && lowPressure.toInt() in 85..99 -> MediumPressure
        highPressure.toInt() >= 160 && lowPressure.toInt() >= 100 -> HighPressure
        else -> MediumPressure
    }
} catch (exception: NumberFormatException) {
    NormalPressure
}