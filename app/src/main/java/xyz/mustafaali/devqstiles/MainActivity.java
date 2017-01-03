package xyz.mustafaali.devqstiles;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        Button copyButton = (Button) findViewById(R.id.btn_copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard();
            }
        });

        TextView featureDescription = (TextView) findViewById(R.id.tv_features_description);
        featureDescription.setText(Html.fromHtml(getString(R.string.features_description), Html.FROM_HTML_MODE_COMPACT));
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.copy_label), getString(R.string.permission_command));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, R.string.msg_copied, Toast.LENGTH_SHORT).show();
    }
}
