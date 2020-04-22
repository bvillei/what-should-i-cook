package hu.bvillei.wsic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListScreen extends AppCompatActivity {

    private static final String TAG = "ListScreen";
    DatabaseHelper myDb;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen_layout);
        Log.d(TAG, "onCreate: Starting.");

        myDb = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        listAll();
    }

    public void listAll() {
        List<Food> foods = new ArrayList<>(myDb.getAllFoodFromDB());
        if (foods.isEmpty()) {
            Toast.makeText(ListScreen.this, "The Database was empty :(", Toast.LENGTH_LONG).show();
        } else {
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods.stream().map(Food::getName).collect(Collectors.toList()));
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("Name: ").append(foods.get(position).getName()).append(System.lineSeparator());
                        buffer.append("Type: ").append(foods.get(position).getType()).append(System.lineSeparator());
                        if(foods.get(position).isVegetarian()) {buffer.append("Vegetarian").append(System.lineSeparator());}
                        showMessage(foods.get(position).getName(), buffer.toString());
                    }
                });

        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
