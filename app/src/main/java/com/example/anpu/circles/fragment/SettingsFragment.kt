package com.example.anpu.circles.fragment

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ajguan.library.EasyRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.anpu.circles.AddCircleActivity
import com.example.anpu.circles.R
import com.example.anpu.circles.adapter.CirclesAdapter
import com.example.anpu.circles.model.CircleBean
import com.example.anpu.circles.model.CircleItemLab
import com.example.anpu.circles.model.CircleResponseBean
import com.google.gson.Gson
import okhttp3.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.io.IOException


/**
 * Created by anpu on 2018/3/5.
 * rewrite in Koilin on 2018/4/19
 * @author Anpu Li & Wenhe Li
 */

class SettingsFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: EasyRefreshLayout
    private lateinit var mAdapter: CirclesAdapter
    private val mLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var addButton: FloatingActionButton
    private val queryCirlcesUrl = "http://steins.xin:8001/circles/querycircles"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.tab_settings, container, false)
        mRecyclerView = rootView.findViewById<View>(R.id.circles_recycler) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_circle)
        addButton = rootView.findViewById(R.id.fab_add)

        val itemsData = CircleItemLab.get(context).circleItems
        mAdapter = CirclesAdapter(R.layout.circle_item, itemsData)

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, view, position -> toast("you click:$position") }

        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.circle_avatar -> toast( "jump to avatar")
                R.id.circle_title -> toast( "you click title")
            }
        }

        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout.addEasyEvent(object : EasyRefreshLayout.EasyEvent {
            override fun onLoadMore() {
                //pull upon
                Handler().postDelayed({ mSwipeRefreshLayout.loadMoreComplete({ }, 500) }, 2000)
                // This is a local test
                val len = CircleItemLab.get(activity).length()
                val eventId = CircleItemLab.get(activity).getCircleItem(len - 1).id

                getEvent(eventId)
            }

            override fun onRefreshing() {
                //pull down
                Handler().postDelayed({ mSwipeRefreshLayout.refreshComplete() }, 2000)
                // just an expedient
                // clear the data set and reload it
                CircleItemLab.get(activity).clear()
                getEvent(-1)
            }
        })

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CircleItemLab.get(activity).circleItems
        getEvent(-1)
        addButton.onClick { startActivity<AddCircleActivity>() }
    }

    private fun getEvent(event_id: Int) {
        val gson = Gson()

        val circle = CircleBean(event_id)

        val circleString = gson.toJson(circle)
        // post to the server
        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), circleString)
        val request = Request.Builder()
                .post(body)
                .url(queryCirlcesUrl)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread { Toast.makeText(activity, "Failure to connect to the server", Toast.LENGTH_LONG).show() }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val ans = response.body().string()
                val circleResponseBean = gson.fromJson(ans, CircleResponseBean::class.java)
                if (circleResponseBean.status == 0) {
                    activity?.runOnUiThread { Toast.makeText(activity, "Account is not activated.", Toast.LENGTH_LONG).show() }
                } else {
                    if (circleResponseBean.data.isEmpty()) {
                        activity?.runOnUiThread { toast("no more message") }
                    } else {
                        for (dataBean in circleResponseBean.data) {
                            CircleItemLab.get(activity).addCircleItem(CircleBean(dataBean.title,
                                    dataBean.content, dataBean.avatar, dataBean.nickname, dataBean.event_id))

                        }
                        activity?.runOnUiThread { mAdapter.notifyDataSetChanged() }
                    }
                }
            }
        })

    }

}
