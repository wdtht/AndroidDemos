package com.example.androiddemos.customview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.androiddemos.R;
import com.example.androiddemos.view.Progress;
import com.example.androiddemos.view.RoundProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressPercentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressPercentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Progress bar;
    private RoundProgressBar roundBar;
    private Button btn;
    private int progress;
    private Timer timer;

    public ProgressPercentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressPercentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressPercentFragment newInstance(String param1, String param2) {
        ProgressPercentFragment fragment = new ProgressPercentFragment();
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
        View view = inflater.inflate(R.layout.fragment_progress_percent, container, false);
        // Inflate the layout for this fragment
        if(view!=null){
            bar = view.findViewById(R.id.bar);
            roundBar = view.findViewById(R.id.id_round_progressbar);
            btn = view.findViewById(R.id.btn);
        }
        btn.setOnClickListener(view1 -> reset());
        return view;
    }

    private void navigateTo(){
//        ProgressPercentFragment progressPercentFragment;
//        progressPercentFragment = new ProgressPercentFragment();
//        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_main,progressPercentFragment);
//        fragmentTransaction.commit();
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