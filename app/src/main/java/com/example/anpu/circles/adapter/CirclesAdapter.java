package com.example.anpu.circles.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.anpu.circles.R;
import com.example.anpu.circles.model.CircleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.anpu.circles.fragment.HomeFragment.options;

public class CirclesAdapter extends BaseQuickAdapter<CircleBean, BaseViewHolder> {

    private String baseUrl = "http://steins.xin:8001";
    private int eventId;

    public CirclesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleBean item) {
        helper.setText(R.id.circle_title, item.getTitle());
        helper.setText(R.id.circle_content, item.getContent());
        Glide.with(mContext).load(baseUrl + item.getAvatar())
                .apply(options)
                .into((ImageView) helper.getView(R.id.circle_avatar));

        GridView gridView = helper.getView(R.id.gridView);
        gridView.setAdapter(new GridPreViewAdapter(mContext, item.getImgs()));
        Log.d("imgs", String.valueOf(item.getImgs().size() ));
        if (item.getImgs().size() == 0) {
            Log.d("img", "000");
            gridView.setVisibility(GridView.GONE);
            gridView.setEnabled(false);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = 0;
            layoutParams.width = 0;
            gridView.setBackgroundColor(255);
            gridView.setLayoutParams(layoutParams);
        }
        helper.addOnClickListener(R.id.circle_avatar);
        helper.addOnClickListener(R.id.circle_title);


        eventId = item.getEventId();
    }


    @Nullable
    @Override
    public CircleBean getItem(int position) {
        return super.getItem(position);
    }
}
