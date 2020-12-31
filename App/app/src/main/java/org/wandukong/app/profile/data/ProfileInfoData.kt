package org.wandukong.app.profile.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileInfoData (
    val title : String,
    val content : String,
    val detail : String
): Parcelable