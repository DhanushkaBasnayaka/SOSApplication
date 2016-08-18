package trimdevelopmentcom.sos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import trimdevelopmentcom.sos.models.Object_notification;

public class DatabaseHandler extends SQLiteOpenHelper {
    //send data
    public static final String KEY_ROWID_messg = "M_id";
    public static final String Push_email_one ="push_email1";
    public static final String Push_email_two ="push_email2";
    public static final String Push_messeg_one ="push_messeg1";
    public static final String Push_messeg_two ="push_messeg2";
    public static final String Push_location="push_locatio";
    public static final String Push_addres ="Push_addres";
    public static final String Push_date ="Push_date";
    public static final String Push_stetest ="Push_stetest";

    public static final String KEY_NOTID = "not_inotd";
    public static final String NOT_ID = "not_id";
    public static final String NOT_TITIL ="not_titil";
    public static final String NOT_MESSEG="not_messeg";
    public static final String NOT_DATE="not_date";
    public static final String NOT_WEBLINK="not_weblink";
    public static final String NOT_STETES="not_stetes";



    public static final String KEY_ROWID = "_id";
    public static final String U_nama ="U_nama";
    public static final String U_Email="U_Email";
    public static final String U_Phone="U_Phone";
    public static final String Sos_phone_1="Sos_phone_1";
    public static final String Sos_phone_2="Sos_phone_2";
    public static final String U_Phone_cord="U_Phone_cord";
    public static final String Sos_phone_1_cord="Sos_phone_1_cord";
    public static final String Sos_phone_2_cord="Sos_phone_2_cord";
    public static final String Sos_Email_1="Sos_Email_1";
    public static final String Sos_Email_2="Sos_Email_2";
    public static final String Sos_Custom_mas="Sos_Custom_mas";
    public static final String Sos_coundown_mas="Sos_coundown_mas";
    public static final String Sos_cord="Sos_cord";
    public static  final String Country="country";

    public static final String KEY_ROWID2 = "_id";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "MyDB";


    private static final String DATABASE_TABLE = "contacts";
    private static final String DATABASE_TABLE2 = "notification";
    private static final String DATABASE_TABLE3 = "Messeg_send";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ROWID + " INTEGER PRIMARY KEY,"
                + U_nama + " TEXT,"
                + U_Email + " TEXT,"
                + U_Phone + " TEXT,"
                + Sos_phone_1 + " TEXT,"
                + Sos_phone_2 + " TEXT,"
                + U_Phone_cord + " TEXT,"
                + Sos_phone_1_cord + " TEXT,"
                + Sos_phone_2_cord + " TEXT,"
                + Sos_Email_1 + " TEXT,"
                + Sos_Email_2 + " TEXT,"
                + Sos_Custom_mas + " TEXT,"
                + Sos_coundown_mas + " TEXT,"
                + Sos_cord + " TEXT,"

                + Country + " TEXT" +")";
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + DATABASE_TABLE2 + "("
                + KEY_NOTID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOT_TITIL + " TEXT,"
                + NOT_DATE + " TEXT,"
                + NOT_MESSEG + " TEXT,"
                + NOT_STETES + " TEXT,"
                + NOT_ID + " TEXT,"
                + NOT_WEBLINK + " TEXT" + ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
//
//        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + DATABASE_TABLE3 + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
//                + KEY_TITLE + " TEXT,"
//                + KEY_MESSAGE + " TEXT" + ")";
//        db.execSQL(CREATE_MESSAGE_TABLE);
//
//        String CREATE_WAITLIST_TABLE = "CREATE TABLE " + TABLE_WAITLIST + "("
//                + KEY_WID + " INTEGER PRIMARY KEY"  + ")";
//        db.execSQL(CREATE_WAITLIST_TABLE);

        String CREATE_SHIPPING_ADDRESS_TABLE = "CREATE TABLE " + DATABASE_TABLE3 + "("
                + KEY_ROWID_messg + " INTEGER PRIMARY KEY,"
                + Push_email_one + " TEXT,"
                + Push_email_two + " TEXT,"
                + Push_messeg_one + " TEXT,"
                + Push_messeg_two + " TEXT,"
                + Push_location + " TEXT,"
                + Push_addres + " TEXT,"
                + Push_date + " TEXT,"
                + Push_stetest + " TEXT" +")";
        db.execSQL(CREATE_SHIPPING_ADDRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);


