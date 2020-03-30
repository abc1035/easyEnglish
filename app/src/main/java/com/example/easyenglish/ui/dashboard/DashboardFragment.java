package com.example.easyenglish.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easyenglish.ExamAcvitity.ExamCet4Activity;
import com.example.easyenglish.ExamAcvitity.ExamCet6Activity;
import com.example.easyenglish.ExamAcvitity.ExamIeltsActivity;
import com.example.easyenglish.ExamAcvitity.ExamToeflActivity;
import com.example.easyenglish.HomeActivity;
import com.example.easyenglish.LoginActivity;
import com.example.easyenglish.R;
import com.example.easyenglish.RegisterActivity;

public class DashboardFragment extends Fragment {

   //private DashboardViewModel dashboardViewModel;
    private Button exam_cet4_btn;
    private Button exam_cet6_btn;
    private Button exam_ielts_btn;
    private Button exam_toefl_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //dashboardViewModel =
              //  ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        exam_cet4_btn=getActivity().findViewById(R.id.exam_cet4_btn);
        exam_cet6_btn=getActivity().findViewById(R.id.exam_cet6_btn);
        exam_ielts_btn=getActivity().findViewById(R.id.exam_ielts_btn);
        exam_toefl_btn=getActivity().findViewById(R.id.exam_toefl_btn);

        exam_cet4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExamCet4Activity.class));
            }
        });

        exam_cet6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExamCet6Activity.class));
            }
        });

        exam_ielts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExamIeltsActivity.class));
            }
        });

        exam_toefl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExamToeflActivity.class));
            }
        });

    }
}
