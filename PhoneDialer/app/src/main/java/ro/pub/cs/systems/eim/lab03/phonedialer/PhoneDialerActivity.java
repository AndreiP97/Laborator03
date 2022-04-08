package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton callButton;
    private ImageButton hangupButton;
    private ImageButton backspaceButton;
    private Button genericButton;

    private CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private class CallButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    };

    private HangupButtonClickListener hangupButtonClickListener = new HangupButtonClickListener();
    private class HangupButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString()
                    + ((Button) view).getText().toString());
        }
    };

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();

            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_phone_dialer);

        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);

        backspaceButton = (ImageButton) findViewById(R.id.backspace_button);
        backspaceButton.setOnClickListener(backspaceButtonClickListener);

        callButton = (ImageButton) findViewById(R.id.call_button);
        callButton.setOnClickListener(callButtonClickListener);

        hangupButton = (ImageButton) findViewById(R.id.hangup_button);
        hangupButton.setOnClickListener(hangupButtonClickListener);

        for (int i = 0; i < Constants.buttonIds.length; i++) {
            genericButton = (Button) findViewById(Constants.buttonIds[i]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }
    }
}