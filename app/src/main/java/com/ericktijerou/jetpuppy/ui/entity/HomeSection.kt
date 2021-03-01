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
package com.ericktijerou.jetpuppy.ui.entity

sealed class HomeSection {
    abstract val title: Int
    abstract val caption: Int
    abstract val list: List<Any>
}

data class HomeDogSection(
    override val title: Int,
    override val caption: Int,
    override val list: List<Dog>
) : HomeSection()

data class HomeShelterSection(
    override val title: Int,
    override val caption: Int,
    override val list: List<Shelter>
) : HomeSection()
