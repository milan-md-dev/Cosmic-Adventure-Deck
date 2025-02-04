package com.miles.cosmicadventuredeck.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.miles.cosmicadventuredeck.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onNavigateToCardScreen: () -> Unit
) {

    val activity = (LocalContext.current as? Activity)

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val imageSize =
        when {
            screenWidth < 600 -> (screenWidth * 0.50f).dp
            screenWidth in 601..839 -> (screenWidth * 0.35f).dp
            screenWidth > 840 -> (screenWidth * 0.25f).dp
            else -> {
                (screenWidth * 0.50).dp
            }
        }

    BackHandler(
        enabled = true
    ) {
        activity?.finishAffinity()
    }

    val textAnimationDuration = 1500
    val buttonAnimationDuration = 1200
    val textDelay = 1400
    var firstLine by remember { mutableStateOf(false) }
    var secondLine by remember { mutableStateOf(false) }
    var thirdLine by remember { mutableStateOf(false) }
    var mainTitle by remember { mutableStateOf(false) }
    var buttonShown by remember { mutableStateOf(false) }

    val firstLineAlpha by animateFloatAsState(
        targetValue = if (firstLine) 1f else 0f,
        animationSpec = tween(durationMillis = textAnimationDuration),
        label = ""
    )

    val secondLineAlpha by animateFloatAsState(
        targetValue = if (secondLine) 1f else 0f,
        animationSpec = tween(durationMillis = textAnimationDuration),
        label = ""
    )

    val thirdLineAlpha by animateFloatAsState(
        targetValue = if (thirdLine) 1f else 0f,
        animationSpec = tween(durationMillis = textAnimationDuration),
        label = ""
    )

    val mainTitleAlpha by animateFloatAsState(
        targetValue = if (mainTitle) 1f else 0f,
        animationSpec = tween(durationMillis = textAnimationDuration),
        label = ""
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (buttonShown) 1f else 0f,
        animationSpec = tween(durationMillis = buttonAnimationDuration),
        label = ""
    )

    LaunchedEffect(Unit) {
        delay(500)
        firstLine = true
        secondLine = true
        thirdLine = true
        delay(textDelay.toLong())
        mainTitle = true
        delay(textDelay.toLong())
        buttonShown = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            modifier = Modifier
                .padding(32.dp),
            elevation = CardDefaults.cardElevation()
        ) {
            Content(
                firstLineAlpha = firstLineAlpha,
                secondLineAlpha = secondLineAlpha,
                thirdLineAlpha = thirdLineAlpha,
                mainTitleAlpha = mainTitleAlpha,
                buttonShown = buttonShown,
                buttonAlpha = buttonAlpha,
                imageSize = imageSize,
                onClick = { onNavigateToCardScreen() }
            )
        }
    }
}

@Composable
fun Content(
    firstLineAlpha: Float,
    secondLineAlpha: Float,
    thirdLineAlpha: Float,
    mainTitleAlpha: Float,
    buttonShown: Boolean,
    buttonAlpha: Float,
    imageSize: Dp,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 24.dp, vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = stringResource(R.string.welcome_text_1),
                fontStyle = FontStyle.Normal,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFD700).copy(alpha = firstLineAlpha)
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.welcome_text_2),
                fontStyle = FontStyle.Normal,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFD700).copy(alpha = secondLineAlpha)
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.welcome_text_3),
                fontStyle = FontStyle.Normal,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFD700).copy(alpha = thirdLineAlpha)
            )
        }
        item {
            MainTitleAnimation(
                text = stringResource(R.string.main_name),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                shimmerColor = Color(0xFFFFD700).copy(alpha = mainTitleAlpha),
                textStyle = TextStyle(lineHeight = 2.em),
                modifier = Modifier.padding(top = 44.dp)
            )
        }
        item {
            Box(
                modifier = Modifier.size(imageSize),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = buttonShown,
                    enter = fadeIn(animationSpec = tween(2800, easing = LinearEasing))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.textbuttonwithfade),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { onClick() }
                                .alpha(buttonAlpha)
                                .clip(CircleShape)
                                .size(imageSize)
                        )
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MainTitleAnimation(
    text: String,
    textAlign: TextAlign,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily,
    shimmerColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(1800, 2000, easing = LinearEasing)
) {
    val infiniteTransition = rememberInfiniteTransition("MainTitleTransition")

    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "ShimmerProgress"
    )

    val brush = remember(shimmerProgress) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val initialXOffset = 0f
                val totalSweepDistance = size.width * 2
                val currentPosition =
                    initialXOffset + (totalSweepDistance * shimmerProgress) - size.width

                return LinearGradientShader(
                    colors = listOf(Color.Transparent, shimmerColor, Color.Transparent),
                    from = Offset(currentPosition, 0f),
                    to = Offset(currentPosition + size.width, 0f)
                )
            }
        }
    }

    Text(
        text = text,
        textAlign = textAlign,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        modifier = modifier,
        style = textStyle.copy(brush)
    )
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onNavigateToCardScreen = { }
    )
}