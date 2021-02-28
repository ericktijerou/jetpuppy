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

import androidx.lifecycle.ViewModel
import com.ericktijerou.jetpuppy.ui.entity.Dog
import com.ericktijerou.jetpuppy.ui.entity.HomeSectionType
import com.ericktijerou.jetpuppy.ui.entity.Shelter
import com.ericktijerou.jetpuppy.util.PuppyDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(private val dataManager: PuppyDataManager) : ViewModel() {
    fun getPuppyListBySection(@HomeSectionType sectionType: String): List<Dog> {
        return dataManager.puppies.filter { it.section == sectionType }
    }

    fun getShelterList(): List<Shelter> {
        return dataManager.shelters
    }
}
