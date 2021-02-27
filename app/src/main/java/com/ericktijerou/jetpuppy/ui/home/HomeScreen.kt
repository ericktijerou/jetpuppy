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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.util.JetpuppyHelper

@Composable
fun HomeScreen(modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            DogList(
                title = R.string.label_recommended,
                caption = R.string.caption_recommended,
                dogs = JetpuppyHelper.generatePuppyDataSet(),
                modifier = Modifier.padding(top = 16.dp),
                navigateTo = { /*Empty*/ }
            )
        }
        item {
            ShelterList(
                title = R.string.label_shelter,
                caption = R.string.caption_shelter,
                shelters = JetpuppyHelper.generateShelterDataSet()
            )
        }
        item {
            DogList(
                title = R.string.label_near_you,
                caption = R.string.caption_near_you,
                dogs = JetpuppyHelper.generatePuppyDataSet(),
                modifier = Modifier.padding(top = 16.dp),
                navigateTo = { /*Empty*/ }
            )
        }
        item {
            DogList(
                title = R.string.label_senior,
                caption = R.string.caption_senior,
                dogs = JetpuppyHelper.generateSeniorDataSet(),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                navigateTo = { /*Empty*/ }
            )
        }
    }
}
