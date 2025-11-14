package com.example.placesintheworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onPlaceClick: (Place) -> Unit,
    currentViewType: ViewType
) {
    val places = remember { getSamplePlaces() }

    when (currentViewType) {
        ViewType.LAZY_COLUMN -> {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(places) { place ->
                    PlaceItemCard(place = place, onPlaceClick = onPlaceClick)
                }
            }
        }
        ViewType.STAGGERED_GRID -> {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(places.chunked(2)) { rowPlaces ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowPlaces.forEach { place ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                PlaceItemCard(place = place, onPlaceClick = onPlaceClick)
                            }
                        }
                        if (rowPlaces.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun PlaceItemCard(place: Place, onPlaceClick: (Place) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = { onPlaceClick(place) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Text(
                text = place.name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class Place(
    val id: Int,
    val name: String,
    val imageRes: Int
)

fun getSamplePlaces(): List<Place> {
    return listOf(
        Place(1, "Playa Algarve", R.drawable.image1),
        Place(2, "Maldivas", R.drawable.image2),
        Place(3, "Machu Pichu", R.drawable.image3),
        Place(4, "Gran Muralla China", R.drawable.image4),
        Place(5, "Alhambra", R.drawable.image5),
        Place(6, "Atenas", R.drawable.image6),
        Place(7, "Piramide Kukulkan", R.drawable.image7),
        Place(8, "Punta Cana", R.drawable.image8)
    )
}