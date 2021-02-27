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
package com.ericktijerou.jetpuppy.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.jetpuppy.ui.entity.Dog
import com.ericktijerou.jetpuppy.ui.util.Screen
import com.ericktijerou.jetpuppy.ui.util.verticalGradientScrim
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogList(
    @StringRes title: Int,
    @StringRes caption: Int,
    dogs: List<Dog>,
    modifier: Modifier,
    navigateTo: (Screen) -> Unit
) {
    Card(modifier = modifier, backgroundColor = MaterialTheme.colors.primary) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = stringResource(id = caption),
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
            )
            DogRow(dogs = dogs, navigateTo = navigateTo)
        }
    }
}

@Composable
fun DogRow(
    dogs: List<Dog>,
    navigateTo: (Screen) -> Unit
) {
    val lastIndex = dogs.size - 1
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(items = dogs) { index: Int, item: Dog ->
            DogItem(dog = item, navigateTo = { /*TODO*/ })
            if (index < lastIndex) Spacer(Modifier.width(16.dp))
        }
    }
}

@Composable
fun DogItem(
    dog: Dog,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(shape = RoundedCornerShape(8.dp)) {
        Box(
            modifier = modifier
                .size(120.dp, 180.dp)
                .clickable(onClick = { })
        ) {
            CoilImage(
                data = dog.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .verticalGradientScrim(
                        color = Color.Black.copy(alpha = 0.5f),
                        startYPercentage = 0f,
                        endYPercentage = 1f
                    )
                    .padding(8.dp)

            ) {
                Text(
                    text = dog.name,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = dog.bread,
                    style = MaterialTheme.typography.body2.copy(fontSize = 10.sp),
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 12.dp)
                    .size(20.dp)
                    .align(Alignment.BottomEnd),
                tint = Color.White
            )
        }
    }
}
