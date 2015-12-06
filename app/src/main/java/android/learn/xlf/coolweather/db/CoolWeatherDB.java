package android.learn.xlf.coolweather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.learn.xlf.coolweather.model.City;
import android.learn.xlf.coolweather.model.County;
import android.learn.xlf.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xulinfeng on 2015/12/6.
 */
public class CoolWeatherDB {

    /**
     * ���ݿ���
     */
    private static final String DB_NAME = "cool_weather";

    /**
     * ���ݿ�汾
     */
    private static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    /**
     * ���췽��˽�л�
     */
    private CoolWeatherDB(Context context)
    {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * ��ȡCoolWeatherDBʵ��
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * ��Provinceʵ���洢�����ݿ�
     */
    public void saveProvince(Province province) {
        if (province != null)
        {
            db.execSQL("insert into province(province_name�� province_code) values(?, ?)",
                    new String[]{province.getProvinceName(), province.getProvinceCode()});
//            ContentValues values = new ContentValues();
//            values.put("province_name", province.getProvinceName());
//            values.put("province_code", province.getProvinceCode());
//            db.insert("Province", null, values);
        }
    }

    /**
     * ���ݿ��ȡȫ�����е�ʡ����Ϣ
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.rawQuery("select * from province", null);
        if (cursor.moveToFirst())
        {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * ��cityʵ���洢�����ݿ�
     */
    public void saveCity(City city)
    {
        db.execSQL("insert into city(city_name, city_code, province_id) values (?, ?, ?)",
                new String[]{city.getCityName(), city.getCityCode(), String.valueOf(city.getProvinceId())});
    }

    public List<City> loadCities()
    {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.rawQuery("select * from city", null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * ��Countyʵ���洢�����ݿ�
     */
    public void saveCounty(County county)
    {
        db.execSQL("insert into county(county_name, county_code, city_id) values (?, ?, ?)",
                new String[]{county.getCountyName(), county.getCountyCode(), String.valueOf(county.getCityId())});
    }

    public List<County> loadCounties()
    {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.rawQuery("select * from county", null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            } while (cursor.moveToNext());
        }
        return list;
    }

}
