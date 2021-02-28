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
package com.ericktijerou.jetpuppy.ui.dog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.entity.Dog
import com.ericktijerou.jetpuppy.util.EMPTY
import com.ericktijerou.jetpuppy.util.SuperellipseCornerShape
import com.ericktijerou.jetpuppy.util.lerp
import com.ericktijerou.jetpuppy.util.verticalGradientScrim
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.launch

private val InfoContainerMaxHeight = 400.dp
private val InfoContainerMinHeight = 90.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogScreen(viewModel: DogViewModel, dogId: String, onBackPressed: () -> Unit) {
    BoxWithConstraints {
        val sheetState = rememberSwipeableState(SheetState.Open)
        val infoMaxHeightInPixels = with(LocalDensity.current) { InfoContainerMaxHeight.toPx() }
        val infoMinHeightInPixels = with(LocalDensity.current) { InfoContainerMinHeight.toPx() }
        val dragRange = infoMaxHeightInPixels - infoMinHeightInPixels
        val scope = rememberCoroutineScope()
        val dog = viewModel.getPuppyById(dogId)
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .swipeable(
                    state = sheetState,
                    anchors = mapOf(
                        0f to SheetState.Closed,
                        -dragRange to SheetState.Open
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical
                )
        ) {
            val openFraction = if (sheetState.offset.value.isNaN()) {
                0f
            } else {
                -sheetState.offset.value / dragRange
            }.coerceIn(0f, 1f)
            val (image, containerInfo, back, share) = createRefs()
            val offsetY = lerp(
                infoMaxHeightInPixels,
                0f,
                openFraction
            )

            CoilImage(
                data = dog.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = EMPTY,
                modifier = Modifier
                    .clickable(
                        enabled = sheetState.currentValue == SheetState.Open,
                        onClick = {
                            scope.launch {
                                sheetState.animateTo(SheetState.Closed)
                            }
                        })
                    .constrainAs(image) {
                        linkTo(
                            start = parent.start,
                            top = parent.top,
                            end = parent.end,
                            bottom = containerInfo.top,
                            bottomMargin = (-32).dp
                        )
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .verticalGradientScrim(
                        color = Color.Black.copy(alpha = 0.3f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
            )

            IconButton(onClick = onBackPressed, Modifier.constrainAs(back) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = EMPTY,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = { }, Modifier.constrainAs(share) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = EMPTY,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier
                    .constrainAs(containerInfo) {
                        linkTo(start = parent.start, end = parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        val actualHeight = InfoContainerMaxHeight - offsetY.dp
                        height = Dimension.value(actualHeight.coerceAtLeast(InfoContainerMinHeight))
                    }
            ) {
                InfoContainer(
                    dog,
                    Modifier
                        .height(InfoContainerMinHeight)
                        .clickable(
                            enabled = sheetState.currentValue == SheetState.Closed,
                            onClick = {
                                scope.launch {
                                    sheetState.animateTo(SheetState.Open)
                                }
                            })
                )
            }
        }
    }
}

@Composable
fun InfoContainer(dog: Dog, modifier: Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {

        val rowsModifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 20.dp)
        TitleRow(title = "${dog.name}, ${dog.age}", breed = dog.breed, modifier)
        Text(
            text = stringResource(id = dog.summary),
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            modifier = rowsModifier
        )
        listOf(
            R.string.label_sex to dog.gender,
            R.string.label_weight to dog.weight,
            R.string.label_color to dog.color
        ).forEach {
            InfoItemRow(modifier = rowsModifier, label = it.first, value = it.second)
        }
        AdoptButton()
    }
}

@Composable
fun AdoptButton() {
    Card(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .height(48.dp)
            .fillMaxSize(),
        shape = SuperellipseCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            Text(
                text = stringResource(R.string.label_adopt),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun TitleRow(title: String, breed: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = breed,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.Black.copy(alpha = 0.6f)
            )
        }

        IconButton(
            onClick = { },
            Modifier
                .padding(end = 24.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = EMPTY,
                Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun InfoItemRow(@StringRes label: Int, value: String, modifier: Modifier) {
    Row(modifier) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(120.dp),
            color = Color.Black.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W500)
        )
    }
}

enum class SheetState { Open, Closed }
