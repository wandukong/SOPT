package org.wandukong.app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamData(
    val title : String,
    val subTitle : String,
    val date : String,
    val detail : String
): Parcelable
