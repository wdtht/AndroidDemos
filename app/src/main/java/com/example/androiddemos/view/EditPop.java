package com.example.androiddemos.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.androiddemos.R;

/**
 * 作用：自定义弹窗（未写完）
 *
 * @author chenkexi
 * @date :2023/7/29
 */
public class EditPop extends Dialog {

    private Button btnOk;
    private final String TAG = "superdemo/EditPop";
    private Button btnCancel;
    private EditText edit;
    private String mContent;
    private Context mContext;

    private OnLeftOrRightButtonClick onLeftOrRightButtonClick;


    /**
     * 注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
     */

    public EditPop(@NonNull Context context, OnLeftOrRightButtonClick onLeftOrRightButtonClick) {
        super(context);
        this.mContext = context;
        this.onLeftOrRightButtonClick = onLeftOrRightButtonClick;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = 1000;
        params.height = 700;
        this.getWindow().setAttributes(params);
    }

    public EditPop(@NonNull Context context,
                   String content, OnLeftOrRightButtonClick onLeftOrRightButtonClick) {
        super(context);
        this.mContext = context;
        this.mContent = content;
        this.onLeftOrRightButtonClick = onLeftOrRightButtonClick;
    }


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = View.inflate(getContext(),R.layout.pop_view,null);
        setContentView(R.layout.pop_view);
        btnOk = (Button) findViewById(R.id.btn_Ok);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);
        edit = findViewById(R.id.edit_text);
        btnOk.setOnClickListener(v -> {
            this.onLeftOrRightButtonClick.onRightClick("设置成功！");
            Log.d(TAG,"edit text:"+edit.getText().toString());
            this.onLeftOrRightButtonClick.onEditText(edit.getText().toString());
            dismiss();
        });
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
    }


}
