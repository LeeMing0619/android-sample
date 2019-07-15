package com.app.flex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.flex.R;


public class HelpCenterFragment extends Fragment {

    LinearLayout mInstantChatLi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_help_center, container, false);

        mInstantChatLi = rootview.findViewById(R.id.instant_chat_li);
        mInstantChatLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpCenterChatFragment someFragment = new HelpCenterChatFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootview;
    }

}
