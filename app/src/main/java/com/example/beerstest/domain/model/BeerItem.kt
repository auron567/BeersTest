package com.example.beerstest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class BeerItem

@Parcelize
data class BeerEntity(
    val id: Int = 0,
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val productionYear: String = ""
) : BeerItem(), Parcelable

object BeerLoader : BeerItem()
