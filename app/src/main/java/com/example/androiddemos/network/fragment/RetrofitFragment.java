package com.example.androiddemos.network.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddemos.R;
import com.example.androiddemos.databinding.FragmentRetrofitBinding;
import com.example.androiddemos.network.retrofit.GitHubService;
import com.example.androiddemos.network.retrofit.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitFragment extends Fragment {
    private static final String TAG = "RetrofitFragment";
    private FragmentRetrofitBinding binding;
    public RetrofitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<List<Repo>> reposRequest = gitHubService.listRepos("wdtht");
        reposRequest.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {

                Log.i(TAG, "onCreate: gitHubService.listRepos: response:" + response);
                assert response.body() != null;
                binding.textView.setText(response.body().toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_retrofit,container,false);
        return binding.getRoot();
    }
}