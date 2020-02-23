package com.example.clothes.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ClotheDAO extends Clothe {


    public ClotheDAO(Clothe clothe) {
        if (clothe != null) {
            id = clothe.id;
            name = clothe.name;
            price = clothe.price;
            size = clothe.size;
            price = clothe.price;
            description = clothe.description;
            purchaseDate = clothe.purchaseDate;
            isFavorite = clothe.isFavorite;
            state = clothe.state;
            image = clothe.image;
        }
    }

    public ClotheDAO() {

    }

    public ClotheDAO(int id) {
        this.id = id;
    }

    /**
     * Método usado para insertar el objeto Clothe en la base de datos.
     *
     * @return null si a ocurrido un problema al iniciar la conexión a la base de datos, -1 si hubo
     * un problema al insertar el dato o cualquier otro número positivo que representa el id del
     * objeto añadido.
     */
    public Long insert() throws Exception {
        Long id = null;
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        if (sqLiteDb != null) {
            ContentValues args = new ContentValues();
            args.put("name", name);
            args.put("price", price);
            args.put("size", size);
            args.put("description", description);
            args.put("purchaseDate", purchaseDate);
            args.put("isFavourite", isFavorite == true ? 1 : 0);
            args.put("state", state);
            args.put("image", image);

            //NullColumnHack: Posible Un posible string para insertar un valor nulo.
            id = sqLiteDb.insert("clothe", null, args);
            if (sqLiteDb != null) {
                sqLiteDb.close();
            }
        }

        if (id == null) {
            throw new Exception("Ocurrio un error insertando");
        } else {
            if (id.intValue() == -1) {
                throw new Exception("Ocurrio un error insertando");
            }
        }

        return id;
    }

    public static List<Clothe> getClothes() {
        List<Clothe> clothes = new ArrayList<>();
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        if (sqLiteDb != null) {
            String[] columnasAConsultar = {"name", "price", "image", "id"};
            Cursor cursor = sqLiteDb.query(
                    "clothe",
                    columnasAConsultar,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int price = cursor.getInt(cursor.getColumnIndex("price"));
                        String image = cursor.getString(cursor.getColumnIndex("image"));
                        int id = cursor.getInt(cursor.getColumnIndex("id"));

                        Clothe clothe = new Clothe(id, name, price, image);
                        clothes.add(clothe);
                    }
                }
            }
            if (sqLiteDb != null) {
                sqLiteDb.close();
            }
        }
        return clothes;
    }

    public Clothe getClothe() {
        Clothe clothe = null;

        if (this.id != null) {
            Database db = Database.getInstance();
            SQLiteDatabase sqLiteDb = db.getWritableDatabase();

            StringBuilder query = new StringBuilder("SELECT * FROM clothe WHERE id=?");
            Cursor cursor = sqLiteDb.rawQuery(query.toString(), new String[]{Integer.toString(this.id)});

            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int price = cursor.getInt(cursor.getColumnIndex("price"));
                        String size = cursor.getString(cursor.getColumnIndex("size"));
                        String description = cursor.getString(cursor.getColumnIndex("description"));
                        String purchaseDate = cursor.getString(cursor.getColumnIndex("purchaseDate"));
                        int isFavourite = cursor.getInt(cursor.getColumnIndex("isFavourite"));
                        boolean favourite = isFavourite != 0;
                        String state = cursor.getString(cursor.getColumnIndex("state"));
                        String image = cursor.getString(cursor.getColumnIndex("image"));
                        int id = cursor.getInt(cursor.getColumnIndex("id"));

                        clothe = new Clothe(id, name, price, size, description, purchaseDate,
                                favourite, state, image);
                    }
                }
            }
        }
        return clothe;
    }

    public boolean remove() {
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        Integer affectedRows = null;

        if (sqLiteDb != null) {
            affectedRows = sqLiteDb.delete("clothe", "id=" + id, null);
        }
        return affectedRows == null || affectedRows == 0 ? false : true;
    }

    public boolean update() {
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        Integer affectedRows = null;
        if (sqLiteDb != null) {
            ContentValues args = new ContentValues();
            args.put("name", name);
            args.put("price", price);
            args.put("size", size);
            args.put("description", description);
            args.put("purchaseDate", purchaseDate);
            args.put("isFavourite", isFavorite);
            args.put("state", state);
            args.put("image", image);
            affectedRows = sqLiteDb.update("clothe", args, "id=" + id, null);
        }
        return affectedRows == null || affectedRows == 0 ? false : true;
    }

    public static List<String> getStates() {
        List<String> states = new ArrayList<>();
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();
        if (sqLiteDb != null) {
            String[] columnasAConsultar = {"state"};
            Cursor cursor = sqLiteDb.query(
                    true,
                    "clothe",
                    columnasAConsultar,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String state = cursor.getString(0);
                        states.add(state);
                    }
                }
            }
        }

        return states;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Clothe> findBy() throws Exception {
        Database db = Database.getInstance();
        SQLiteDatabase sqLiteDb = db.getWritableDatabase();

        StringBuilder query = new StringBuilder("SELECT * FROM clothe WHERE ");
        ArrayList<String> args = new ArrayList<>();

        for (String column : getClotheFieldsToSearch()) {
            switch (column) {
                case "name":
                    query.append("name =?");
                    args.add(name);
                    break;
                case "purchaseDate":
                    query.append("purchaseDate =?");
                    args.add(purchaseDate);
                    break;
                case "state":
                    query.append("state =?");
                    args.add(state);
                    break;
                default:
                    throw new Exception("Columna no válida");

            }
            query.append(" AND ");

        }

        query.delete(query.length() - " AND ".length(), query.length());
        Object[] objArr = args.toArray();
        String[] params = Arrays.copyOf(objArr, objArr.length, String[].class);
        Cursor cursor = sqLiteDb.rawQuery(query.toString(), params.length == 0 ? null : params);

        List<Clothe> clothes = new ArrayList<>();

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int price = cursor.getInt(cursor.getColumnIndex("price"));
                    String size = cursor.getString(cursor.getColumnIndex("size"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String purchaseDate = cursor.getString(cursor.getColumnIndex("purchaseDate"));
                    int isFavourite = cursor.getInt(cursor.getColumnIndex("isFavourite"));
                    boolean favourite = isFavourite != 0;
                    String state = cursor.getString(cursor.getColumnIndex("state"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));

                    Clothe clothe = new Clothe(id, name, price, size, description, purchaseDate,
                            favourite, state, image);
                    clothes.add(clothe);
                }
            }
        }

        return clothes;
    }

    private List<String> getClotheFieldsToSearch() {
        ArrayList<String> searchFields = new ArrayList<>();

        if (name != null) {
            searchFields.add("name");
        }
        if (purchaseDate != null) {
            searchFields.add("purchaseDate");
        }
        if (state != null) {
            searchFields.add("state");
        }

        return searchFields;
    }


}
