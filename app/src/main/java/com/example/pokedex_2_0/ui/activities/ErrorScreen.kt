package com.example.pokedex_2_0.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex_2_0.ui.theme.PurpleGrey40
import com.example.pokedex_2_0.ui.theme.PurpleGrey80

@Composable
fun ErrorDialog(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            tint = PurpleGrey40,
            modifier = Modifier.size(100.dp),
            contentDescription = "error"
        )
        Text(
            text = message,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = PurpleGrey80
        )
    }
}