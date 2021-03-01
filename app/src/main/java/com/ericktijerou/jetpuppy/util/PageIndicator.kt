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
package com.ericktijerou.jetpuppy.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    pagesCount: Int,
    currentPageIndex: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.wrapContentSize()) {
        for (pageIndex in 0 until pagesCount) {
            val (tint, width) = if (currentPageIndex == pageIndex) {
                color to 16.dp
            } else {
                color.copy(alpha = 0.5f) to 4.dp
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
