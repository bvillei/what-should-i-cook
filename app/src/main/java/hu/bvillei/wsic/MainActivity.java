package hu.bvillei.wsic;

import android.database.Cursor;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button rollButton, listButton;
    TextView resultsTextView;
    CheckBox vegetarianCheckBox;
    RadioGroup typeRadioGroup;
    List<Food> foodSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        rollButton = findViewById(R.id.rollButton);
        listButton = findViewById(R.id.listButton);
        resultsTextView = findViewById(R.id.resultsTextView);
        vegetarianCheckBox = findViewById(R.id.vegetarianCheckBox);
        typeRadioGroup = findViewById(R.id.typeRadioGroup);

        foodSet = new ArrayList<>();
        foodSet.add(new Food(1, "Pizza", true, Type.MAIN));
        foodSet.add(new Food(2, "Gyros", false, Type.MAIN));
        foodSet.add(new Food(3, "Chicken soup", false, Type.SOUP));
        foodSet.add(new Food(4, "Tiramisu", true, Type.DESSERT));
        foodSet.add(new Food(5, "Muffin", true, Type.DESSERT));

        roll();
        viewAll();
    }

    public void viewAll() {
        listButton.setOnClickListener(
                v -> {
                    Cursor res = myDb.getAllData();
                    if (res.getCount() == 0) {
                        showMessage("Error", "No data found");
                        return;
                    }
                    StringBuilder buffer = new StringBuilder();
                    while (res.moveToNext()) {
                        buffer.append("Id :").append(res.getString(0)).append("\n");
                        buffer.append("Name :").append(res.getString(1)).append("\n");
                        buffer.append("Vegetarian :").append(res.getString(2)).append("\n");
                        buffer.append("Type :").append(res.getString(3)).append("\n");
                    }
                    showMessage("Data", buffer.toString());
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
                                resultFoodList = new ArrayList<>(foodSet);
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
        return foodSet.stream().filter(food -> food.getType().equals(foodType)).collect(Collectors.toList());
    }
}
