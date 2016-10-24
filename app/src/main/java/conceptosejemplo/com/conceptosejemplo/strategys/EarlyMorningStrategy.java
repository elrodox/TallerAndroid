package conceptosejemplo.com.conceptosejemplo.strategys;

import java.util.ArrayList;

import conceptosejemplo.com.conceptosejemplo.interfaces.TotalCostStrategy;
import conceptosejemplo.com.conceptosejemplo.model.Food;

public class EarlyMorningStrategy implements TotalCostStrategy {
    @Override
    public int totalCost(ArrayList<Food> ingredients) {
        int totalCost = 0;
        for (int i=0; i<ingredients.size();i++){
            totalCost = ingredients.get(i).getCost()*2 + totalCost;
        }
        return totalCost+1000;
    }
}
