package hu.bvillei.wsic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddScreen extends AppCompatActivity {

    private static final String TAG = "AddScreen";
    DatabaseHelper myDb;
    EditText nameInput;
    Button submitButton;
    CheckBox vegetarianInput;
    Spinner typeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen_layout);
        Log.d(TAG, "onCreate: Starting.");

        myDb = new DatabaseHelper(this);

        nameInput = findViewById(R.id.nameInput);
        submitButton = findViewById(R.id.submitButton);
        vegetarianInput = findViewById(R.id.vegetarianInput);
        typeInput = findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeInput.setAdapter(adapter);

        addFood();
    }

    public void addFood() {
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(nameInput.getText().toString(),
                                (vegetarianInput.isChecked()) ? 1 : 0,
                                typeInput.getSelectedItem().toString());
                        if (isInserted) {
                            Intent intent = new Intent(AddScreen.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(AddScreen.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddScreen.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
