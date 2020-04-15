package hu.bvillei.wsic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddScreen extends AppCompatActivity {

    private static final String TAG = "AddScreen";
    Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen_layout);
        Log.d(TAG, "onCreate: Starting.");

        backButton = findViewById(R.id.backButton);

        navigateBack();
    }

    public void navigateBack() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked backButton.");

                Intent intent = new Intent(AddScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
