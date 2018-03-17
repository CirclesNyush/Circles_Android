package com.example.anpu.circles.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.anpu.circles.R;
import com.example.anpu.circles.utilities.GlideV4ImageEngine;
import com.example.anpu.circles.utilities.PermissonHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.anpu.circles.utilities.PermissonHelper.askStroage;

/**
 * Created by anpu on 2018/3/5.
 */

public class GroupFragment extends Fragment {

    List<Uri> mSelected;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_group, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button selc = (Button) getActivity().findViewById(R.id.test_selc);
        askStroage(getActivity());
        selc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(getActivity())
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(9)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.picker))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideV4ImageEngine())
                        .forResult(1111);
                Toast.makeText(getActivity(), "tttttttt", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }


}
