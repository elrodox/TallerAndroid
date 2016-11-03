package conceptosejemplo.com.conceptosejemplo.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import conceptosejemplo.com.conceptosejemplo.dao.AssetDatabaseOpenHelper;
import conceptosejemplo.com.conceptosejemplo.dao.DataAccessObject;
import conceptosejemplo.com.conceptosejemplo.facade.DailyInfoFacade;
import conceptosejemplo.com.conceptosejemplo.managers.GalleryManager;
import conceptosejemplo.com.conceptosejemplo.managers.TimerManager;
import conceptosejemplo.com.conceptosejemplo.R;
import conceptosejemplo.com.conceptosejemplo.model.Products;
import conceptosejemplo.com.conceptosejemplo.singletons.RandomComboGenerator;
import conceptosejemplo.com.conceptosejemplo.model.Dessert;
import conceptosejemplo.com.conceptosejemplo.model.Drink;
import conceptosejemplo.com.conceptosejemplo.model.Food;
import conceptosejemplo.com.conceptosejemplo.model.Meat;
import conceptosejemplo.com.conceptosejemplo.model.Bread;
import conceptosejemplo.com.conceptosejemplo.model.Menu;
import conceptosejemplo.com.conceptosejemplo.model.Vegetal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImageChooserListener {

    private Button botonPrueba;
    private Button menuDetails;
    private Button productsDetail;
    private Button galleryButton;
    private Button loadLocalDataButton;
    private Button pizzaButton;
    private Button connectionButton;

    private RelativeLayout menuBackGround;
    private Menu myMenu;

    private ArrayList<Vegetal> vegetalsList = new ArrayList<>();
    private ArrayList<Meat> meatsList = new ArrayList<>();
    private ArrayList<Bread> breadsList = new ArrayList<>();
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<Dessert> desserts = new ArrayList<>();
    private ArrayList<Food> products = new ArrayList<>();

    IntentFilter s_intentFilter = new IntentFilter();
    private int connectionCounter = 0;
    private Button dailyInfoButton;
    private List<Products> productsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        botonPrueba = (Button)findViewById(R.id.random_combos_button);
        botonPrueba.setOnClickListener(this);

        menuDetails = (Button)findViewById(R.id.menu_details);
        menuDetails.setOnClickListener(this);

        productsDetail = (Button)findViewById(R.id.products_detail);
        productsDetail.setOnClickListener(this);

        galleryButton = (Button)findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(this);

        loadLocalDataButton = (Button)findViewById(R.id.load_localdata_button);
        loadLocalDataButton.setOnClickListener(this);

        pizzaButton = (Button)findViewById(R.id.pizza_button);
        pizzaButton.setOnClickListener(this);

        connectionButton = (Button)findViewById(R.id.connection_button);
        connectionButton.setOnClickListener(this);

        dailyInfoButton = (Button)findViewById(R.id.daily_info_button);
        dailyInfoButton.setOnClickListener(this);

        menuBackGround = (RelativeLayout)findViewById(R.id.menu_background);

        defaultsObjects();

        defaultMenu();

        String packageName = getApplicationContext().getPackageName()+".ACTION";

        (TimerManager.getInstance()).launchClock(getApplicationContext(), packageName, 1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            try {
                if (data.getData() != null) {
                    Uri uriPicture = data.getData();
                    menuBackGround.setBackground(uriPictureToBitmap(uriPicture));
                }
            } catch (NullPointerException nPE) {
                nPE.getMessage();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.random_combos_button:
                RandomComboGenerator.getInstance().fillMenu(vegetalsList, meatsList, breadsList, drinks, desserts, myMenu);
                Toast.makeText(getBaseContext(),"Combos generados",Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_details:
                Intent menusList = new Intent(MainActivity.this, MenuList.class);
                menusList.putExtra("obj", myMenu);
                startActivity(menusList);
                break;

            case R.id.products_detail:
                Intent productsListIntent = new Intent(MainActivity.this, ProductsList.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("products", products);
                productsListIntent.putExtras(bundleObject);
                startActivity(productsListIntent);
                break;

            case R.id.gallery_button:
                GalleryManager.getInstance(this, this).selectImageFromGallery();
                break;

            case R.id.pizza_button:
                Intent pizzaIntent = new Intent(MainActivity.this, PizzaCustom.class);
                Bundle bundleProducts = new Bundle();
                bundleProducts.putSerializable("products", products);
                pizzaIntent.putExtras(bundleProducts);
                startActivity(pizzaIntent);
                break;

            case R.id.load_localdata_button:
                Intent employeesIntent = new Intent(MainActivity.this, EmployeesPayroll.class);
                startActivity(employeesIntent);
                break;

            case R.id.connection_button:
                Intent connectionIntent = new Intent(MainActivity.this, UserConnection.class);
                connectionCounter++;
                if(connectionCounter%2==0){
                    connectionIntent.putExtra("connectionBoolean",true);
                }else{
                    connectionIntent.putExtra("connectionBoolean",false);
                }
                startActivity(connectionIntent);
                break;

            case R.id.daily_info_button:
                Intent dailyInfo = new Intent(MainActivity.this, DailyInfoFacade.class);
                dailyInfo.putExtra("obj", myMenu);
                startActivity(dailyInfo);
                break;

        }
    }

    private void defaultsObjects(){

        try {
            DataAccessObject.getDao(getApplicationContext());
            AssetDatabaseOpenHelper databaseAdmin = new AssetDatabaseOpenHelper(getApplicationContext());
            SQLiteDatabase database = databaseAdmin.openDatabase();

            Cursor cursor = DataAccessObject.getDao(getApplicationContext()).getAllRowsProducts();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Products product = new Products();
                    //  product.setId(cursor.getInt(cursor.getColumnIndex("id")));


                    Products products = new Products(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getInt(cursor.getColumnIndex("cost")),
                            cursor.getInt(cursor.getColumnIndex("weight")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("season")),
                            cursor.getString(cursor.getColumnIndex("origin")),
                            cursor.getDouble(cursor.getColumnIndex("fatIndex")),
                            cursor.getString(cursor.getColumnIndex("originCountry")),
                            cursor.getString(cursor.getColumnIndex("wholemeal")),
                            cursor.getString(cursor.getColumnIndex("unit")),
                            cursor.getString(cursor.getColumnIndex("arrivalDate")),
                            cursor.getInt(cursor.getColumnIndex("duration"))

                    );
                    productsArrayList.add(products);
                    cursor.moveToNext();

                }
            }
            database.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Vegetal tomate = new Vegetal(this.productsArrayList.get(0).getName(),
                this.productsArrayList.get(0).getCost(),
                this.productsArrayList.get(0).getWeight(),
                this.productsArrayList.get(0).getType(),
                this.productsArrayList.get(0).getSeason()
        );
        Vegetal lechuga = new Vegetal(this.productsArrayList.get(1).getName(),
                this.productsArrayList.get(1).getCost(),
                this.productsArrayList.get(1).getWeight(),
                this.productsArrayList.get(1).getType(),
                this.productsArrayList.get(1).getSeason()
        );
        Vegetal cebolla = new Vegetal(this.productsArrayList.get(2).getName(),
                this.productsArrayList.get(2).getCost(),
                this.productsArrayList.get(2).getWeight(),
                this.productsArrayList.get(2).getType(),
                this.productsArrayList.get(2).getSeason()
        );
        Vegetal chucrut = new Vegetal(this.productsArrayList.get(3).getName(),
                this.productsArrayList.get(3).getCost(),
                this.productsArrayList.get(3).getWeight(),
                this.productsArrayList.get(3).getType(),
                this.productsArrayList.get(3).getSeason()
        );

        Vegetal olive = new Vegetal(this.productsArrayList.get(4).getName(),
                this.productsArrayList.get(4).getCost(),
                this.productsArrayList.get(4).getWeight(),
                this.productsArrayList.get(4).getType(),
                this.productsArrayList.get(4).getSeason()
        );

        Vegetal mushRoom = new Vegetal(this.productsArrayList.get(5).getName(),
                this.productsArrayList.get(5).getCost(),
                this.productsArrayList.get(5).getWeight(),
                this.productsArrayList.get(5).getType(),
                this.productsArrayList.get(5).getSeason()
        );

        Meat lomo = new Meat(
                this.productsArrayList.get(6).getName(),
                this.productsArrayList.get(6).getCost(),
                this.productsArrayList.get(6).getWeight(),
                this.productsArrayList.get(6).getType(),
                this.productsArrayList.get(6).getOrigin(),
                this.productsArrayList.get(6).getFatIndex()
        );
        Meat tapaPecho = new Meat(
                this.productsArrayList.get(7).getName(),
                this.productsArrayList.get(7).getCost(),
                this.productsArrayList.get(7).getWeight(),
                this.productsArrayList.get(7).getType(),
                this.productsArrayList.get(7).getOrigin(),
                this.productsArrayList.get(7).getFatIndex()
        );

        Meat pavo = new Meat(
                this.productsArrayList.get(8).getName(),
                this.productsArrayList.get(8).getCost(),
                this.productsArrayList.get(8).getWeight(),
                this.productsArrayList.get(8).getType(),
                this.productsArrayList.get(8).getOrigin(),
                this.productsArrayList.get(8).getFatIndex()
        );

        Meat pollo = new Meat(
                this.productsArrayList.get(9).getName(),
                this.productsArrayList.get(9).getCost(),
                this.productsArrayList.get(9).getWeight(),
                this.productsArrayList.get(9).getType(),
                this.productsArrayList.get(9).getOrigin(),
                this.productsArrayList.get(9).getFatIndex()
        );

        Meat choricillo = new Meat(
                this.productsArrayList.get(10).getName(),
                this.productsArrayList.get(10).getCost(),
                this.productsArrayList.get(10).getWeight(),
                this.productsArrayList.get(10).getType(),
                this.productsArrayList.get(10).getOrigin(),
                this.productsArrayList.get(10).getFatIndex()
        );

        Meat jamon = new Meat(
                this.productsArrayList.get(11).getName(),
                this.productsArrayList.get(11).getCost(),
                this.productsArrayList.get(11).getWeight(),
                this.productsArrayList.get(11).getType(),
                this.productsArrayList.get(11).getOrigin(),
                this.productsArrayList.get(11).getFatIndex()
        );

        Food cheese = new Food(
                this.productsArrayList.get(12).getName(),
                this.productsArrayList.get(12).getCost(),
                this.productsArrayList.get(12).getWeight(),
                this.productsArrayList.get(12).getType()
        );

        Drink cocacola = new Drink(
                this.productsArrayList.get(15).getName(),
                this.productsArrayList.get(15).getCost(),
                this.productsArrayList.get(15).getWeight(),
                this.productsArrayList.get(15).getType(),
                this.productsArrayList.get(15).getUnit()
        );

        Drink fanta = new Drink(
                this.productsArrayList.get(16).getName(),
                this.productsArrayList.get(16).getCost(),
                this.productsArrayList.get(16).getWeight(),
                this.productsArrayList.get(16).getType(),
                this.productsArrayList.get(16).getUnit()
        );

        Drink sprite = new Drink(
                this.productsArrayList.get(17).getName(),
                this.productsArrayList.get(17).getCost(),
                this.productsArrayList.get(17).getWeight(),
                this.productsArrayList.get(17).getType(),
                this.productsArrayList.get(17).getUnit()
        );

        Drink piñaSmall = new Drink(
                this.productsArrayList.get(18).getName(),
                this.productsArrayList.get(18).getCost(),
                this.productsArrayList.get(18).getWeight(),
                this.productsArrayList.get(18).getType(),
                this.productsArrayList.get(18).getUnit()
        );

        Drink naranjaSmall = new Drink(
                this.productsArrayList.get(19).getName(),
                this.productsArrayList.get(19).getCost(),
                this.productsArrayList.get(19).getWeight(),
                this.productsArrayList.get(19).getType(),
                this.productsArrayList.get(19).getUnit()
        );

        Drink frambuezaSmall = new Drink(
                this.productsArrayList.get(20).getName(),
                this.productsArrayList.get(20).getCost(),
                this.productsArrayList.get(20).getWeight(),
                this.productsArrayList.get(20).getType(),
                this.productsArrayList.get(20).getUnit()
        );

        Dessert iceCream = new Dessert(
                this.productsArrayList.get(21).getName(),
                this.productsArrayList.get(21).getCost(),
                this.productsArrayList.get(21).getWeight(),
                this.productsArrayList.get(21).getType(),
                this.productsArrayList.get(21).getArrivalDate(),
                this.productsArrayList.get(21).getDuration()

        );

        Dessert apple = new Dessert(
                this.productsArrayList.get(22).getName(),
                this.productsArrayList.get(22).getCost(),
                this.productsArrayList.get(22).getWeight(),
                this.productsArrayList.get(22).getType(),
                this.productsArrayList.get(22).getArrivalDate(),
                this.productsArrayList.get(22).getDuration()

        );

        Dessert cake = new Dessert(
                this.productsArrayList.get(23).getName(),
                this.productsArrayList.get(23).getCost(),
                this.productsArrayList.get(23).getWeight(),
                this.productsArrayList.get(23).getType(),
                this.productsArrayList.get(23).getArrivalDate(),
                this.productsArrayList.get(23).getDuration()

        );

       /* Vegetal tomate = new Vegetal("Tomate", 1000, 100, "importación", "alta");
        Vegetal lechuga = new Vegetal("Lechuga", 2000, 100, "nacional", "baja");
        Vegetal cebolla = new Vegetal("Cebolla", 3000, 100, "nacional", "baja");
        Vegetal chucrut = new Vegetal("Chucrut", 4000, 100, "importación", "baja");
        Vegetal olive = new V
        egetal("Aceitunas", 1000, 200, "importación", "baja");
        Vegetal mushRoom = new Vegetal("Champiñones", 1000, 400, "importación", "baja");

        Meat lomo = new Meat("Lomo", 300, 5000, "carne roja", "importada", 1.2);
        Meat tapaPecho = new Meat("Tapapecho", 300, 5000, "carne roja", "importada", 1.2);
        Meat pavo = new Meat("Pavo", 300, 5000, "carne blanca", "importada", 0.8);
        Meat pollo = new Meat("Pollo", 300, 5000, "carne blanca", "importada", 0.8);
        Meat choricillo = new Meat("Choricillo", 1000, 500, "embutidos", "nacional", 0.5);
        Meat jamon = new Meat("Jamón", 1000, 500, "embutidos", "nacional", 0.5);

        Food cheese = new Food("Queso", 1000, 50, "acompañamientos");
*/
        Bread frica = new Bread("Pan Frica", 200, 1000,"frica", "francia", false);
        Bread lorem = new Bread("Pan Molde", 200, 1000,"lorem", "ipsum", false);
/*
        Drink cocacola = new Drink("Coca-Cola Pequeña", 750, 500, "bebida", "cc");
        Drink fanta = new Drink("Fanta Pequeña", 750, 500, "bebida", "cc");
        Drink sprite = new Drink("Sprite Pequeña", 750, 500, "bebida", "cc");
        Drink piñaSmall = new Drink("Jugo Piña Pequeño", 1500, 350, "bebida", "cc");
        Drink naranjaSmall = new Drink("Jugo Naranja Pequeño", 1500, 350, "bebida", "cc");
        Drink frambuezaSmall = new Drink("Jugo Frambueza Pequeño", 1500, 350, "bebida", "cc");

        Dessert iceCream = new Dessert("Helado de crema", 500, 125, "helado", "29-06-16", 1);
        Dessert apple = new Dessert("Manzana", 500, 125, "fruta", "29-06-16", 5);
        Dessert cake = new Dessert("Torta", 500, 125, "torta", "29-06-16", 2);
*/

        vegetalsList.add(tomate);
        vegetalsList.add(lechuga);
        vegetalsList.add(cebolla);
        vegetalsList.add(chucrut);
        vegetalsList.add(olive);
        vegetalsList.add(mushRoom);

        meatsList.add(lomo);
        meatsList.add(tapaPecho);
        meatsList.add(pavo);
        meatsList.add(pollo);
        meatsList.add(choricillo);
        meatsList.add(jamon);

        breadsList.add(frica);
        breadsList.add(lorem);

        drinks.add(cocacola);
        drinks.add(fanta);
        drinks.add(sprite);
        drinks.add(piñaSmall);
        drinks.add(naranjaSmall);
        drinks.add(frambuezaSmall);

        desserts.add(iceCream);
        desserts.add(apple);
        desserts.add(cake);

        for (int i=0; i<vegetalsList.size(); i++){
            products.add(vegetalsList.get(i));
        }

        for (int i=0; i<meatsList.size(); i++){
            products.add(meatsList.get(i));
        }

        for (int i=0; i<breadsList.size(); i++){
            products.add(breadsList.get(i));
        }

        for (int i=0; i<drinks.size(); i++){
            products.add(drinks.get(i));
        }

        for (int i=0; i<desserts.size(); i++){
            products.add(desserts.get(i));
        }

        products.add(cheese);

    }




    private void defaultMenu(){
        myMenu = new Menu(1, "Default Menu", "Lorem ipsum Menu");
    }

    private Drawable uriPictureToBitmap(Uri selectedImage){
        Drawable resultDrawable = null;
        try {
            InputStream imageStream = MainActivity.this.getContentResolver().openInputStream(selectedImage);
            Bitmap returnedImageBitmap = BitmapFactory.decodeStream(imageStream);
            resultDrawable = new BitmapDrawable(getResources(), returnedImageBitmap);
            if(returnedImageBitmap.getHeight()>2000){
                int width = Math.round(returnedImageBitmap.getWidth() / 4);
                int height = Math.round(returnedImageBitmap.getHeight() / 4);
                resultDrawable = new BitmapDrawable(Bitmap.createScaledBitmap(returnedImageBitmap, width, height, false));
            }
        } catch (FileNotFoundException| OutOfMemoryError | NullPointerException e) {
            e.printStackTrace();
        }
        return resultDrawable;
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {}

    @Override
    public void onError(String s) {}

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {}
}
