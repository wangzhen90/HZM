

package com.hengze.hengzemanager.function.nfc.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.hengze.hengzemanager.function.nfc.util.Common;

public abstract class NFCBasicActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Common.setNfcAdapter(NfcAdapter.getDefaultAdapter(this));
  }
  /**
   * Enable NFC foreground dispatch system.
   */
  @Override public void onResume() {
    super.onResume();
    Common.enableNfcForegroundDispatch(this);
  }

  /**
   * Disable NFC foreground dispatch system.
   */
  @Override public void onPause() {
    super.onPause();
    Common.disableNfcForegroundDispatch(this);
  }

  /**
   * Handle new Intent as a new tag Intent and if the tag/device does not
   * support Mifare Classic
   *
   * @see Common#treatAsNewTag(Intent, android.content.Context)
   */
  @Override public void onNewIntent(Intent intent) {
    int typeCheck = Common.treatAsNewTag(intent, this);
    if (typeCheck == -1 || typeCheck == -2) {
      // Device or tag does not support Mifare Classic.
      // Run the only thing that is possible: The tag info tool.
      //Intent i = new Intent(this, TagInfoTool.class);
      //startActivity(i);
      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
      dialog.setTitle("提醒").setMessage("该手机不支持此种卡片").setNegativeButton("确定",
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              finish();
            }
          });
      dialog.show();
      return;
    }
    //readCard();
  }

  public void readCard(){

  }


}
