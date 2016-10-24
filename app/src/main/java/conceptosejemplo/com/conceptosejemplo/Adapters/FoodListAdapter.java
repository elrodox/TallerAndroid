package conceptosejemplo.com.conceptosejemplo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import conceptosejemplo.com.conceptosejemplo.R;
import conceptosejemplo.com.conceptosejemplo.model.Food;

public class FoodListAdapter extends ArrayAdapter<Food> implements ListAdapter {

    private Context context;
    private ArrayList<Food> foods;

    public FoodListAdapter(Context context, ArrayList<Food> foods) {
        super(context, 0, foods);
        this.context = context;
        this.foods = foods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_products_list, parent, false);
        Food food = getItem(position);
        TextView name = (TextView) row.findViewById(R.id.combo_text);
        name.setText(food.getName()+" = $"+food.getCost());
        return row;
    }

}
