package com.example.androiddemos.serialport;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.androiddemos.BaseActivity;
import android.Manifest;
import com.example.androiddemos.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import tp.xmaihh.serialport.SerialHelper;
import tp.xmaihh.serialport.bean.ComBean;
import tp.xmaihh.serialport.stick.AbsStickPackageHelper;
import tp.xmaihh.serialport.utils.ByteUtil;

public class SerialportActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener , View.OnClickListener{


    RadioGroup mRgType;
    EditText mEtReadContent;
    EditText mEtSendContent;

    private SerialHelper serialHelper;
    private boolean isHexType = false;
    private String text = "";

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ComBean comBean = (ComBean) msg.obj;
            String time = comBean.sRecTime;
            String rxText;
            rxText = new String(comBean.bRec);
            if (isHexType) {
                //转成十六进制数据
                rxText = ByteUtil.ByteArrToHex(comBean.bRec);
            }
            text += "Rx-> " + time + ": " + rxText + "\r" + "\n";
            mEtReadContent.setText(text);
            return false;
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialport);
        mRgType = findViewById(R.id.rg_type);
        mEtReadContent = findViewById(R.id.et_read_content);
        mEtSendContent = findViewById(R.id.et_send_content);
        findViewById(R.id.bt_open).setOnClickListener(this);
        findViewById(R.id.bt_close).setOnClickListener(this);
        findViewById(R.id.bt_send).setOnClickListener(this);
        mRgType.setOnCheckedChangeListener(this);
        initSerialConfig();
    }

    private void initSerialConfig() {
        //初始化SerialHelper对象，设定串口名称和波特率（此处为接收扫码数据）
        serialHelper = new SerialHelper("/dev/ttyS6", 9600) {
            @Override
            protected void onDataReceived(ComBean paramComBean) {
                Message message = mHandler.obtainMessage();
                message.obj = paramComBean;
                Log.e("TAG", "onDataReceived: " + new Gson().toJson(message.obj));
                mHandler.sendMessage(message);
            }
        };

        /*
         * 默认的BaseStickPackageHelper将接收的数据扩展成64位，一般用不到这么多位
         * 我这里重新设定一个自适应数据位数的
         */

        serialHelper.setStickPackageHelper(new AbsStickPackageHelper() {
            @Override
            public byte[] execute(InputStream is) {
                try {
                    int available = is.available();
                    if (available > 0) {
                        byte[] buffer = new byte[available];
                        int size = is.read(buffer);
                        if (size > 0) {
                            return buffer;
                        }
                    } else {
                        SystemClock.sleep(50);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    public void onButtonClicked(View view){
        if(view.getId() == R.id.bt_open){
            if (serialHelper.isOpen()) {
                Toast.makeText(this,  "串口已经打开", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                serialHelper.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this,  "串口打开成功", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.bt_close) {
            if (serialHelper.isOpen()) {
                serialHelper.close();
                Toast.makeText(this,  "串口已经关闭", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.bt_clear_content) {
            text = "";
            mEtReadContent.setText(text);

        } else if (view.getId() == R.id.bt_send) {
            if (!serialHelper.isOpen()) {
                Toast.makeText(this,  "串口没打开 发送失败", Toast.LENGTH_SHORT).show();
                return;
            }
            String sendContent = mEtSendContent.getText().toString();
            if (isHexType) {
                serialHelper.sendHex(sendContent);
            } else {
                serialHelper.sendTxt(sendContent);
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rb_hex) {
            isHexType = true;
            mEtSendContent.setText("发送hex类型数据");
        } else if (checkedId == R.id.rb_txt) {
            isHexType = false;
            mEtSendContent.setText("发送txt类型数据");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serialHelper.close();
        serialHelper = null;
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, SerialportActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View v) {
        onButtonClicked(v);
    }
}