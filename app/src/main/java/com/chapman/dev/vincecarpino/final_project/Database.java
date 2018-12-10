package com.chapman.dev.vincecarpino.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.CalendarContract;
import android.util.Log;

//TODO: SERVICE????????????????
public class Database extends SQLiteOpenHelper {
    private Context context;
    private static int CURRENT_USER_ID = -1;
    private static Database sInstance;
    private static final String DATABASE_NAME  = "ChappyFAFS";
    private static final String USER_TABLE     = "User";
    private static final String PRODUCT_TABLE  = "Product";
    private static final String CATEGORY_TABLE = "Category";
    private static final String[] userColumns = {
            "Username",
            "Password",
            "Rating"
    };
    private static final String[] productColumns = {
            "Name",
            "Description",
            "CategoryID",
            "SellerID",
            "Price"
    };
    private static final String[] categories = {
            "Art",
            "Books",
            "Clothing",
            "Crafts",
            "Electronics",
            "Everything else",
            "Furniture",
            "Health & Beauty",
            "Jewelry",
            "Musical Instruments",
            "Real Estate",
            "Sporting Goods"
    };

    // deleteFromTable

    // selectFromTable

    private Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        //myDB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
//        SQLiteDatabase.CursorFactory factory = new SQLiteDatabase.CursorFactory() {
//            @Override
//            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
//                return null;
//            }
//        };
//
//        myDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, factory, null);

//        if (getCountOfCategoryTable() != categories.length) {
//            String sql = "DROP TABLE IF EXISTS Category;";
//            SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
//            stmt.executeUpdateDelete();
//
//            createCategoryTable();
//
//            populateCategoryTable();
//        }
    }

    public static synchronized Database getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Database(context);
        }
        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);

        createProductTable(db);

        createCategoryTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void createUserTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS User("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username VARCHAR, "
                + "Password VARCHAR, "
                + "Rating DECIMAL(1,1));";
        db.execSQL(sql);
//        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
//        stmt.execute();
    }

    private void createProductTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Product("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR, "
                + "Description VARCHAR, "
                + "CategoryID INTEGER, "
                + "SellerID INTEGER, "
                + "Price DECIMAL(4,2));";
        db.execSQL(sql);
//        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
//        stmt.execute();
    }

    private void createCategoryTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Category("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR);";
        db.execSQL(sql);

        populateCategoryTable(db);
//        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
//        stmt.execute();
    }

    public void insertIntoUser(User u) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(userColumns[0], u.getUsername());
            values.put(userColumns[1], u.getPassword());
            values.put(userColumns[2], 0f);

            db.insertOrThrow(USER_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DATABASE", "Error adding to User table");
        } finally {
            db.endTransaction();
        }
//
//        String sql = "INSERT INTO User(Username, Password) VALUES(?,?);";
//        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
//        stmt.bindString(1, u.getUsername());
//        stmt.bindString(2, u.getPassword());
//
//        stmt.executeInsert();

        // TODO: return id of new user
    }

    public void insertIntoProduct(Product p) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(productColumns[0], p.getName());
            values.put(productColumns[1], p.getDescription());
            values.put(productColumns[2], p.getCategoryId());
            values.put(productColumns[3], p.getSellerId());
            values.put(productColumns[4], p.getPrice());

            db.insertOrThrow(PRODUCT_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DATABASE", "Error adding to Product table");
        } finally {
            db.endTransaction();
        }

//        String sql = "INSERT INTO Product(Name, Desription, CategoryID, SellerID, Price) VALUES(?,?,?,?,?);";
//        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
//        stmt.bindString(1, p.getName());
//        stmt.bindString(2, p.getDescription());
//        stmt.bindDouble(3, p.getCategoryId());
//        stmt.bindDouble(4, p.getSellerId());
//        stmt.bindDouble(5, p.getPrice());
//
//        stmt.executeInsert();
    }

    private long getCountOfCategoryTable() {
        String sql = "SELECT COUNT(*) FROM Category;";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);

        return stmt.simpleQueryForLong();
    }

    private void populateCategoryTable(SQLiteDatabase db) {
        for (String c : categories) {

            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();

                values.put("Name", c);

                db.insertOrThrow(CATEGORY_TABLE, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e("DATABASE", "Error adding to Category table");
            } finally {
                db.endTransaction();
            }
//            String sql = "INSERT INTO Category(Name) VALUES(?);";
//            SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
//            stmt.bindString(1, c);
//
//            stmt.executeInsert();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.executeInsert();
    }

    public String getUsername(int id) {
        String sql = "SELECT Username FROM User WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        String username = stmt.simpleQueryForString();

        return username;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE ID=?;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[] { String.valueOf(id) });
        c.moveToFirst();
        User user = new User();

        user.setId(Integer.valueOf(c.getString(0)));
        user.setUsername(c.getString(1));
        user.setPassword(c.getString(2));
        user.setRating(Float.valueOf(c.getString(3)));

        c.close();

        return user;
    }

    public int checkIfUserExists(String username, String password) {
        int idOfResult;
        String sql = "SELECT * FROM User WHERE Username=? AND Password=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { username, password });

        c.moveToFirst();

        idOfResult = c.getCount() == 0 ? -1 : c.getInt(0);

        c.close();

        return idOfResult;
    }

    public int getCategoryIdByName(String name) {
        int idOfResult;
        String sql = "SELECT ID FROM Category WHERE Name=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { name });

        c.moveToFirst();

        idOfResult = c.getInt(0);

        c.close();

        return idOfResult;
    }

    public static void setCurrentUserId(int id) {
        CURRENT_USER_ID = id;
    }


    public static int getCurrentUserId() {
        return CURRENT_USER_ID;
    }

//    public ArrayList<String> getProductDetails(int id)
//    {
//        ArrayList<String> productDetails = new ArrayList<>();
//
//        String sql = "SELECT Name, Description, CategoryID, sellerID, Price FROM Product WHERE ID=" + String.valueOf(id);
//        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
//        //not finished
//    }
}
