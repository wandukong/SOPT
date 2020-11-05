package org.wandukong.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileInfoData (
    val title : String,
    val content : String
): Parcelable