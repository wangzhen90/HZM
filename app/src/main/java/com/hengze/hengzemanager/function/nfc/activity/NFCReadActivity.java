package com.hengze.hengzemanager.function.nfc.activity;

import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.function.nfc.modle.MCReader;
import com.hengze.hengzemanager.function.nfc.util.Common;

public class NFCReadActivity extends NFCBasicActivity {
  private final Handler mHandler = new Handler();
  @Bind(R.id.result) TextView text_result;
  @Bind(R.id.editTextReadTagSector) EditText editTextReadTagSector;

  @Bind(R.id.editTextReadTagBlock) EditText editTextReadTagBlock;
  @Bind(R.id.buttonWriteTagBlock) Button buttonRead;

  @Bind(R.id.keyA) CheckBox keyA;
  @Bind(R.id.keyB) CheckBox keyB;
  String result;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nfcread);
    ButterKnife.bind(this);
    //onNewIntent(getIntent());

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

    buttonRead.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        readCard();
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
  }

  public void readCard() {
    final MCReader reader = Common.checkForTagAndCreateReader(this);

    final int sectorIndex = Integer.valueOf(editTextReadTagSector.getText().toString());
    final int blockIndex = Integer.valueOf(editTextReadTagBlock.getText().toString());
    final byte[] key =
        { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

    final boolean useAsKeyB = keyB.isChecked();
    if (reader == null) {
      return;
    }

    new Thread(new Runnable() {
      @Override public void run() {
        // Get key map from glob. variable.
        //mRawDump = reader.readAsMuchAsPossible(Common.getKeyMap());

        try {
          //result = reader.readSector(0, MifareClassic.KEY_DEFAULT, true);
          result = reader.readBlock(sectorIndex, blockIndex, key, useAsKeyB);
        } catch (TagLostException e) {
          e.printStackTrace();
        }
        reader.close();

        mHandler.post(new Runnable() {
          @Override public void run() {

            text_result.setText(result);
          }
        });
      }
    }).start();
  }
}
