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