package org.wandukong.app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_web_search.*
import org.wandukong.app.R
import org.wandukong.app.search.data.SearchData
import org.wandukong.app.search.data.WebSearchResponseData
import org.wandukong.app.network.WebServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebSearchFragment : Fragment() {

    private var searchDataList = mutableListOf<SearchData>()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_request_search.setOnClickListener {
            var call : Call<WebSearchResponseData> = WebServiceImpl.service.webSearch(query = et_query_search.text.toString())
            call.enqueue(object : Callback<WebSearchResponseData> {
                override fun onResponse(call: Call<WebSearchResponseData>, response: Response<WebSearchResponseData>) {
                    response.takeIf { it.isSuccessful }
                            ?.body()
                            ?.let { webSearchResponseData ->
                                searchAdapter.data.clear()
                                for (i in 0 until webSearchResponseData.documents.size)
                                    searchDataList.add(
                                        SearchData(WebServiceImpl.removeHTMLTag(webSearchResponseData.documents[i].title),
                                            webSearchResponseData.documents[i].url,
                                            webSearchResponseData.documents[i].datetime.slice(IntRange(0,9)) + " "+webSearchResponseData.documents[i].datetime.slice(IntRange(11,15)),
                                            WebServiceImpl.removeHTMLTag(webSearchResponseData.documents[i].contents))
                                    )
                                searchAdapter.data = searchDataList
                                searchAdapter.notifyDataSetChanged()
                            } ?: WebServiceImpl.showError(view.context, response.errorBody())
                }

                override fun onFailure(call: Call<WebSearchResponseData>, t: Throwable) {
                }
            })
        }
        searchAdapter = SearchAdapter(view.context)
        rcv_response_search.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }
}