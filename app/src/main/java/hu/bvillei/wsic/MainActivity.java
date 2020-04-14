package hu.bvillei.wsic;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    Button rollButton, showButton;
    TextView resultsTextView;
    CheckBox vegetarianCheckBox;
    RadioGroup typeRadioGroup;
    List<Food> foodSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = findViewById(R.id.rollButton);
        showButton = findViewById(R.id.showButton);
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
