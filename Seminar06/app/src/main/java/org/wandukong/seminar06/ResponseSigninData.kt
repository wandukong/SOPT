package org.wandukong.seminar06

data class ResponseSigninData(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val email: String,
        val password: String,
        val userName: String
    )
}