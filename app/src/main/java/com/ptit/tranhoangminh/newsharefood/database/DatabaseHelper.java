package com.ptit.tranhoangminh.newsharefood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lãng on 9/3/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    private static final String DATABASE_NAME = "ProductDB";//ten co so du lieu
    private static final int DATABASE_VERSION = 1;//phien ban
    private static final String TABLE_PRODUCTS = "Product";//ten bang: Product

    private static final String KEY_ID = "ID";
    private static final String KEY_PID = "PID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_IMAGE = "IMAGE";

    private static final String TABLE_PRODUCTDETAIL = "ProductDetail";

    private static final String KEYPD_ID = "PD_ID";
    private static final String KEYPD_RECIPE = "PD_RECIPE";
    private static final String KEYPD_MATERIALS = "PD_MATERIALS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //tao cac bang
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "DatabaseHelper.onCreate...");

        String script = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                KEY_ID + " VARCHAR PRIMARY KEY  NOT NULL ," +
                KEY_PID + " VARCHAR NOT NULL ," +
                KEY_NAME + " VARCHAR NOT NULL ," +
                KEY_IMAGE + " BLOG NOT NULL )";

        //chay lenh tao bang
        sqLiteDatabase.execSQL(script);

        script = "CREATE TABLE " + TABLE_PRODUCTDETAIL + "(" +
                KEYPD_ID + " VARCHAR PRIMARY KEY  NOT NULL ," +
                KEYPD_MATERIALS + " VARCHAR NOT NULL ," +
                KEYPD_RECIPE + " VARCHAR NOT NULL )";

        //chay lenh tao bang
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "DatabaseHelper.onUpgrade...");

        //DROP TABLE
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDETAIL);

        //RECREATE
        onCreate(sqLiteDatabase);
    }

    public void createDefaultProductsifNeed() throws Exception {
        int count = this.getProductsCount();
        if (count == 0) {
            //create mop product
            try {
                //this.addProduct(pro1);
                //this.addProduct(pro2);
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void addProduct(Product product, Bitmap bitmap) throws SQLiteException {
        Log.i(TAG, "DatabaseHelper.addProduct... " + product.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.getId());
        values.put(KEY_PID, product.getParent_id());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_IMAGE, getBitmapAsByteArray(bitmap));

        //chen mot dong vao bang
        long resultId = db.insert(TABLE_PRODUCTS, null, values);
        if (resultId == -1) {
            throw new SQLiteException("Cannot insert item into " + TABLE_PRODUCTS);
        }
        //dong ket noi
        db.close();
    }

    public void addProductDetail(ProductDetail pDetail) throws SQLiteException {
        Log.i(TAG, "DatabaseHelper.addProductDetail... " + pDetail.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEYPD_ID, pDetail.getId());
        values.put(KEYPD_MATERIALS, pDetail.getMaterials());
        values.put(KEYPD_RECIPE, pDetail.getRecipe());

        //chen mot dong vao bang
        long resultId = db.insert(TABLE_PRODUCTDETAIL, null, values);
        if (resultId == -1) {
            throw new SQLiteException("Cannot insert item into " + TABLE_PRODUCTDETAIL);
        }
        //dong ket noi
        db.close();
    }

    public Product getProduct(String id) {
        Log.i(TAG, "DatabaseHelper.getProduct... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{KEY_ID, KEY_PID, KEY_NAME, KEY_IMAGE}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        ProductSQLite product = null;
        if (cursor.getCount() != 0 && cursor.moveToFirst()) {
            product = new ProductSQLite();
            product.setId(cursor.getString(0));
            product.setParent_id(cursor.getString(1));
            product.setName(cursor.getString(2));
            byte[] imageByte = cursor.getBlob(3);
            product.setBitmap(imageByte);
        }
        cursor.close();
        db.close();
        return product;
    }

    public ProductDetail getProductDetail(String id) {
        Log.i(TAG, "DatabaseHelper.getProductDetail... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTDETAIL, new String[]{KEYPD_ID, KEYPD_MATERIALS, KEYPD_RECIPE}, KEYPD_ID + "=?",
                new String[]{id}, null, null, null, null);
        ProductDetail pDetail = null;
        if (cursor.getCount() != 0 && cursor.moveToFirst()) {
            pDetail = new ProductDetail();
            pDetail.setId(cursor.getString(0));
            pDetail.setMaterials(cursor.getString(1));
            pDetail.setRecipe(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return pDetail;
    }

    public List<ProductSQLite> getAllProducts() {
        Log.i(TAG, "DatabaseHelper.getAllProducts...");

        List<ProductSQLite> productList = new ArrayList<>();
        //Select all query
        String query = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //duyet tren con tro va them vao list
        if (cursor.moveToFirst()) {
            do {
                ProductSQLite product = new ProductSQLite();
                product.setId(cursor.getString(0));
                product.setParent_id(cursor.getString(1));
                product.setName(cursor.getString(2));
                byte[] imageByte = cursor.getBlob(3);
                product.setBitmap(imageByte);

                //them vao list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> searchProducts(String name) {
        Log.i(TAG, "DatabaseHelper.searchProducts...");

        List<Product> productList = new ArrayList<>();
        //Select all query
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE name LIKE '%" + name + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //duyet tren con tro va them vao list
        if (cursor.moveToFirst()) {
            do {
                ProductSQLite product = new ProductSQLite();
                product.setId(cursor.getString(0));
                product.setParent_id(cursor.getString(1));
                product.setName(cursor.getString(2));
                byte[] imageByte = cursor.getBlob(3);
                product.setBitmap(imageByte);

                //them vao list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<ProductDetail> getAllProductsDetail() {
        Log.i(TAG, "DatabaseHelper.getAllProductsDetail...");

        List<ProductDetail> productDetailList = new ArrayList<>();
        //Select all query
        String query = "SELECT * FROM " + TABLE_PRODUCTDETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //duyet tren con tro va them vao list
        if (cursor.moveToFirst()) {
            do {
                ProductDetail pDetail = new ProductDetail();
                pDetail.setId(cursor.getString(0));
                pDetail.setMaterials(cursor.getString(1));
                pDetail.setRecipe(cursor.getString(2));

                //them vao list
                productDetailList.add(pDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productDetailList;
    }

    public int getProductsCount() {
        Log.i(TAG, "DatabaseHelper.getProductsCount...");

        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getProductsDetailCount() {
        Log.i(TAG, "DatabaseHelper.getProductsDetailCount...");

        String query = "SELECT * FROM " + TABLE_PRODUCTDETAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int updateProduct(ProductSQLite product) {
        Log.i(TAG, "DatabaseHeler.updateProduct... " + product.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.getId());
        values.put(KEY_PID, product.getParent_id());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_IMAGE, product.getBitmap());

        //update row
        return db.update(TABLE_PRODUCTS, values, KEY_ID + "=?", new String[]{product.getId()});
    }

    public int updateProductDetail(ProductDetail pDetail) {
        Log.i(TAG, "DatabaseHeler.updateProductDetail... " + pDetail.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEYPD_ID, pDetail.getId());
        values.put(KEYPD_MATERIALS, pDetail.getMaterials());
        values.put(KEYPD_RECIPE, pDetail.getRecipe());

        //update row
        return db.update(TABLE_PRODUCTDETAIL, values, KEYPD_ID + "=?", new String[]{pDetail.getId()});
    }

    public void deleteProduct(String id) {
        Log.i(TAG, "DatabaseHelp.deleteProduct... " + id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + "=?", new String[]{id});
        db.close();
    }

    public void deleteProductDetail(String id) {
        Log.i(TAG, "DatabaseHelp.deleteProductDetail... " + id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTDETAIL, KEYPD_ID + "=?", new String[]{id});
        db.close();
    }
}
