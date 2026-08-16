package com.github.koros.sampleapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.koros.gridrecyclerview.GridDescriptor
import com.github.koros.gridrecyclerview.GridRecyclerView
import com.github.koros.sampleapp.model.Actor
import com.github.koros.sampleapp.model.Genre
import com.github.koros.sampleapp.model.GridHeader
import com.github.koros.sampleapp.model.Movie
import com.github.koros.sampleapp.util.DummyDataGenerator.getSampleActors
import com.github.koros.sampleapp.util.DummyDataGenerator.getSampleGenres
import com.github.koros.sampleapp.util.DummyDataGenerator.getSampleMovies
import com.github.koros.sampleapp.util.HeaderKey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF6F7F9)
                ) {
                    SampleGridScreen(gridItems = sampleGridItems())
                }
            }
        }
    }
}

private fun sampleGridItems(): Map<GridHeader, GridDescriptor<*>> = linkedMapOf(
    GridHeader("Genres", "One column section", HeaderKey.GENRE) to GridDescriptor(1, getSampleGenres()),
    GridHeader("Movies", "Two column section", HeaderKey.MOVIE) to GridDescriptor(2, getSampleMovies()),
    GridHeader("Actors", "Three column section", HeaderKey.ACTOR) to GridDescriptor(3, getSampleActors())
)

@Composable
private fun SampleGridScreen(gridItems: Map<GridHeader, GridDescriptor<*>>) {
    val context = LocalContext.current

    GridRecyclerView(
        gridItems = gridItems,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        headerContent = { header ->
            SectionHeader(header = header)
        },
        gridItemContent = { header, item ->
            when (header.key) {
                HeaderKey.GENRE -> GenreCard(item as Genre)
                HeaderKey.MOVIE -> MovieCard(item as Movie)
                HeaderKey.ACTOR -> ActorCard(item as Actor)
                else -> Text(text = item.toString())
            }
        },
        onGridItemClick = { header, item ->
            Toast.makeText(
                context,
                "${header.header}: ${itemLabel(item)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}

private fun itemLabel(item: Any?): String = when (item) {
    is Actor -> item.name
    is Genre -> item.name
    is Movie -> item.name
    else -> item.toString()
}

@Composable
private fun SectionHeader(header: GridHeader) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = header.header,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF202124)
        )
        header.subHeader?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
private fun GenreCard(genre: Genre) {
    CardShell {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            NamedImage(
                name = genre.image,
                contentDescription = genre.name,
                modifier = Modifier
                    .weight(0.9f)
                    .height(104.dp)
            )
            Text(
                text = genre.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MovieCard(movie: Movie) {
    PosterCard(title = movie.name, imageName = movie.cover)
}

@Composable
private fun ActorCard(actor: Actor) {
    PosterCard(title = actor.name, imageName = actor.image)
}

@Composable
private fun PosterCard(title: String, imageName: String) {
    CardShell {
        NamedImage(
            name = imageName,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CardShell(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }
}

@Composable
private fun NamedImage(name: String, contentDescription: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageId = remember(name) {
        context.resources.getIdentifier(name, "drawable", context.packageName)
            .takeIf { it != 0 }
            ?: R.drawable.ic_placeholder
    }

    Image(
        painter = painterResource(imageId),
        contentDescription = contentDescription,
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(Color(0xFFE5E7EB)),
        contentScale = ContentScale.Crop
    )
}
