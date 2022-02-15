package com.fabledt5.health.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fabledt5.health.R
import com.fabledt5.health.presentation.theme.MediumPressure
import com.fabledt5.health.presentation.theme.NormalPressure

@Composable
fun AddDataDialog(
    enabled: Boolean,
    onDismiss: () -> Unit,
    onAddDataClick: (String, String, String) -> Unit
) {
    var lowPressureText by remember { mutableStateOf("") }
    var highPressureText by remember { mutableStateOf("") }
    var pulseText by remember { mutableStateOf("") }

    if (enabled)
        Dialog(onDismissRequest = onDismiss) {
            Card(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(R.string.add_data),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = highPressureText,
                            onValueChange = { highPressureText = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text(text = stringResource(R.string.high)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = NormalPressure,
                                disabledIndicatorColor = NormalPressure,
                                unfocusedIndicatorColor = NormalPressure
                            )
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_slash),
                            contentDescription = stringResource(R.string.icon_splash),
                            modifier = Modifier
                                .weight(1f)
                                .height(30.dp)
                        )
                        TextField(
                            value = lowPressureText,
                            onValueChange = { lowPressureText = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text(text = stringResource(R.string.low)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = NormalPressure,
                                disabledIndicatorColor = NormalPressure,
                                unfocusedIndicatorColor = NormalPressure
                            )
                        )
                    }
                    TextField(
                        value = pulseText,
                        onValueChange = { pulseText = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        placeholder = { Text(text = stringResource(R.string.heart_beat)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = NormalPressure,
                            disabledIndicatorColor = NormalPressure,
                            unfocusedIndicatorColor = NormalPressure
                        )
                    )
                    Row(modifier = Modifier.padding(8.dp)) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MediumPressure)
                        ) {
                            Text(text = stringResource(R.string.cancel))
                        }
                        Spacer(modifier = Modifier.weight(.5f))
                        Button(
                            onClick = {
                                onAddDataClick(lowPressureText, highPressureText, pulseText)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = NormalPressure),
                            enabled = lowPressureText.isNotEmpty()
                                    && highPressureText.isNotEmpty()
                                    && pulseText.isNotEmpty()
                        ) {
                            Text(text = stringResource(R.string.submit))
                        }
                    }
                }
            }
        }
}