package xyz.mustafaali.devqstiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share_app) {
            shareApp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void initUi() {
        Button copyButton = (Button) findViewById(R.id.btn_copy);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePermissionsCommand();
            }
        });

        TextView featureDescription = (TextView) findViewById(R.id.tv_features_description);
        featureDescription.setText(Html.fromHtml(getString(R.string.features_description), Html.FROM_HTML_MODE_COMPACT));
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_share_app));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void sharePermissionsCommand() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.permission_command));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
