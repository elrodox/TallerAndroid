package conceptosejemplo.com.conceptosejemplo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class DataAccessObject {

    private static DataAccessObject dao;
    private static Context context;
    private static SQLiteDatabase database;
    private static JSONArray presenceList;
    private static AssetDatabaseOpenHelper databaseAdmin;

    private DataAccessObject(){}

    public static DataAccessObject getDao(Context currentContext) throws JSONException {
        if(dao==null){
            dao = new DataAccessObject();
            context = currentContext;
            loadDataBase(context);
            try {
                loadJsonFile();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    private static void loadDataBase(Context context){
        databaseAdmin = new AssetDatabaseOpenHelper(context);
    }

    private static String loadJSONFromAsset(String nameJSONFile){
        String json = null;
        try {
            InputStream is = context.getAssets().open(nameJSONFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static void loadJsonFile() throws JSONException {
        String nameJSONFile = "presence.json";
        presenceList = new JSONArray(loadJSONFromAsset(nameJSONFile));
    }

    public String getPresenceById(int id) throws JSONException {
        String result = "Ausente";
        for (int i=0; i<presenceList.length(); i++){
            JSONObject jsonObject = presenceList.getJSONObject(i);
            if(jsonObject.getString("id").equals(Integer.toString(id))){
                if(jsonObject.getBoolean("presence")){
                    result = "Presente";
                }
            }
        }
        return result;
    }

    public void openDatabase(){
        database = databaseAdmin.openDatabase();
    }

    public void closeDatabase(){
        database.close();
    }

    public Cursor getAllEmployees(){
        database = databaseAdmin.openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM employees", null);
        //database.close();
        return cursor;
    }

    public Cursor getRowEmployeeByName(String name){
        database = databaseAdmin.openDatabase();
        Cursor cursorWhere = database.rawQuery("SELECT * FROM employees WHERE name = ?", new String[]{name}, null);
        database.close();
        return cursorWhere;
    }

    public Cursor getUser(String user, String password){
        database = databaseAdmin.openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user where username = '"+user+"' and password = '"+password+"' ", null);
        //database.close();
        return cursor;
    }

    public Cursor getAllRowsProducts(){
        database = databaseAdmin.openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM productos", null);
        //Cursor cursor2 = database.rawQuery("",null);
        //database.close();
        return cursor;
    }




}
