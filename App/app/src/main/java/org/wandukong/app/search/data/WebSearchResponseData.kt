package org.wandukong.app.search.data

data class WebSearchResponseData(
    val documents: List<Document>,
    val meta: Meta
) {
    data class Document(
        val contents: String,
        val datetime: String,
        val title: String,
        val url: String
    )

    data class Meta(
        val is_end: Boolean,
        val pageable_count: Int,
        val total_count: Int
    )
}