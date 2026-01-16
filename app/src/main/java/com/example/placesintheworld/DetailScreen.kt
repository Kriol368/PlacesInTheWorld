package com.example.placesintheworld

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette

@Composable
fun DetailScreen(
    place: Place,
    onColorsExtracted: (Color, Color) -> Unit
) {
    val context = LocalContext.current
    val colors = remember { mutableStateOf<Palette?>(null) }

    LaunchedEffect(place.imageRes) {
        val bitmap = BitmapFactory.decodeResource(context.resources, place.imageRes)
        Palette.from(bitmap).generate { palette ->
            colors.value = palette
            val vibrantColor = palette?.vibrantSwatch?.rgb?.let { Color(it) } ?: Color.Black
            val darkVibrantColor = palette?.darkVibrantSwatch?.rgb?.let { Color(it) } ?: Color.DarkGray
            onColorsExtracted(vibrantColor, darkVibrantColor)
        }
    }

    val palette = colors.value
    val vibrantColor = palette?.vibrantSwatch?.rgb?.let { Color(it) } ?: Color.Black
    val darkVibrantColor = palette?.darkVibrantSwatch?.rgb?.let { Color(it) } ?: Color.DarkGray
    val lightVibrantColor = palette?.lightVibrantSwatch?.rgb?.let { Color(it) } ?: Color.LightGray
    val mutedColor = palette?.mutedSwatch?.rgb?.let { Color(it) } ?: Color.Gray
    val darkMutedColor = palette?.darkMutedSwatch?.rgb?.let { Color(it) } ?: Color.DarkGray
    val lightMutedColor = palette?.lightMutedSwatch?.rgb?.let { Color(it) } ?: Color.White

    fun getContrastTextColor(backgroundColor: Color): Color {
        val luminance = ColorUtils.calculateLuminance(backgroundColor.toArgb())
        return if (luminance > 0.5) Color.Black else Color.White
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = place.imageRes),
            contentDescription = place.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(lightVibrantColor)
                .padding(16.dp)
        ) {
            Text(
                text = "LightVibrant",
                color = getContrastTextColor(lightVibrantColor),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(vibrantColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Vibrant",
                color = getContrastTextColor(vibrantColor),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(darkVibrantColor)
                .padding(16.dp)
        ) {
            Text(
                text = "DarkVibrant",
                color = getContrastTextColor(darkVibrantColor),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(lightMutedColor)
                .padding(16.dp)
        ) {
            Text(
                text = "LightMuted",
                color = getContrastTextColor(lightMutedColor),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(mutedColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Muted",
                color = getContrastTextColor(mutedColor),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(darkMutedColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Dark Muted",
                color = getContrastTextColor(darkMutedColor),
                modifier = Modifier.weight(1f)
            )
        }
    }
}