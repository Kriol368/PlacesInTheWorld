package com.example.placesintheworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(place: Place) {
    var rotationYValue by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }
    var blurRadius by remember { mutableFloatStateOf(0f) }

    val saltyOceanFontFamily = FontFamily(
        Font(R.font.saltyocean, FontWeight.Normal)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = place.name,
                fontSize = 65.sp,
                fontFamily = saltyOceanFontFamily,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 32.dp),
                maxLines = 2,
                lineHeight = 70.sp
            )

            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .graphicsLayer {
                        rotationY = rotationYValue
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha

                        renderEffect = BlurEffect(
                            blurRadius.dp.toPx(),
                            blurRadius.dp.toPx(),
                            TileMode.Decal
                        )
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rotación",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
                Slider(
                    value = rotationYValue,
                    onValueChange = { rotationYValue = it },
                    modifier = Modifier.weight(2f),
                    valueRange = 0f..360f
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Escala",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
                Slider(
                    value = scale,
                    onValueChange = { scale = it },
                    modifier = Modifier.weight(2f),
                    valueRange = 0.1f..3f
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transparencia",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
                Slider(
                    value = alpha,
                    onValueChange = { alpha = it },
                    modifier = Modifier.weight(2f),
                    valueRange = 0f..1f
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Desenfoque",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
                Slider(
                    value = blurRadius,
                    onValueChange = { blurRadius = it },
                    modifier = Modifier.weight(2f),
                    valueRange = 0f..20f
                )
            }
        }
    }
}