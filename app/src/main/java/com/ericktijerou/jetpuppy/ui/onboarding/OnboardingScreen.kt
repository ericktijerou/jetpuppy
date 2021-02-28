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
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.util.Pager
import com.ericktijerou.jetpuppy.util.PagerState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(items: List<OnboardingPage>, viewModel: OnboardingViewModel, skip: () -> Unit = {}) {
    val pagerState = remember { PagerState() }
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    val skipInternal = {
        skip()
        viewModel.setOnboarding()
    }
    val onboardingPage = items[pagerState.currentPage]
    val backgroundColor by animateColorAsState(
        onboardingPage.color,
        spring(DampingRatioLowBouncy, StiffnessVeryLow)
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (pagerIndicator, button, options) = createRefs()
        val guideline = createGuidelineFromBottom(0.2f)

        Pager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingPage(items[page], backgroundColor)
        }

        PageIndicator(
            pagesCount = items.count(),
            currentPageIndex = pagerState.currentPage,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(pagerIndicator) {
                    top.linkTo(guideline)
                    linkTo(start = parent.start, end = parent.end)
                }
        )

        AnimatedVisibility(
            visible = pagerState.currentPage != pagerState.maxPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(options) {
                    bottom.linkTo(parent.bottom)
                },
            enter = slideInVertically(initialOffsetY = { 100 }),
            exit = slideOutVertically(targetOffsetY = { 100 })
        ) {
            OnboardingOptions(skipInternal)
        }

        AnimatedVisibility(
            visible = pagerState.currentPage == pagerState.maxPage,
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom, 24.dp)
                width = Dimension.fillToConstraints
            },
            enter = slideInHorizontally(initialOffsetX = { 500 }) + fadeIn(),
            exit = fadeOut(),
        ) {
            Button(
                modifier = Modifier.wrapContentWidth(),
                onClick = skipInternal,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = stringResource(R.string.label_get_started),
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Composable
fun OnboardingOptions(skip: () -> Unit) {
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
                color = Color.White
            )
        }

        TextButton(
            onClick = {},
            modifier = Modifier
                .clickable(onClick = { })
                .constrainAs(textNext) {
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(R.string.label_next),
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}

@Composable
fun OnboardingPage(item: OnboardingPage, color: Color) {
    Surface(color = color) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, title, subtitle) = createRefs()
            val guideline = createGuidelineFromBottom(0.2f)
            val animationSpec = remember { LottieAnimationSpec.RawRes(item.animation) }
            val animationState =
                rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState,
                modifier = Modifier
                    .padding(32.dp)
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
                color = Color.White,
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
                color = Color.White,
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
}

@Composable
private fun PageIndicator(pagesCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.wrapContentSize()) {
        for (pageIndex in 0 until pagesCount) {
            val (tint, width) = if (currentPageIndex == pageIndex) {
                Color.White to 16.dp
            } else {
                Color.White.copy(alpha = 0.5f) to 4.dp
            }
            Spacer(
                modifier = Modifier
                    .padding(4.dp)
                    .height(4.dp)
                    .width(width)
                    .background(tint, RoundedCornerShape(percent = 50))
            )
        }
    }
}
