package com.hengze.hengzemanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import com.hengze.hengzemanager.ui.adapter.CardDetailAdapter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardDetailFragment extends Fragment {
  RecyclerView recyclerView;
  CardDetailAdapter adapter;
  ArrayList<CardConsumeInfo> infos;

  public CardDetailFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    infos = (ArrayList<CardConsumeInfo>) getArguments().getSerializable(Constant.CARD_CONSUME_INFO);

    return inflater.inflate(R.layout.fragment_card_detail, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (RecyclerView) view.findViewById(R.id.card_detail_recyclerview);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new CardDetailAdapter(infos);
    recyclerView.setAdapter(adapter);
  }
}
