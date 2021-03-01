/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetpuppy.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.theme.BlueOnboarding
import com.ericktijerou.jetpuppy.ui.theme.JetpuppyTheme
import com.ericktijerou.jetpuppy.util.JetPuppyDataManager
import com.ericktijerou.jetpuppy.util.PageIndicator
import com.ericktijerou.jetpuppy.util.Pager
import com.ericktijerou.jetpuppy.util.PagerState
import com.ericktijerou.jetpuppy.util.ThemedPreview
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    skip: () -> Unit = {}
) {
    val items = viewModel.getItems()
    OnboardingScreenBody(items = items) {
        viewModel.setOnboarding()
        skip()
    }
}

@Composable
fun OnboardingScreenBody(items: List<OnboardingPage>, onFinish: () -> Unit) {
    val pagerState = remember { PagerState() }
    val (isFinish, setFinish) = remember { mutableStateOf(false) }
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    val onboardingPage = items[pagerState.currentPage]
    val color = if (isFinish) MaterialTheme.colors.primary else onboardingPage.color
    val backgroundColor by animateColorAsState(
        color,
        spring(DampingRatioLowBouncy, StiffnessLow)
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        val (pagerIndicator, button, options, wave, waveBody) = createRefs()
        val guideline = createGuidelineFromBottom(0.2f)
        val guidelineWave = createGuidelineFromTop(0.5f)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .constrainAs(waveBody) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guidelineWave)
                    height = Dimension.fillToConstraints
                }
        )
        Image(
            painter = painterResource(R.drawable.ic_wave),
            contentDescription = "",
            modifier = Modifier
                .aspectRatio(4.5f)
                .constrainAs(wave) {
                    top.linkTo(guidelineWave)
                },
            colorFilter = ColorFilter.tint(backgroundColor)
        )
        Pager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingPage(items[page], isFinish)
        }

        if (isFinish) return@ConstraintLayout

        PageIndicator(
            pagesCount = items.count(),
            currentPageIndex = pagerState.currentPage,
            color = JetpuppyTheme.colors.textSecondaryColor,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(pagerIndicator) {
                    top.linkTo(guideline)
                    linkTo(start = parent.start, end = parent.end)
                }
        )

        OnboardingOptions(
            visible = pagerState.currentPage != pagerState.maxPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(options) {
                    bottom.linkTo(parent.bottom)
                },
            skip = { setFinish(true) },
            next = {
                pagerState.currentPage += 1
            }
        )

        GetStartedButton(
            visible = pagerState.currentPage == pagerState.maxPage,
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom, 24.dp)
                width = Dimension.fillToConstraints
            },
            finish = { setFinish(true) }
        )
    }

    LaunchedEffect(isFinish) {
        if (isFinish) {
            delay(500)
            onFinish()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GetStartedButton(
    visible: Boolean,
    modifier: Modifier = Modifier,
    finish: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInHorizontally(initialOffsetX = { 1300 }, animationSpec = tween()),
        exit = fadeOut()
    ) {
        Button(
            modifier = Modifier.wrapContentWidth(),
            onClick = finish,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueOnboarding),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = stringResource(R.string.label_get_started),
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingOptions(
    visible: Boolean,
    modifier: Modifier = Modifier,
    skip: () -> Unit,
    next: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        exit = fadeOut()
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (textSkip, textNext) = createRefs()
            TextButton(
                onClick = skip,
                modifier = Modifier
                    .constrainAs(textSkip) {
                        start.linkTo(parent.start)
                    }
            ) {
                Text(
                    text = stringResource(R.string.label_skip),
                    style = MaterialTheme.typography.button.merge(TextStyle(fontWeight = FontWeight.Medium)),
                    color = JetpuppyTheme.colors.textPrimaryColor
                )
            }

            TextButton(
                onClick = next,
                modifier = Modifier
                    .constrainAs(textNext) {
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = stringResource(R.string.label_next),
                    style = MaterialTheme.typography.button,
                    color = JetpuppyTheme.colors.textPrimaryColor
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(item: OnboardingPage, isFinish: Boolean) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isFinish) return@ConstraintLayout
        val (image, title, subtitle) = createRefs()
        val guideline = createGuidelineFromBottom(0.2f)
        val animationSpec = remember { LottieAnimationSpec.RawRes(item.animation) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
        LottieAnimation(
            spec = animationSpec,
            animationState = animationState,
            modifier = Modifier
                .padding(56.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        top = parent.top,
                        end = parent.end,
                        bottom = title.top
                    )
                },
        )
        Text(
            text = stringResource(id = item.title),
            style = MaterialTheme.typography.h5,
            color = JetpuppyTheme.colors.textPrimaryColor,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(subtitle.top, 16.dp)
                    width = Dimension.fillToConstraints
                },
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = item.subtitle),
            color = JetpuppyTheme.colors.textPrimaryColor,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .constrainAs(subtitle) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(guideline, 32.dp)
                    width = Dimension.fillToConstraints
                },
            textAlign = TextAlign.Center
        )
    }
}

@Preview("Onboarding screen body")
@Composable
fun PreviewOnboardingScreenBody() {
    ThemedPreview {
        OnboardingScreenBody(JetPuppyDataManager.onboardingItems) {}
    }
}

@Preview("Onboarding screen body dark")
@Composable
fun PreviewOnboardingScreenBodyDark() {
    ThemedPreview(darkTheme = true) {
        OnboardingScreenBody(JetPuppyDataManager.onboardingItems) {}
    }
}
