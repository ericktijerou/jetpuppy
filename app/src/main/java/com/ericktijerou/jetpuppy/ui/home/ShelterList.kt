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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetpuppy.ui.entity.Shelter
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ShelterList(
    @StringRes title: Int,
    @StringRes caption: Int,
    shelters: List<Shelter>
) {
    Card(modifier = Modifier.padding(top = 16.dp), backgroundColor = MaterialTheme.colors.primary) {
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
            ShelterRow(shelters = shelters)
        }
    }
}

@Composable
fun ShelterRow(
    shelters: List<Shelter>,
) {
    val lastIndex = shelters.size - 1
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(items = shelters) { index: Int, item: Shelter ->
            ShelterItem(shelter = item)
            if (index < lastIndex) Spacer(Modifier.width(16.dp))
        }
    }
}

@Composable
fun ShelterItem(
    shelter: Shelter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(120.dp)
    ) {
        CoilImage(
            data = shelter.avatar,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = shelter.name,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
