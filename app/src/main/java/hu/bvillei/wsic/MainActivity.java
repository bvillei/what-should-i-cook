package hu.bvillei.wsic;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper myDb;
    Button rollButton, listButton, addButton;
    TextView resultsTextView;
    CheckBox vegetarianCheckBox;
    RadioGroup typeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        myDb = new DatabaseHelper(this);

        rollButton = findViewById(R.id.rollButton);
        listButton = findViewById(R.id.listButton);
        addButton = findViewById(R.id.addButton);
        resultsTextView = findViewById(R.id.resultsTextView);
        vegetarianCheckBox = findViewById(R.id.vegetarianCheckBox);
        typeRadioGroup = findViewById(R.id.typeRadioGroup);

        roll();
        listAll();
        navigateToAdd();
    }

    public List<Food> getAllFoodFromDB() {
        List<Food> list = new ArrayList<>();
        Cursor res = myDb.getAllData();
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            boolean vegetarian = (res.getInt(2) == 1);
            Type type = Type.valueOf(res.getString(3));

            list.add(new Food(id, name, vegetarian, type));
        }
        return list;
    }

    public void listAll() {
        listButton.setOnClickListener(
                v -> {
                    List<Food> foods = new ArrayList<>(getAllFoodFromDB());
                    if (foods.isEmpty()) {
                        showMessage("Error", "No data found");
                        return;
                    }
                    StringBuilder buffer = new StringBuilder();
                    for (Food food : foods) {
                        buffer.append("Name: ").append(food.getName()).append(System.lineSeparator());
                        buffer.append("Type: ").append(food.getType()).append(System.lineSeparator());
                        if (food.isVegetarian()) {buffer.append("Vegetarian").append(System.lineSeparator());}
                        buffer.append("\n");
                    }
                    showMessage("Cook one of this:", buffer.toString());
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void roll() {
        rollButton.setOnClickListener(
                v -> {
                    List<Food> resultFoodList;
                    try {
                        switch (typeRadioGroup.getCheckedRadioButtonId()) {
                            case (R.id.soupRadioButton):
                                resultFoodList = filterListByType(Type.SOUP);
                                break;
                            case (R.id.mainRadioButton):
                                resultFoodList = filterListByType(Type.MAIN);
                                break;
                            case (R.id.dessertRadioButton):
                                resultFoodList = filterListByType(Type.DESSERT);
                                break;
                            default:
                                resultFoodList = new ArrayList<>(getAllFoodFromDB());
                        }
                        if (vegetarianCheckBox.isChecked()) {
                            resultFoodList.removeIf(food -> !(food.isVegetarian()));
                        }
                        resultsTextView.setText(resultFoodList.get(new Random().nextInt(resultFoodList.size())).getName());
                    } catch (IllegalArgumentException e) {
                        resultsTextView.setText("No Result");
                    }
                }
        );
    }

    private List<Food> filterListByType(Type foodType) {
        return getAllFoodFromDB().stream().filter(food -> food.getType().equals(foodType)).collect(Collectors.toList());
    }

    private void navigateToAdd() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked addButton.");

                Intent intent = new Intent(MainActivity.this, AddScreen.class);
                startActivity(intent);
            }
        });
    }

}
