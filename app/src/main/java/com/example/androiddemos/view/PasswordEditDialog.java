package com.example.androiddemos.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androiddemos.R;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/9/7
 */
public class PasswordEditDialog extends Dialog {
    private final String TAG = "superdemo/PasswordEditDialog";
    private Context mContext;
    private String password;
    private Button btnOk;
    private Button btnCancel;
    private EditText edit;
    private TextView errorTips;
    private PasswordComfireRight mOnPasswordIsRightCallback;
    public interface PasswordComfireRight {
        void onPasswordIsRight(Boolean isRight);
    }

    public PasswordEditDialog(@NonNull Context context ,String password) {
        super(context);
        this.mContext = context;
        this.password = password;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = 1000;
        params.height = 700;
        this.getWindow().setAttributes(params);
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        initView();
    }
    private void initView() {
        btnOk = (Button) findViewById(R.id.btn_Ok);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);
        edit = findViewById(R.id.edit_text);
        errorTips = findViewById(R.id.error_tips);
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        btnOk.setOnClickListener(v -> {
            if (edit.getText().toString().equals(password)) {
                Log.d(TAG, "initView: 密码正确");
                if(mOnPasswordIsRightCallback!=null){
                    mOnPasswordIsRightCallback.onPasswordIsRight(true);
                }
                dismiss();
            } else {
                Log.d(TAG, "initView: 密码错误");
                //弹出密码错误提示，并清空输入框
                errorTips.setVisibility(View.VISIBLE);
                edit.setText("");
                if(mOnPasswordIsRightCallback!=null){
                    mOnPasswordIsRightCallback.onPasswordIsRight(false);
                }
            }
        });
        edit.setOnClickListener(v -> {
            //点击输入框，隐藏密码错误提示
            errorTips.setVisibility(View.GONE);
        });
    }
    public void passwordComfireRightCallback(PasswordComfireRight passwordIsRightCallback){
        mOnPasswordIsRightCallback = passwordIsRightCallback;
    }


}
