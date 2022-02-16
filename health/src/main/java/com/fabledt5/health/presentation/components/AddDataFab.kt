package com.fabledt5.health.presentation.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.fabledt5.health.R

@Composable
fun AddDataFab(onAddDataCLicked: () -> Unit) {
    FloatingActionButton(onClick = onAddDataCLicked, backgroundColor = Color.Red) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = stringResource(R.string.icon_favorite),
            tint = Color.White
        )
    }
}