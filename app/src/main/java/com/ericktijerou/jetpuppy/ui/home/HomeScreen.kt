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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetpuppy.ui.entity.HomeDogSection
import com.ericktijerou.jetpuppy.ui.entity.HomeSection
import com.ericktijerou.jetpuppy.ui.entity.HomeShelterSection
import com.ericktijerou.jetpuppy.ui.home.list.DogList
import com.ericktijerou.jetpuppy.ui.home.list.ShelterList
import com.ericktijerou.jetpuppy.util.JetPuppyDataManager
import com.ericktijerou.jetpuppy.util.ThemedPreview

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier, navigateToDog: (String) -> Unit) {
    HomeScreenBody(sections = viewModel.getHomeSectionList(), navigateToDog = navigateToDog, modifier = modifier)
}

@Composable
fun HomeScreenBody(sections: List<HomeSection>, modifier: Modifier = Modifier, navigateToDog: (String) -> Unit) {
    LazyColumn(modifier = modifier) {
        sections.forEach {
            item {
                when (it) {
                    is HomeShelterSection -> {
                        ShelterList(
                            title = it.title,
                            caption = it.caption,
                            shelters = it.list
                        )
                    }
                    is HomeDogSection -> {
                        DogList(
                            title = it.title,
                            caption = it.caption,
                            dogs = it.list,
                            modifier = Modifier.padding(top = 16.dp),
                            navigateTo = navigateToDog
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview("Home screen body")
@Composable
fun PreviewHomeScreenBody() {
    ThemedPreview {
        val sections = JetPuppyDataManager.homeSectionList
        HomeScreenBody(sections) {}
    }
}

@Preview("Home screen body dark")
@Composable
fun PreviewHomeScreenBodyDark() {
    ThemedPreview(darkTheme = true) {
        val sections = JetPuppyDataManager.homeSectionList
        HomeScreenBody(sections) {}
    }
}
