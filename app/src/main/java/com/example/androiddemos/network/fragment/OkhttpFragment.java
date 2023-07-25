package com.example.androiddemos.network.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddemos.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OkhttpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OkhttpFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView responseText;
    private  Button okhttpBtnGet;
    private  Button okhttpBtnPost;

    private final String Tag = "superdemo/OkhttpFragment";

    public OkhttpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OkhttpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OkhttpFragment newInstance(String param1, String param2) {
        OkhttpFragment fragment = new OkhttpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_okhttp, container, false);
        if(view!=null){
            okhttpBtnGet = view.findViewById(R.id.okhttp_btn_get);
            okhttpBtnPost = view.findViewById(R.id.okhttp_btn_post);
            responseText = view.findViewById(R.id.response_text);
            okhttpBtnPost.setOnClickListener(this);
            okhttpBtnGet.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.okhttp_btn_get){
            sendGetRequestOkhttp();
        }else if(v.getId() == R.id.okhttp_btn_post){
            sendPostRequestOkhttp();
        }
    }

    private void sendGetRequestOkhttp() {
        new Thread(() -> {
            try {
                Log.d(Tag,"#sendRequestOkhttp");
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
                client.newBuilder().readTimeout(10,TimeUnit.SECONDS);
                client.newBuilder().writeTimeout(10,TimeUnit.SECONDS);
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                //Response response = client.newCall(request).execute();//同步方法
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(Tag,"#onFailure");
                        Looper.prepare();
                        Toast.makeText(getActivity(),"request fail!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d(Tag,"#onResponse");
                        assert response.body() != null;
                        //Log.d(Tag,"#sendRequestOkhttp responseData:" +response.body().string());//切记不能打印出来，string()只能调用一次，不然会崩
                        //可参考：https://blog.csdn.net/weixin_38629529/article/details/89789405
                        String responseData = response.body().string();
                        Log.d(Tag,"#onResponse responseData:"+responseData);
//                        Looper.prepare();
//                        Toast.makeText(getActivity(),"request success!", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
                        showResponse(responseData);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showResponse(final String response) {
        Log.d(Tag,"#showResponse response:"+response);
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(),"request success!", Toast.LENGTH_SHORT).show();
            responseText.setText(response);
        });
    }

    private void sendPostRequestOkhttp(){

    }

}