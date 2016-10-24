package conceptosejemplo.com.conceptosejemplo.interfaces;

import java.util.ArrayList;
import conceptosejemplo.com.conceptosejemplo.model.Food;

public interface TotalCostStrategy {
    public int totalCost(ArrayList<Food> ingredients);
}
