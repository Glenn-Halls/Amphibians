package com.example.amphibians.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
    Text(text = list[0].name)
    Text(text = list.size.toString())
    Text(text = list[0].description)
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
        Text(text = amphibian.name)
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(amphibian.imgSrc)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.amphibian),
            error = painterResource(id = R.drawable.ic_connection_error),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = amphibian.description)
    }
}
