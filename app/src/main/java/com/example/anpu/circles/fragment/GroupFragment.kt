package com.example.anpu.circles.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.anpu.circles.R
import com.example.anpu.circles.adapter.ViewPageSlideShowAdapter
import com.example.anpu.circles.utilities.GlideV4ImageEngine
import com.example.anpu.circles.utilities.PermissonHelper
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter

import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.LinkedList
import java.util.Objects

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

import android.app.Activity.RESULT_OK
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.anpu.circles.ViewCircleActivity
import com.example.anpu.circles.adapter.CirclesAdapter
import com.example.anpu.circles.adapter.EventsAdapter
import com.example.anpu.circles.model.*
import com.example.anpu.circles.utilities.PermissonHelper.askStroage
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.tab_group.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by anpu on 2018/3/5.
 */

class GroupFragment : Fragment() {

    internal var images = ArrayList<String>()
    internal lateinit var banner: Banner
    internal lateinit var newsBean : NewsBean
    internal lateinit var eventsBean : EventBean
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: EventsAdapter

    private lateinit var mDivider: DividerItemDecoration


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab_group, container, false)
        banner = view.findViewById<View>(R.id.banner) as Banner
        mRecyclerView = view.findViewById(R.id.events_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

        mDivider = DividerItemDecoration(activity,DividerItemDecoration.VERTICAL)
        mDivider.setDrawable(ContextCompat.getDrawable(this.activity!!, R.drawable.custom_divider)!!)
        mRecyclerView.addItemDecoration(mDivider)


        getNews()
        getEvents()

        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                Glide.with(context).load(path).into(imageView)
            }
        })
        banner.setImages(images)
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        banner.setOnBannerListener {
            val url = newsBean.item[it].link
            browse(url, true)
        }

        banner.start()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        askStroage(activity)
        val events = null
        mAdapter = EventsAdapter(R.layout.event_item, null)

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)

        mRecyclerView.adapter = mAdapter
    }

    private fun getNews() {
        val gson = Gson()
        val user = User(UserData.getEmail())
        val userJson = gson.toJson(user)

        Log.d("json", userJson)
        // post to the server
        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
        val request = Request.Builder()
                .post(body)
                .url("http://steins.xin:8001/news")
                .build()

        //receive from server
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity!!.runOnUiThread { Toast.makeText(activity, "Failure to connect to the server", Toast.LENGTH_LONG).show() }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val ans = response.body().string()
                newsBean = gson.fromJson(ans, NewsBean::class.java)
                val imgs = ArrayList<String>()
                for (itemBean in newsBean.item) {
                    imgs.add("https://" + itemBean.img)

                }
                Log.d("group", imgs[0])
                Objects.requireNonNull<FragmentActivity>(activity).runOnUiThread({
                    val titles = ArrayList<String>()
                    for (it in newsBean.item) {
                        titles.add(it.title)
                    }

                    banner.setBannerTitles(titles)
                    banner.setImages(imgs)
                    banner.start()
                })
            }
        })
    }

    private fun getEvents() {
        val gson = Gson()
        val user = User(UserData.getEmail())
        val userJson = gson.toJson(user)

        Log.d("json", userJson)
        // post to the server
        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
        val request = Request.Builder()
                .post(body)
                .url("http://steins.xin:8001/events")
                .build()

        //receive from server
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity!!.runOnUiThread { Toast.makeText(activity, "Failure to connect to the server", Toast.LENGTH_LONG).show() }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val ans = response.body().string()
                eventsBean = gson.fromJson(ans, EventBean::class.java)
                val imgs = ArrayList<String>()

                Objects.requireNonNull<FragmentActivity>(activity).runOnUiThread({
                    val events = eventsBean.events

                    mAdapter.data = events

                })
            }
        })
    }


}
