package com.example.easyenglish.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyenglish.Activity.ExamAcvitity.Cet4Activity;
import com.example.easyenglish.Activity.ExamAcvitity.Cet6Activity;
import com.example.easyenglish.Activity.ExamAcvitity.IeltsActivity;
import com.example.easyenglish.Activity.ExamAcvitity.ToeflActivity;
import com.example.easyenglish.R;

public class DashboardFragment extends Fragment {

    private Button exam_cet4_btn;
    private Button exam_cet6_btn;
    private Button exam_ielts_btn;
    private Button exam_toefl_btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
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
                startActivity(new Intent(getActivity(), Cet4Activity.class));
            }
        });

        exam_cet6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Cet6Activity.class));
            }
        });

        exam_ielts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), IeltsActivity.class));
            }
        });

        exam_toefl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ToeflActivity.class));
            }
        });

    }
}
