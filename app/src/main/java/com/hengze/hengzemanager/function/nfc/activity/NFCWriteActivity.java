package com.hengze.hengzemanager.function.nfc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.function.nfc.modle.MCReader;
import com.hengze.hengzemanager.function.nfc.util.Common;

public class NFCWriteActivity extends NFCBasicActivity {

  @Bind(R.id.editTextWriteTagSector) EditText editTextWriteTagSector;
  @Bind(R.id.editTextWriteTagBlock) EditText editTextWriteTagBlock;
  @Bind(R.id.editTextWriteTagData) EditText editTextWriteTagData;
  @Bind(R.id.keyA) CheckBox keyA;
  @Bind(R.id.keyB) CheckBox keyB;
  @Bind(R.id.buttonWriteTagBlock) Button buttonWriteTagBlock;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nfcwrite);
    ButterKnife.bind(this);

    keyA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) keyB.setChecked(false);
      }
    });

    keyB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) keyA.setChecked(false);
      }
    });

    buttonWriteTagBlock.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
       writeBlock();
      }
    });

  }

  private void writeBlock() {
    MCReader reader = Common.checkForTagAndCreateReader(this);
    if (reader == null) {
      return;
    }
    int sector = Integer.parseInt(editTextWriteTagSector.getText().toString());
    int block = Integer.parseInt(editTextWriteTagBlock.getText().toString());
    int result = -1;
    final byte[] key =
        { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

    String data = editTextWriteTagData.getText().toString();
    if(data == null || data.trim().length() != 32 ){
      ToastUtils.showToast("写入数据必须为16个字节");
    }

    final boolean useAsKeyB = keyB.isChecked();
    if (key != null) {
      result = reader.writeBlock(sector, block,
          Common.hexStringToByteArray(data),
          key, useAsKeyB);
    }
    // Error while writing? Maybe tag has default factory settings ->
    // try to write with key a (if there is one).
    if (result == -1 ) {
      //result = reader.writeBlock(sector, block,
      //    Common.hexStringToByteArray(mDataText.getText().toString()),
      //    keys[0], false);
    }
    reader.close();

    // Error handling.
    switch (result) {
      case 2:
        ToastUtils.showToast("写卡成功");
        finish();
        return;
      case -1:
        ToastUtils.showToast("写卡失败");
        return;
    }
    Toast.makeText(this, R.string.info_write_successful,
        Toast.LENGTH_LONG).show();
    finish();
  }


}
