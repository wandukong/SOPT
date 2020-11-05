package org.wandukong.app.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_team.*
import org.wandukong.app.ItemTouchCallback
import org.wandukong.app.R
import org.wandukong.app.adapter.TeamAdapter
import org.wandukong.app.model.TeamData

class TeamFragment : Fragment() {

    private lateinit var teamAdapter: TeamAdapter
    private var teamList = mutableListOf<TeamData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView(view)
    }

    private fun createRecyclerView(view : View){
        teamList.add(TeamData("Manchester United", "Winner", "1878-03-05", "Red Devils"))
        teamList.add(TeamData("Tottenham Hotspur", "Runner up", "1905-03-10", "Spurs"))
        teamList.add(TeamData("Liverpool", "3rd", "1892-03-15", "Reds"))
        teamList.add(TeamData("Manchester city", "4th", "1894-04-16", "Blues"))
        teamList.add(TeamData("Chelsea", "5th", "1905-03-10", "The Blues"))
        teamList.add(TeamData("Arsenal", "6th", "1886-10-01", "The Gunners"))
        teamList.add(TeamData("Leicester city", "7th", "18884-01-01", "The Foxes"))
        teamList.add(TeamData("Everton", "8th", "1878-01-01", "The Toffees"))

        teamAdapter = TeamAdapter(view.context)
        teamAdapter.touchHelper = ItemTouchHelper(ItemTouchCallback(teamAdapter))
        teamAdapter.data = teamList

        rcv_teamList_team.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamAdapter
        }
        teamAdapter.notifyDataSetChanged()
        teamAdapter.touchHelper.attachToRecyclerView(rcv_teamList_team)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        var subMenu: Menu? = menu?.addSubMenu("SORT")
        subMenu?.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "List")
        subMenu?.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Grid")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            Menu.FIRST + 1 -> {
                teamAdapter.viewType = 1
                rcv_teamList_team.layoutManager = LinearLayoutManager(context)
            }
            Menu.FIRST + 2 -> {
                teamAdapter.viewType = 2
                rcv_teamList_team.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}