        onCreate(db);
    }

//    public void addNotificationOnOrOff(String type, String switchOnOff) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TYPE, type);
//        values.put(KEY_NAME, switchOnOff);
//
//        db.insert(TABLE_NOTIFICATIONS_ON, null, values);
//        db.close();
//    }

    public void addinsertContact(String u_name,String u_email,String u_phone,String sos_phone_1,String sos_phone_2,String u_phone_cord,String sos_phone_1_cord,String sos_phone_2_cord,String sos_email_1,String sos_email_2
            ,String sos_custom_mas,String sos_coundown_mas,String sos_cord,String country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(U_nama, u_name);
        initialValues.put(U_Email, u_email);
        initialValues.put(U_Phone, u_phone);
        initialValues.put(Sos_phone_1, sos_phone_1);
        initialValues.put(Sos_phone_2, sos_phone_2);
        initialValues.put(U_Phone_cord, u_phone_cord);
        initialValues.put(Sos_phone_1_cord, sos_phone_1_cord);
        initialValues.put(Sos_phone_2_cord, sos_phone_2_cord);
        initialValues.put(Sos_Email_1, sos_email_1);
        initialValues.put(Sos_Email_2, sos_email_2);
        initialValues.put(Sos_Custom_mas, sos_custom_mas);
        initialValues.put(Sos_coundown_mas, sos_coundown_mas);
        initialValues.put(Sos_cord, sos_cord);
        initialValues.put(Country, country);

        db.insert(DATABASE_TABLE, null, initialValues);
        db.close();
    }

    public boolean updateinsertContact(long rowId, String u_name, String u_email,String u_phone,String sos_phone_1,String sos_phone_2,String u_phone_cord,String sos_phone_1_cord,String sos_phone_2_cord,String sos_email_1,String sos_email_2
            ,String sos_custom_mas,String sos_coundown_mas,String sos_cord,String country){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(U_nama, u_name);
        args.put(U_Email, u_email);
        args.put(U_Phone, u_phone);
        args.put(Sos_phone_1, sos_phone_1);
        args.put(Sos_phone_2, sos_phone_2);
        args.put(U_Phone_cord, u_phone_cord);
        args.put(Sos_phone_1_cord, sos_phone_1_cord);
        args.put(Sos_phone_2_cord, sos_phone_2_cord);
        args.put(Sos_Email_1, sos_email_1);
        args.put(Sos_Email_2, sos_email_2);

        args.put(Sos_Custom_mas, sos_custom_mas);
        args.put(Sos_coundown_mas, sos_coundown_mas);
        args.put(Sos_cord, sos_cord);
        args.put(Country, country);

        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public Cursor getAllContacts()
    {  SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, U_nama,U_Email,U_Phone,Sos_phone_1,
                        Sos_phone_2,U_Phone_cord,Sos_phone_1_cord,
                        Sos_phone_2_cord,Sos_Email_1,Sos_Email_2,Sos_Custom_mas,Sos_coundown_mas,Sos_cord,Sos_cord},
                null, null, null, null, null);
    }
//    public int getCountShippingAddress() {
//        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int count = cursor.getColumnIndex(KEY_ROWID);
//        cursor.close();
//
//        return count;
//    }

//    public boolean deleteContact(long rowId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_CART, KEY_CID + "=" + rowId, null) > 0;
//    }

