package com.example.amphibians.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian

@Composable
fun MainScreen(
    uiState: AppUiState
) {
    when (uiState) {
        is AppUiState.Loading -> LoadingScreen()
        is AppUiState.Error -> ErrorScreen()
        is AppUiState.Success -> SuccessScreen(list = uiState.amphibianList)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = Modifier.size(300.dp)
        )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_connection_error),
        contentDescription = stringResource(R.string.connection_error),
        modifier = Modifier.size(300.dp)
    )
}

@Composable
fun SuccessScreen(
    list: List<Amphibian>,
    modifier: Modifier = Modifier,
) {
    AmphibianList(amphibians = list)
}

@Composable
fun AmphibianPhotoCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
    ) {
        Text(
            text = "${amphibian.name} (${amphibian.type})",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 0.dp,
                    end = 0.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
            style = MaterialTheme.typography.headlineSmall,
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(amphibian.imgSrc)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.amphibian),
            error = painterResource(id = R.drawable.ic_connection_error),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = amphibian.description,
            modifier = modifier.padding(2.dp),
        )
    }
}

@Composable
fun AmphibianList(amphibians: List<Amphibian>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = amphibians,
            key = { amphibian -> amphibian.name }
        ) { amphibian ->
            AmphibianPhotoCard(
                amphibian = amphibian,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }

    }
}
