package com.hengze.hengzemanager.ui.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengze.hengzemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectionFragment extends Fragment {

  public DetectionFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_account, container, false);
  }
}
