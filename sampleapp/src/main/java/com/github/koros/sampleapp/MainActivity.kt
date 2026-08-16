package com.github.koros.sampleapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

/**
 * Entry point for the sample app that demonstrates the Compose grid library.
 */
class MainActivity : ComponentActivity() {
    /**
     * Creates the Compose UI and renders the sample grid sections.
     *
     * @param savedInstanceState Previously saved activity state, if Android is recreating the activity.
     */
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

/**
 * Builds the ordered sample sections shown by the demo screen.
 *
 * @return A linked map so the sample renders sections in the declared order.
 */
private fun sampleGridItems(): Map<GridHeader, GridDescriptor<*>> = linkedMapOf(
    GridHeader("Genres", "One column section", HeaderKey.GENRE) to GridDescriptor(1, getSampleGenres()),
    GridHeader("Movies", "Two column section", HeaderKey.MOVIE) to GridDescriptor(2, getSampleMovies()),
    GridHeader("Actors", "Three column section", HeaderKey.ACTOR) to GridDescriptor(3, getSampleActors())
)

/**
 * Renders all sample sections and delegates each section's cell UI to typed cards.
 *
 * @param gridItems The section descriptors to render in display order.
 */
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
            // Keep click ownership inside the item content so each card can use its typed model.
            when (header.key) {
                HeaderKey.GENRE -> {
                    val genre = item as Genre
                    GenreCard(
                        genre = genre,
                        modifier = Modifier.clickable { showSelection(context, header, genre.name) }
                    )
                }
                HeaderKey.MOVIE -> {
                    val movie = item as Movie
                    MovieCard(
                        movie = movie,
                        modifier = Modifier.clickable { showSelection(context, header, movie.name) }
                    )
                }
                HeaderKey.ACTOR -> {
                    val actor = item as Actor
                    ActorCard(
                        actor = actor,
                        modifier = Modifier.clickable { showSelection(context, header, actor.name) }
                    )
                }
                // Unknown sample keys still render a readable fallback instead of failing the preview.
                else -> Text(text = item.toString())
            }
        }
    )
}

/**
 * Shows the selected item from a sample section.
 *
 * @param context Android context used to display the toast.
 * @param header  The section header for the selected item.
 * @param itemName The display name of the selected item.
 */
private fun showSelection(context: android.content.Context, header: GridHeader, itemName: String) {
    Toast.makeText(
        context,
        "${header.header}: $itemName",
        Toast.LENGTH_SHORT
    ).show()
}

/**
 * Renders a section heading and optional subheading.
 *
 * @param header The header model for the section.
 */
@Composable
private fun SectionHeader(header: GridHeader) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = header.header,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF202124)
        )
        // Subheaders are optional so compact sections can use only a title.
        header.subHeader?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )
        }
    }
}

/**
 * Renders a wide genre card for the one-column section.
 *
 * @param genre    The genre model to display.
 * @param modifier Caller-provided modifier, typically including click behavior.
 */
@Composable
private fun GenreCard(genre: Genre, modifier: Modifier = Modifier) {
    CardShell(modifier = modifier) {
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

/**
 * Renders a movie item using the shared poster-card treatment.
 *
 * @param movie    The movie model to display.
 * @param modifier Caller-provided modifier, typically including click behavior.
 */
@Composable
private fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    PosterCard(title = movie.name, imageName = movie.cover, modifier = modifier)
}

/**
 * Renders an actor item using the shared poster-card treatment.
 *
 * @param actor    The actor model to display.
 * @param modifier Caller-provided modifier, typically including click behavior.
 */
@Composable
private fun ActorCard(actor: Actor, modifier: Modifier = Modifier) {
    PosterCard(title = actor.name, imageName = actor.image, modifier = modifier)
}

/**
 * Renders an image-first card used by poster-like sample content.
 *
 * @param title    Text shown below the image.
 * @param imageName Drawable resource name for the card image.
 * @param modifier Caller-provided modifier for layout and interaction.
 */
@Composable
private fun PosterCard(title: String, imageName: String, modifier: Modifier = Modifier) {
    CardShell(modifier = modifier) {
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

/**
 * Provides shared card styling for sample grid items.
 *
 * @param modifier Caller-provided modifier applied to the card.
 * @param content  Card body content.
 */
@Composable
private fun CardShell(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }
}

/**
 * Resolves a drawable by resource name and renders it with a placeholder fallback.
 *
 * @param name               Drawable resource entry name.
 * @param contentDescription Accessibility description for the image.
 * @param modifier           Caller-provided image modifier.
 */
@Composable
private fun NamedImage(name: String, contentDescription: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageId = remember(name) {
        // Resource names come from sample data, so fall back when the drawable is absent.
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
