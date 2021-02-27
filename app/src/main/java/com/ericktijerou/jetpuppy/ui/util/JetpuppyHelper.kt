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
package com.ericktijerou.jetpuppy.ui.util

import com.ericktijerou.jetpuppy.ui.entity.Dog
import com.ericktijerou.jetpuppy.ui.entity.Shelter
import kotlin.random.Random

object JetpuppyHelper {
    fun generatePuppyDataSet(): List<Dog> {
        val list = mutableListOf<Dog>()
        for (i in 1..10) {
            list.add(
                Dog(
                    name = "Friend $i",
                    age = "",
                    gender = "",
                    imageUrl = puppyList[Random.nextInt(puppyList.size)],
                    bread = "Bread $i"
                )
            )
        }
        return list
    }

    fun generateShelterDataSet(): List<Shelter> {
        val list = mutableListOf<Shelter>()
        for (i in 1..10) {
            list.add(
                Shelter(
                    name = "Shelter $i",
                    avatar = shelterList[Random.nextInt(shelterList.size)]
                )
            )
        }
        return list
    }

    fun generateSeniorDataSet(): List<Dog> {
        val list = mutableListOf<Dog>()
        for (i in 1..10) {
            list.add(
                Dog(
                    name = "Friend $i",
                    age = "",
                    gender = "",
                    imageUrl = seniorList[Random.nextInt(seniorList.size)],
                    bread = "Bread $i"
                )
            )
        }
        return list
    }

    private val puppyList = listOf(
        "https://c.stocksy.com/a/B2l400/z9/1134115.jpg",
        "https://c.stocksy.com/a/7dt000/z9/213845.jpg",
        "https://c.stocksy.com/a/DOg400/z9/1116261.jpg",
        "https://c.stocksy.com/a/TbP000/za/98423.jpg",
        "https://images.pexels.com/photos/5256698/pexels-photo-5256698.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/4587971/pexels-photo-4587971.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/4588435/pexels-photo-4588435.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/6291572/pexels-photo-6291572.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/4223623/pexels-photo-4223623.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/59965/dog-young-dog-puppy-59965.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://c.stocksy.com/a/X1o500/za/1383935.jpg",
        "https://images.pexels.com/photos/2067447/pexels-photo-2067447.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/3299894/pexels-photo-3299894.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/3299896/pexels-photo-3299896.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/3908762/pexels-photo-3908762.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/1317111/pexels-photo-1317111.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    )

    private val seniorList = listOf(
        "https://images.pexels.com/photos/5867321/pexels-photo-5867321.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/220938/pexels-photo-220938.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/1629777/pexels-photo-1629777.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/5472350/pexels-photo-5472350.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/5257587/pexels-photo-5257587.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/800330/pexels-photo-800330.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/4588001/pexels-photo-4588001.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/4197483/pexels-photo-4197483.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/895259/pexels-photo-895259.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/662417/pexels-photo-662417.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/1436134/pexels-photo-1436134.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        "https://images.pexels.com/photos/220938/pexels-photo-220938.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
    )

    private val shelterList = listOf(
        "https://image.freepik.com/free-vector/cute-corgi-dog-playing-box-cartoon_138676-2307.jpg",
        "https://image.freepik.com/free-vector/cute-corgi-astronaut-flying-cartoon-vector-illustration-animal-science-concept-isolated-vector-flat-cartoon-style_138676-1931.jpg",
        "https://img.freepik.com/free-vector/cute-corgi-astronaut-with-rocket-cartoon-icon-illustration-animal-space-icon-concept-isolated-flat-cartoon-style_138676-1351.jpg?size=338&ext=jpg",
        "https://image.freepik.com/free-vector/cute-corgi-drink-milk-tea-boba-cartoon-vector-illustration-animal-drink-concept-isolated-vector-flat-cartoon-style_138676-1943.jpg",
        "https://image.freepik.com/free-vector/cute-dog-box-cartoon-illustration-animal-icon-concept_138676-1920.jpg",
        "https://img.freepik.com/free-vector/home-pets-logo-icon_126523-600.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-vector/cute-australian-shepherd_138676-2071.jpg?size=338&ext=jpg",
        "https://img.freepik.com/free-vector/cute-corgi-dog-holding-heart-cartoon_138676-2533.jpg?size=338&ext=jpg",
        "https://img.freepik.com/free-vector/cute-bulldog-playing-skateboard-cartoon-vector-illustration-animal-sport-concept-isolated-vector-flat-cartoon-style_138676-1925.jpg?size=338&ext=jpg",
        "https://img.freepik.com/free-vector/cute-pug-with-cup-coffee-cartoon-illustration-animal-drink-concept-isolated-flat-cartoon_138676-2286.jpg?size=338&ext=jpg",
        "https://img.freepik.com/free-vector/flat-pug-with-butterfly_23-2147697447.jpg?size=338&ext=jpg",
        "https://image.freepik.com/free-vector/hipster-monocle-bow-tie-dog_23-2147493779.jpg?2"
    )
}
