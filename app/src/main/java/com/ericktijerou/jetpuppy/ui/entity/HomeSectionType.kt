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

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef(
    HomeSectionType.NEAR_YOU,
    HomeSectionType.RECOMMENDED,
    HomeSectionType.SENIOR,
    HomeSectionType.SHELTER
)

annotation class HomeSectionType {
    companion object {
        const val NEAR_YOU = "near_you"
        const val RECOMMENDED = "recommended"
        const val SENIOR = "senior"
        const val SHELTER = "shelter"
    }
}
