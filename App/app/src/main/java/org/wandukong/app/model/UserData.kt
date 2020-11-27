package org.wandukong.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val name : String,
    val email : String,
    val imageSrc : String,
): Parcelable
