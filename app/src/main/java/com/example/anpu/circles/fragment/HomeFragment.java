package com.example.anpu.circles.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.anpu.circles.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anpu on 2018/3/5.
 */

public class HomeFragment extends Fragment {

//    @BindView(R.id.get_token) Button getToken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button getToken = (Button) getActivity().findViewById(R.id.get_token);
        getToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d("TOKEN: ", token);
                System.out.println("TOKEN: " + token);
                Toast.makeText(getActivity(), "tttttttt", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
