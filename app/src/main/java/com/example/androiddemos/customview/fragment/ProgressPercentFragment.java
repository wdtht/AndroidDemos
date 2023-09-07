package com.example.androiddemos.customview.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androiddemos.R;
import com.example.androiddemos.view.Progress;
import com.example.androiddemos.view.PasswordEditDialog;
import com.example.androiddemos.view.RoundProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressPercentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressPercentFragment extends Fragment {
    private final String TAG = "superdemo/ProgressPercentFragment";
    private Progress bar;
    private RoundProgressBar roundBar;
    private Button btn;
    private Button passwordEditDialog;
    private int progress;
    private Timer timer;

    public ProgressPercentFragment() {
        // Required empty public constructor
    }
    public static ProgressPercentFragment newInstance(String param1, String param2) {
        ProgressPercentFragment fragment = new ProgressPercentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_percent, container, false);
        if(view!=null){
            bar = view.findViewById(R.id.bar);
            roundBar = view.findViewById(R.id.id_round_progressbar);
            btn = view.findViewById(R.id.btn);
            passwordEditDialog =view.findViewById(R.id.btn_password_dialog);
        }
        btn.setOnClickListener(view1 -> reset());
        passwordEditDialog.setOnClickListener(v -> {
              PasswordEditDialog dialog = new PasswordEditDialog(getContext(),"123456");
              dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
              dialog.passwordComfireRightCallback(isRight-> {
                  Log.d(TAG,"isRight:"+isRight);
                    if(isRight){
                        dialog.dismiss();
                        Toast.makeText(getContext(),"密码正确",Toast.LENGTH_SHORT).show();
                    }
              });
              dialog.show();
        });
        return view;
    }
    private void reset() {
        progress = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bar.setProgress(progress);
                roundBar.setProgress(progress);
                progress++;
                if (progress > 100) {
                    timer.cancel();
                }
            }
        }, 0, 30);
    }
}