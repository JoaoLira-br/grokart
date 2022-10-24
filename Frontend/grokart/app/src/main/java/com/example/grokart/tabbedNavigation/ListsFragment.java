package com.example.grokart.tabbedNavigation;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grokart.R;
import com.example.grokart.databinding.FragmentListsBinding;

public class ListsFragment extends Fragment {
    private FragmentListsBinding binding;

    private TextView tv_welcomeUser, tv_appName;
    private Button btn_createNewList, btn_viewListHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListsBinding.inflate(inflater, container, false);

        tv_welcomeUser = binding.tvMainWelcome;
        tv_appName = binding.tvMainAppTitle;
        btn_createNewList = binding.btnMainCreateNewList;
        btn_viewListHistory = binding.btnMainViewListHistory;
        //setting the style for the App Title
        Spannable groKart = new SpannableString(getString(R.string.groKart));
        groKart.setSpan(new ForegroundColorSpan(Color.GREEN), 0,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        groKart.setSpan(new ForegroundColorSpan(Color.RED),3,groKart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_appName.setText(groKart);
        //TODO get username for the fragment
        String userName = "user";
        tv_welcomeUser.append(" " + userName + "!");

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //button listeners here
        btn_viewListHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
