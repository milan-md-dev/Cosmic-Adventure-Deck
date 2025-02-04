package com.miles.cosmicadventuredeck.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.miles.cosmicadventuredeck.R
import com.miles.cosmicadventuredeck.data.CardToShow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    onBackClick: () -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {

    val cardToShow = viewModel.cardToShow.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val orientation = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val screenSize =
        when {
            screenWidth < 600 -> (screenWidth * 0.85f).dp
            screenWidth in 601..839 -> (screenWidth * 0.65f).dp
            screenWidth > 840 -> (screenWidth * 0.55f).dp
            else -> {
                (screenWidth * 0.50).dp
            }
        }


    Log.d("CardScreen", "${cardToShow.value.cardReady}")

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFFFD700)
                        )
                    }
                },
                title = {}
            )
        },
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(R.drawable.card_background),
                contentDescription = "",
                modifier = Modifier
                    .rotate(
                        if (orientation) 90f else 0f
                    )
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .scale(if (orientation) 2.2f else 1.25f)
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                modifier = if (!orientation) {
                    Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                } else {
                    Modifier
                        .width(screenSize)
                        .padding(30.dp)
                },
                elevation = CardDefaults.cardElevation()
            ) {
                if (!cardToShow.value.cardReady) {
                    GettingTheCardReady()
                    Log.d("CircularProgressionIndicator", "${cardToShow.value.cardReady}")
                } else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(40.dp)

                    ) {
                        item {
                            CardName(
                                card = cardToShow.value
                            )
                        }

                        item {
                            Spacer(
                                modifier = Modifier
                                    .height(12.dp)
                            )
                        }

                        item {
                            CardImage(
                                card = cardToShow.value
                            )
                        }

                        item {
                            Spacer(
                                modifier = Modifier
                                    .height(16.dp)
                            )
                        }

                        item {
                            CardText(
                                cardText = cardToShow.value.cardEncounterText
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun GettingTheCardReady() {
    CircularProgressIndicator(
        color = Color.Yellow
    )
}

@Composable
fun CardName(
    card: CardToShow
) {
    Text(
        text = card.cardName,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFF9F3AA),
        textAlign = TextAlign.Center
    )
}


@Composable
fun CardImage(
    card: CardToShow
) {

    val context = LocalContext.current

    val drawableId = remember(card.cardImage) {
        context.resources.getIdentifier(
            card.cardImage,
            "drawable",
            context.packageName
        )
    }

    AsyncImage(
        alignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth(),
        model = ImageRequest.Builder(LocalContext.current)
            .data(drawableId)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}

@Composable
fun CardText(
    cardText: String
) {
    Text(
        text = cardText,
        textAlign = TextAlign.Justify,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFFF9F3AA),
    )

}


@Preview(showBackground = true)
@Composable
fun CardScreenPreview(
    cardToShow: CardToShow = CardToShow(),
) {
    CardScreen(
        onBackClick = {}
    )
}