package com.example.androiddemos.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.androiddemos.R;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/29
 */
public class EditPop extends Dialog {

    private Button btnOk;
    private Button btnCancel;
    private View.OnClickListener listener =null;
    public EditPop(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_view);
        btnOk = (Button) findViewById(R.id.btn_Ok);
        btnCancel = (Button)findViewById(R.id.btn_Cancel);
        btnOk.setOnClickListener(listener);
        btnCancel.setOnClickListener (listener);
    }

    private void setListener( View.OnClickListener clickListener) {
        listener = clickListener;
    }
}
