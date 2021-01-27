package org.wandukong.maskinfo

data class ResponseStoreData(
    val count: Int,
    val stores: List<Store>
) {
    data class Store(
        val addr: String,
        val code: String,
        val created_at: String,
        val lat: Double,
        val lng: Double,
        val name: String,
        val remain_stat: String,
        val stock_at: String,
        val type: String,
        var distance: Double
    )
}