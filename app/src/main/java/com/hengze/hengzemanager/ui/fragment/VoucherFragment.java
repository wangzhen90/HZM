package com.hengze.hengzemanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.function.nfc.activity.NFCReadActivity;
import com.hengze.hengzemanager.function.nfc.activity.NFCWriteActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoucherFragment extends Fragment {

  @Bind(R.id.read_card) Button readCard;
  @Bind(R.id.wirte_card) Button writeCard;

  public VoucherFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_voucher, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    readCard.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NFCReadActivity.class);
        startActivity(intent);
      }
    });

    writeCard.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NFCWriteActivity.class);
        startActivity(intent);
      }
    });

  }



  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
