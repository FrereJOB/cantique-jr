package com.jesusrevient.cantique.models

import androidx.annotation.DrawableRes

data class SocialLink(
    val name: String,
    val url: String,
    @DrawableRes val iconResId: Int
)