//    public List<Cart_object> Read_Waitlist_cart() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        List<Cart_object> mainlist = new ArrayList<Cart_object>();
//        String selectQuery = "SELECT  * FROM " + TABLE_CART;
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c.moveToFirst()) {
//            do {
//                Cart_object wobject = new Cart_object();
//                wobject.setId(c.getInt((c.getColumnIndex(KEY_CID))));
//                wobject.setcon(c.getString((c.getColumnIndex(KEY_PRCONTATI))));
//                wobject.setzice(c.getString((c.getColumnIndex(KEY_ZICE))));
//                wobject.setShipping_class_ids(c.getString((c.getColumnIndex(KEY_SHIP))));
//
//                mainlist.add(wobject);
//            } while (c.moveToNext());
//        }
//
//        return mainlist;
//    }
//
//    public boolean updateContact(long rowId, int con){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(KEY_PRCONTATI, con);
//
//        return db.update(TABLE_CART, args, KEY_CID + "=" + rowId, null) > 0;
//    }
//
//    public void Inset_waitlist(Waitlist_Object wait) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_WID, wait.getId());
//
//        db.insert(TABLE_WAITLIST, null, values);
//        db.close();
//    }

    public void insertContact_notification(Object_notification c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(NOT_ID, c.getNot_id());
        initialValues.put(NOT_TITIL, c.getTitil());
        initialValues.put(NOT_MESSEG,c.getMeeseg() );
        initialValues.put(NOT_DATE,c.getTime() );
        initialValues.put(NOT_WEBLINK,c.getWeb_link() );
        initialValues.put(NOT_STETES,c.getStetest());

        db.insert(DATABASE_TABLE2, null, initialValues);
        db.close();
    }

    public List<Object_notification> Read_notification() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Object_notification> mainlist = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE2;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Object_notification wobject = new Object_notification();
                wobject.setId(c.getColumnIndex(KEY_NOTID));
                wobject.setNot_id(c.getString((c.getColumnIndex(NOT_ID))));
                wobject.setTitil(c.getString((c.getColumnIndex(NOT_TITIL))));
                wobject.setMeeseg(c.getString((c.getColumnIndex(NOT_MESSEG))));
                wobject.setTime(c.getString((c.getColumnIndex(NOT_DATE))));
                wobject.setWeb_link(c.getString((c.getColumnIndex(NOT_WEBLINK))));
                wobject.setStetest(c.getString((c.getColumnIndex(NOT_STETES))));
                mainlist.add(wobject);
            } while (c.moveToNext());
        }

        return mainlist;
    }
    public void insertContact_messeg(Obj_Messag c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();

        initialValues.put(Push_email_one, c.getPush_email_one_gold());
        initialValues.put(Push_email_two,c.getPush_email_two_gold() );
        initialValues.put(Push_messeg_one,c.getPush_messeg_one_gold() );
        initialValues.put(Push_messeg_two,c.getPush_messeg_two_gold() );
        initialValues.put(Push_location, c.getPush_location_gold() );
        initialValues.put(Push_addres, c.getPush_addres_gold());
        initialValues.put(Push_date, c.getPush_date_gold());
        db.insert(DATABASE_TABLE3, null, initialValues);
        db.close();
    }

    public List<Obj_Messag> Read_messeg() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Obj_Messag> mainlist = new ArrayList<Obj_Messag>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE3;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Obj_Messag wobject = new Obj_Messag();
                wobject.setId(c.getColumnIndex(KEY_ROWID_messg));
                wobject.setPush_messeg_two_gold(c.getString((c.getColumnIndex(Push_messeg_one))));

                wobject.setPush_messeg_one_gold(c.getString((c.getColumnIndex(Push_messeg_two))));
                wobject.setPush_email_one_gold(c.getString((c.getColumnIndex(Push_email_one))));
                wobject.setPush_email_two_gold(c.getString((c.getColumnIndex(Push_email_two))));
                wobject.setPush_location_gold(c.getString((c.getColumnIndex(Push_location))));
                wobject.setPush_addres_gold(c.getString((c.getColumnIndex(Push_addres))));
                wobject.setPush_date_gold(c.getString((c.getColumnIndex(Push_date))));


                mainlist.add(wobject);
            } while (c.moveToNext());
        }

        return mainlist;
    }
    public void deleteContact(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE3, null, null);
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DATABASE_TABLE3, Push_date + "=" + rowId, null) > 0;

    }


//    public boolean checkForWaitListID(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_WAITLIST + " WHERE "
//                + KEY_WID + " = " + id;
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if(c != null){
//            c.moveToFirst();
//            return true;
//        }else
//            return false;
//    }

//    public Message getMessage(int ID) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE "
//                + KEY_ID + " = " + ID;
//
//        Log.e("DatabaseHandler", selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        Message message = new Message();
//        message.setID(c.getInt(c.getColumnIndex(KEY_ID)));
//        message.setTitle((c.getString(c.getColumnIndex(KEY_TITLE))));
//        message.setMessage(c.getString(c.getColumnIndex(KEY_MESSAGE)));
//
//        return message;
//    }
//
    public int getCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getCount_notification() {
        String countQuery = "SELECT * FROM " + DATABASE_TABLE2 + " WHERE " + NOT_STETES + " = " + "0";;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean updateContact(String rowId, int con){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();

        args.put(NOT_STETES, 1);

        return db.update(DATABASE_TABLE2, args, NOT_ID + "=" + rowId, null)>0;
    }
}
