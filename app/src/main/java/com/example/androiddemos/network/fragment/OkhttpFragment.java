package com.example.androiddemos.network.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androiddemos.R;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OkhttpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OkhttpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView responseText;
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
        if (view != null) {
            Button okhttpBtn = view.findViewById(R.id.okhttp_btn);
            responseText = view.findViewById(R.id.responseText);
            okhttpBtn.setOnClickListener(v -> {
                Log.d(Tag,"okhttpBtn clink");
                sendRequestOkhttp();
            });
        }
        return view;
    }

    private void sendRequestOkhttp() {
        new Thread(() -> {
            try {
                Log.d(Tag,"#sendRequestOkhttp");
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                Response response = client.newCall(request).execute();
                Log.d(Tag,"#sendRequestOkhttp responseData:" +response.body());
                assert response.body() != null;
                String responseData = response.body().string();

                showResponse(responseData);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showResponse(String response) {
        Log.d(Tag,"#showResponse response:"+response);
        requireActivity().runOnUiThread(() -> {
            Log.d(Tag,"#showResponse response:"+response);
            responseText.setText(response);
        });
    }
}