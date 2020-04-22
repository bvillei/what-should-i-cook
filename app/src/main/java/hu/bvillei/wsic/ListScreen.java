package hu.bvillei.wsic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListScreen extends AppCompatActivity {

    private static final String TAG = "ListScreen";
    DatabaseHelper myDb;
    ListView listView;
    ListAdapter listAdapter;

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
                listAdapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1,
                        foods.stream().map(Food::getName).collect(Collectors.toList()));
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showFoodDetails(foods.get(position));
                    }
                });
        }
    }

    public void showFoodDetails(Food food) {
        StringBuilder content = new StringBuilder();
        content.append("Name: ").append(food.getName()).append(System.lineSeparator());
        content.append("Type: ").append(food.getType()).append(System.lineSeparator());
        if(food.isVegetarian()) {content.append("Vegetarian").append(System.lineSeparator());}

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(food.getName());
        builder.setMessage(content);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.deleteData(food);
                listAll();
            }
        });
        builder.show();
    }
}
