package org.wandukong.app.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_users.*
import org.wandukong.app.ItemTouchCallback
import org.wandukong.app.R
import org.wandukong.app.service.UserServiceImpl
import org.wandukong.app.adapter.UsersAdapter
import org.wandukong.app.model.LoadUsersResponseData
import org.wandukong.app.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var userAdapter: UsersAdapter
    private var userList = mutableListOf<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView(view)
        btn_changeLayout_users.setOnClickListener {
            when(userAdapter.viewType){
                1 ->{
                    rcv_teamList_team.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                    btn_changeLayout_users.setImageResource(R.drawable.ic_baseline_list_24)
                    userAdapter.viewType = 2
                }
                2->{
                    rcv_teamList_team.layoutManager = LinearLayoutManager(context)
                    btn_changeLayout_users.setImageResource(R.drawable.ic_baseline_grid_on_24)
                    userAdapter.viewType = 1
                }
            }
        }

    }

    private fun createRecyclerView(view : View){

        val call : Call<LoadUsersResponseData> = UserServiceImpl.service2.loadUsers(page = 2)
        call.enqueue(object : Callback<LoadUsersResponseData>{
            override fun onResponse(call: Call<LoadUsersResponseData>, response: Response<LoadUsersResponseData>) {

                response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let { loadUsersData ->
                            for (i in 0 until loadUsersData.data.size){
                                userList.add(UserData(loadUsersData.data[i].first_name + " " + loadUsersData.data[i].last_name,
                                        loadUsersData.data[i].email, loadUsersData.data[i].avatar))
                            }
                            userAdapter.data = userList
                            userAdapter.notifyDataSetChanged()

                        }
            }
            override fun onFailure(call: Call<LoadUsersResponseData>, t: Throwable) {
            }
        })
        userAdapter = UsersAdapter(view.context)
        userAdapter.touchHelper = ItemTouchHelper(ItemTouchCallback(userAdapter))

        rcv_teamList_team.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
        userAdapter.touchHelper.attachToRecyclerView(rcv_teamList_team)
    }
}