package com.example.anpu.circles.utilities;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.anpu.circles.R;

public class CancelOrOkDialog extends Dialog {

    public CancelOrOkDialog(Context context, String title) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.dialog_cancel_or_ok);
        setCancelable(false);

        // set the title
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok();
            }
        });
    }

    public void ok() {

    }
}
