package com.datn.quanlybanhang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.model.SanPham;

import java.util.ArrayList;
import java.util.List;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Quan_Ly_Ban_Hang";

    private static final String TABLE_KHACHHANG = "KHACHHANG";
    private static final String COLUMN_KHACHHANG_ID = "KHACHHANG_Id";
    private static final String COLUMN_KHACHHANG_HOTEN = "KHACHHANG_HOTEN";
    private static final String COLUMN_KHACHHANG_DIACHI = "KHACHHANG_DIACHI";
    private static final String COLUMN_KHACHHANG_SODT = "KHACHHANG_SODT";
    private static final String COLUMN_KHACHHANG_GHICHU = "KHACHHANG_GHICHU";
    private static final String COLUMN_KHACHHANG_EMAIL = "KHACHHANG_EMAIL";

    private static final String TABLE_NHANVIEN = "NHANVIEN";
    private static final String COLUMN_NHANVIEN_MANV  = "NHANVIEN_MANV";
    private static final String COLUMN_NHANVIEN_HOTEN = "NHANVIEN_HOTEN";
    private static final String COLUMN_NHANVIEN_SODT = "NHANVIEN_SODT";
    private static final String COLUMN_NHANVIEN_EMAIL = "NHANVIEN_EMAIL";
    private static final String COLUMN_NHANVIEN_QUYEN = "NHANVIEN_QUYEN";
    private static final String COLUMN_NHANVIEN_PASSWORD = "NHANVIEN_PASSWORD";
    private static final String COLUMN_NHANVIEN_IMAGE = "NHANVIEN_IMAGE";

    private static final String TABLE_SANPHAM = "SANPHAM";
    private static final String COLUMN_SANPHAM_MASP = "SANPHAM_MASP";
    private static final String COLUMN_SANPHAM_TENSP = "SANPHAM_TENSP";
    private static final String COLUMN_SANPHAM_DVT = "SANPHAM_DVT";
    private static final String COLUMN_SANPHAM_NUOCSX ="SANPHAM_NUOCSX";
    private static final String COLUMN_SANPHAM_GIA = "SANPHAM_GIA";
    private static final String COLUMN_SANPHAM_CHITIET = "SANPHAM_CHITIET";
    private static final String COLUMN_SANPHAM_SOLUONG = "SANPHAM_SOLUONG";
    private static final String COLUMN_SANPHAM_IMAGE = "SANPHAM_IMAGE";
    private static final String COLUMN_SANPHAM_DANHMUC = "SANPHAM_DANHMUC";


    private static final String TABLE_HOADON = "HOADON";
    private static final String COLUMN_HOADON_SOHD = "HOADON_SOHD";
    private static final String COLUMN_HOADON_NGAYHD = "HOADON_NGAYHD";
    private static final String COLUMN_HOADON_MAKH = "HOADON_MAKH";
    private static final String COLUMN_HOADON_MANV = "HOADON_MANV";
    private static final String COLUMN_HOADON_TRIGIA = "HOADON_TRIGIA";
    private static final String COLUMN_HOADON_NO = "HOADON_NO";

    private static final String TABLE_CTHD ="CTHD";
    private static final String COLUMN_CTHD_SOHD = "CTHD_SOHD";
    private static final String COLUMN_CTHD_MASP = "CTHD_MASP";
    private static final String COLUMN_CTHD_SL = "CTHD_SL";

    private static final String TABLE_NHAPHANG ="NHAPHANG";
    private static final String COLUMN_NHAPHANG_SOHDNHAP = "NHAPHANG_SOHDNHAP";
    private static final String COLUMN_NHAPHANG_MASP = "NHAPHANG_MASP";
    private static final String COLUMN_NHAPHANG_MAKH = "NHAPHANG_MAKH";
    private static final String COLUMN_NHAPHANG_SL = "NHAPHANG_SL";
    private static final String COLUMN_NHAPHANG_GIANHAP = "NHAPHANG_GIANHAP";
    private static final String COLUMN_NHAPHANG_GIABAN = "NHAPHANG_GIABAN";
    private static final String COLUMN_NHAPHANG_MANV = "NHAPHANG_MANV";
    private static final String COLUMN_NHAPHANG_NGAYNHAP = "NHAPHANG_NGAYNHAP";
    private static final String COLUMN_NHAPHANG_NO = "NHAPHANG_NO";




    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String  script0  = "CREATE TABLE "+TABLE_KHACHHANG+" ( "
                + COLUMN_KHACHHANG_ID+" TEXT PRIMARY KEY, "
                + COLUMN_KHACHHANG_HOTEN+" TEXT, "
                + COLUMN_KHACHHANG_DIACHI+" TEXT, "
                + COLUMN_KHACHHANG_EMAIL+" TEXT, "
                + COLUMN_KHACHHANG_SODT + " TEXT, "
                + COLUMN_KHACHHANG_GHICHU  + " TEXT )";

        String  script1  = "CREATE TABLE "+TABLE_HOADON+" ( "
                + COLUMN_HOADON_SOHD+" TEXT PRIMARY KEY, "
                + COLUMN_HOADON_MAKH+" TEXT, "
                + COLUMN_HOADON_MANV+" TEXT, "
                + COLUMN_HOADON_NGAYHD+" TEXT, "
                + COLUMN_HOADON_TRIGIA + " INTEGER, "
                + COLUMN_HOADON_NO + " INTEGER ) ";

        String  script2  = " CREATE TABLE "+TABLE_SANPHAM+" ( "
                + COLUMN_SANPHAM_MASP+" TEXT PRIMARY KEY, "
                + COLUMN_SANPHAM_TENSP+" TEXT, "
                + COLUMN_SANPHAM_DVT+" TEXT, "
                + COLUMN_SANPHAM_NUOCSX+" TEXT, "
                + COLUMN_SANPHAM_CHITIET+" TEXT, "
                + COLUMN_SANPHAM_SOLUONG+" INTEGER, "
                + COLUMN_SANPHAM_IMAGE+" BLOB, "
                + COLUMN_SANPHAM_DANHMUC+" TEXT, "
                + COLUMN_SANPHAM_GIA + " INTEGER )";

        String  script3  = "CREATE TABLE "+TABLE_CTHD+" ( "
                + COLUMN_CTHD_SOHD+" TEXT, "
                + COLUMN_CTHD_MASP+" TEXT, "
                + COLUMN_CTHD_SL+" INTEGER )";
        String  script4  = "CREATE TABLE "+TABLE_NHANVIEN+" ( "
                + COLUMN_NHANVIEN_MANV+" TEXT PRIMARY KEY, "
                + COLUMN_NHANVIEN_HOTEN+" TEXT, "
                + COLUMN_NHANVIEN_EMAIL+" TEXT, "
                + COLUMN_NHANVIEN_SODT+" TEXT, "
                + COLUMN_NHANVIEN_QUYEN+" INTEGER, "
                + COLUMN_NHANVIEN_IMAGE+" BLOB, "
                + COLUMN_NHANVIEN_PASSWORD+" TEXT) ";


        String  script5  = "CREATE TABLE "+TABLE_NHAPHANG+" ( "
                + COLUMN_NHAPHANG_SOHDNHAP+" TEXT PRIMARY KEY, "
                + COLUMN_NHAPHANG_MASP+" TEXT, "
                + COLUMN_NHAPHANG_MANV+" TEXT, "
                + COLUMN_NHAPHANG_MAKH+" TEXT, "
                + COLUMN_NHAPHANG_NGAYNHAP+" TEXT, "
                + COLUMN_NHAPHANG_GIANHAP+" INTEGER, "
                + COLUMN_NHAPHANG_GIABAN+" INTEGER, "
                + COLUMN_NHAPHANG_SL+" INTEGER, "
                + COLUMN_NHAPHANG_NO+" INTEGER ) ";


        sqLiteDatabase.execSQL(script0);
        sqLiteDatabase.execSQL(script1);
        sqLiteDatabase.execSQL(script2);
        sqLiteDatabase.execSQL(script3);
        sqLiteDatabase.execSQL(script4);
        sqLiteDatabase.execSQL(script5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_KHACHHANG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CTHD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_HOADON);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NHANVIEN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_SANPHAM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NHAPHANG);
        onCreate(sqLiteDatabase);

    }

    public Cursor execSQLSelect(String sql,String[] args){
        String selectQuery = "SELECT * FROM "+TABLE_NHAPHANG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.rawQuery(sql,args);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        sqLiteDatabase.close();
        return cursor;
    }
    // CRUD SanPham
    public boolean addHoaDonNhap(HoaDonNhap hoaDonNhap){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHAPHANG_SOHDNHAP,hoaDonNhap.getSoHDNhap());
        values.put(COLUMN_NHAPHANG_MASP,hoaDonNhap.getMaSP());
        values.put(COLUMN_NHAPHANG_MANV,hoaDonNhap.getMaNV());
        values.put(COLUMN_NHAPHANG_MAKH,hoaDonNhap.getMaKH());
        values.put(COLUMN_NHAPHANG_NGAYNHAP,hoaDonNhap.getNgayNhap());
        values.put(COLUMN_NHAPHANG_GIABAN,hoaDonNhap.getGiaBan());
        values.put(COLUMN_NHAPHANG_GIANHAP,hoaDonNhap.getGiaNhap());
        values.put(COLUMN_NHAPHANG_SL,hoaDonNhap.getSoLuong());
        values.put(COLUMN_NHAPHANG_NO,hoaDonNhap.getHoaDonNhapNo());
        long i = sqLiteDatabase.insert(TABLE_NHAPHANG, null, values);
        sqLiteDatabase.close();
        return i > 0;
    }

    public HoaDonNhap getHoaDonNhap(String soHoaDonNhap){
        String [] stringColumns = {
                COLUMN_NHAPHANG_SOHDNHAP,
                COLUMN_NHAPHANG_MASP,
                COLUMN_NHAPHANG_MANV,
                COLUMN_NHAPHANG_MAKH,
                COLUMN_NHAPHANG_NGAYNHAP,
                COLUMN_NHAPHANG_GIANHAP,
                COLUMN_NHAPHANG_GIABAN,
                COLUMN_NHAPHANG_SL,
                COLUMN_NHAPHANG_NO,
        };
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NHAPHANG,stringColumns,COLUMN_NHAPHANG_SOHDNHAP + " = ?",
                new String[]{soHoaDonNhap},null,null,null);
        HoaDonNhap hoaDonNhap =  null;
        if(cursor.moveToFirst()) {
            hoaDonNhap = new HoaDonNhap(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getLong(5),
                    cursor.getLong(6),
                    cursor.getInt(7),
                    cursor.getInt(8));
            cursor.close();
        }
        return hoaDonNhap;
    }
    public List<HoaDonNhap> getListHoaDonNhap(){
        List<HoaDonNhap> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NHAPHANG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                HoaDonNhap hoaDonNhap = new HoaDonNhap(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getLong(5),
                        cursor.getLong(6),
                        cursor.getInt(7),
                        cursor.getInt(8));
                list.add(hoaDonNhap);
            }while (cursor.moveToNext());
            cursor.close();
        }
        sqLiteDatabase.close();
        return list;
    }
    public int getCountHoaDonNhap(){
        String selectQuery = "SELECT * FROM "+TABLE_NHAPHANG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateHoaDonNhap(HoaDonNhap hoaDonNhap){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHAPHANG_MASP,hoaDonNhap.getMaSP());
        values.put(COLUMN_NHAPHANG_MANV,hoaDonNhap.getMaNV());
        values.put(COLUMN_NHAPHANG_MAKH,hoaDonNhap.getMaKH());
        values.put(COLUMN_NHAPHANG_NGAYNHAP,hoaDonNhap.getNgayNhap());
        values.put(COLUMN_NHAPHANG_GIABAN,hoaDonNhap.getGiaBan());
        values.put(COLUMN_NHAPHANG_GIANHAP,hoaDonNhap.getGiaNhap());
        values.put(COLUMN_NHAPHANG_SL,hoaDonNhap.getSoLuong());
        values.put(COLUMN_NHAPHANG_NO,hoaDonNhap.getHoaDonNhapNo());

        int i =  sqLiteDatabase.update(TABLE_NHAPHANG,values,COLUMN_NHAPHANG_SOHDNHAP+ " = ?",
                new String[]{hoaDonNhap.getSoHDNhap()});
        sqLiteDatabase.close();
        return i;
    }

    public boolean deleteHoaDonNhap(HoaDonNhap hoaDonNhap){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = sqLiteDatabase.delete(TABLE_NHAPHANG,COLUMN_NHAPHANG_SOHDNHAP+" = ?",
                new String[]{hoaDonNhap.getSoHDNhap()});
        sqLiteDatabase.close();
        return i>0;
    }
    // CRUD SanPham
    public boolean addSanPham(SanPham sanPham){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SANPHAM_MASP,sanPham.getMaSP());
        values.put(COLUMN_SANPHAM_TENSP,sanPham.getTenSP());
        values.put(COLUMN_SANPHAM_DVT,sanPham.getDonViTinh());
        values.put(COLUMN_SANPHAM_NUOCSX,sanPham.getNuocSX());
        values.put(COLUMN_SANPHAM_CHITIET,sanPham.getChiTietSP());
        values.put(COLUMN_SANPHAM_SOLUONG,sanPham.getSoLuongSP());
        values.put(COLUMN_SANPHAM_GIA,sanPham.getGiaSP());
        values.put(COLUMN_SANPHAM_DANHMUC,sanPham.getLoaiSP());
        values.put(COLUMN_SANPHAM_IMAGE,sanPham.getImgSP());
        long i = sqLiteDatabase.insert(TABLE_SANPHAM, null, values);
        sqLiteDatabase.close();
        return i > 0;
    }

    public SanPham getSanPham(String maSP){
        String [] stringColumns = {
                COLUMN_SANPHAM_MASP,
                COLUMN_SANPHAM_TENSP,
                COLUMN_SANPHAM_CHITIET,
                COLUMN_SANPHAM_DVT,
                COLUMN_SANPHAM_NUOCSX,
                COLUMN_SANPHAM_GIA,
                COLUMN_SANPHAM_SOLUONG,
                COLUMN_SANPHAM_DANHMUC,
                COLUMN_SANPHAM_IMAGE
        };
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_SANPHAM,stringColumns,COLUMN_SANPHAM_MASP + " = ?",
                new String[]{maSP},null,null,null);
        SanPham sanPham =  null;
        if(cursor.moveToFirst()) {
            sanPham = new SanPham(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(2),
                    cursor.getLong(5),
                    cursor.getInt(6),
                    cursor.getBlob(8),
                    cursor.getString(7)
            );
            cursor.close();
        }
        return sanPham;
    }
    public List<SanPham> getListSanPham(){
        List<SanPham> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_SANPHAM;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                SanPham sanPham = new SanPham(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getLong(8),
                        cursor.getInt(5),
                        cursor.getBlob(6),
                        cursor.getString(7)
                );
                list.add(sanPham);
            }while (cursor.moveToNext());
            cursor.close();
        }
        sqLiteDatabase.close();
        return list;
    }
    public int getCountSanPham(){
        String selectQuery = "SELECT * FROM "+TABLE_SANPHAM;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateSanPham(SanPham sanPham){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SANPHAM_TENSP,sanPham.getTenSP());
        values.put(COLUMN_SANPHAM_NUOCSX,sanPham.getNuocSX());
        values.put(COLUMN_SANPHAM_DVT,sanPham.getDonViTinh());
        values.put(COLUMN_SANPHAM_CHITIET,sanPham.getChiTietSP());
        values.put(COLUMN_SANPHAM_SOLUONG,sanPham.getSoLuongSP());
        values.put(COLUMN_SANPHAM_GIA,sanPham.getGiaSP());
        values.put(COLUMN_SANPHAM_DANHMUC,sanPham.getLoaiSP());
        values.put(COLUMN_SANPHAM_IMAGE,sanPham.getImgSP());

        int i =  sqLiteDatabase.update(TABLE_SANPHAM,values,COLUMN_SANPHAM_MASP+ " = ?",
                new String[]{sanPham.getMaSP()});

        sqLiteDatabase.close();
        return i;
    }

    public boolean deleteSanPham(SanPham sanPham){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = sqLiteDatabase.delete(TABLE_SANPHAM,COLUMN_SANPHAM_MASP+" = ?",
                new String[]{sanPham.getMaSP()});
        sqLiteDatabase.close();
        return i>0;
    }


    // CRUD Hoa Don
    public boolean addHoaDon(HoaDon hoaDon){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues valuesHoaDon = new ContentValues();
        valuesHoaDon.put(COLUMN_HOADON_MAKH,hoaDon.getMaKH());
        valuesHoaDon.put(COLUMN_HOADON_MANV,hoaDon.getMaNV());
        valuesHoaDon.put(COLUMN_HOADON_NGAYHD,hoaDon.getNgayHD());
        valuesHoaDon.put(COLUMN_HOADON_SOHD,hoaDon.getSoHD());
        valuesHoaDon.put(COLUMN_HOADON_TRIGIA,hoaDon.getTriGia());
        valuesHoaDon.put(COLUMN_HOADON_NO,hoaDon.getHoaDonNo());


        ContentValues valuesCTHD = new ContentValues();
        for(SanPham sanPham : hoaDon.getSanPhamList()) {
            valuesCTHD.put(COLUMN_CTHD_SOHD,hoaDon.getSoHD());
            valuesCTHD.put(COLUMN_CTHD_MASP,sanPham.getMaSP());
            valuesCTHD.put(COLUMN_CTHD_SL,sanPham.getSoLuongSP());
            long b = sqLiteDatabase.insert(TABLE_CTHD,null,valuesCTHD);
            if(b<0) return false;
        }
        long a = sqLiteDatabase.insert(TABLE_HOADON,null,valuesHoaDon);
        sqLiteDatabase.close();
        return a>0;
    }

    public HoaDon getHoaDon(String SOHD){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String [] stringColumns = {
                COLUMN_HOADON_SOHD,
                COLUMN_HOADON_MAKH,
                COLUMN_HOADON_NGAYHD,
                COLUMN_HOADON_MANV,
                COLUMN_HOADON_TRIGIA,
                COLUMN_HOADON_NO
        };
        Cursor cursorHoaDon = sqLiteDatabase.query(TABLE_HOADON,stringColumns,COLUMN_HOADON_SOHD + " = ? ",
                new String[]{SOHD},null,null,null);

        String sql = "SELECT "+COLUMN_SANPHAM_MASP+","
                +COLUMN_SANPHAM_TENSP+ ","
                +COLUMN_SANPHAM_DVT+","
                +COLUMN_SANPHAM_NUOCSX+","
                +COLUMN_SANPHAM_CHITIET+","
                +COLUMN_CTHD_SL+","
                +COLUMN_SANPHAM_GIA+","
                +COLUMN_SANPHAM_IMAGE+","
                +COLUMN_SANPHAM_DANHMUC+" FROM "+TABLE_SANPHAM+","+TABLE_CTHD+" WHERE "
                +COLUMN_CTHD_SOHD+" = ? AND "+COLUMN_CTHD_MASP+" = ?";
        Cursor cursorCTHD = sqLiteDatabase.rawQuery(sql,new String[]{SOHD,COLUMN_SANPHAM_MASP});
        List<SanPham> sanPhamList = new ArrayList<>();
        HoaDon hoaDon = null;
        if(cursorHoaDon.moveToFirst()) {
            if(cursorCTHD.moveToFirst()) {
                do {
                    SanPham sanPham = new SanPham(cursorCTHD.getString(0),
                            cursorCTHD.getString(1),
                            cursorCTHD.getString(2),
                            cursorCTHD.getString(3),
                            cursorCTHD.getString(4),
                            cursorCTHD.getLong(6),
                            cursorCTHD.getInt(5),
                            cursorCTHD.getBlob(7),
                            cursorCTHD.getString(8)
                    );
                    sanPhamList.add(sanPham);
                } while (cursorCTHD.moveToNext());
                cursorCTHD.close();
            }

            hoaDon = new HoaDon(
                    cursorHoaDon.getString(0),
                    cursorHoaDon.getString(2),
                    cursorHoaDon.getString(1),
                    cursorHoaDon.getString(3),
                    cursorHoaDon.getLong(4),
                    sanPhamList,
                    cursorHoaDon.getInt(5)
            );

            cursorHoaDon.close();
        }
        return hoaDon;
    }

    public List<HoaDon> getListHoaDon(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<HoaDon> list = new ArrayList<>();
        String sqlHoaDon="SELECT *FROM "+TABLE_HOADON;
        Cursor cursorHoaDon = sqLiteDatabase.rawQuery(sqlHoaDon,null);
        Cursor cursorCTHD;
        if(cursorHoaDon.moveToFirst()) {
            do {
                String sql = "SELECT "+ COLUMN_SANPHAM_MASP + ","
                        + COLUMN_SANPHAM_TENSP + ","
                        + COLUMN_SANPHAM_DVT + ","
                        + COLUMN_SANPHAM_NUOCSX + ","
                        + COLUMN_SANPHAM_CHITIET + ","
                        + COLUMN_CTHD_SL + ","
                        + COLUMN_SANPHAM_GIA +","
                        + COLUMN_SANPHAM_IMAGE +","
                        + COLUMN_SANPHAM_DANHMUC +" FROM " + TABLE_CTHD + "," + TABLE_SANPHAM + " WHERE "
                        + COLUMN_CTHD_MASP + " = "+COLUMN_SANPHAM_MASP +" AND " + COLUMN_CTHD_SOHD + " = ?";
                cursorCTHD = sqLiteDatabase.rawQuery(sql,new String[]{cursorHoaDon.getString(0)});
                List<SanPham> sanPhamList = new ArrayList<>();

                if (cursorCTHD.moveToFirst()) {
                    do {
                        SanPham sanPham = new SanPham(cursorCTHD.getString(0),
                                cursorCTHD.getString(1),
                                cursorCTHD.getString(2),
                                cursorCTHD.getString(3),
                                cursorCTHD.getString(4),
                                cursorCTHD.getLong(6),
                                cursorCTHD.getInt(5),
                                cursorCTHD.getBlob(7),
                                cursorCTHD.getString(8)
                        );
                        sanPhamList.add(sanPham);
                    } while (cursorCTHD.moveToNext());
                    cursorCTHD.close();
                }
                HoaDon hoaDon = new HoaDon(
                        cursorHoaDon.getString(0),
                        cursorHoaDon.getString(3),
                        cursorHoaDon.getString(1),
                        cursorHoaDon.getString(2),
                        cursorHoaDon.getLong(4),
                        sanPhamList,
                        cursorHoaDon.getInt(5)
                );
                list.add(hoaDon);
            } while (cursorHoaDon.moveToNext());
            cursorHoaDon.close();
        }
        return list;
    }

    public int getCountHoaDon(){
        String selectQuery = "SELECT * FROM "+TABLE_HOADON;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateHoaDon(HoaDon hoaDon){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOADON_TRIGIA,hoaDon.getTriGia());
        values.put(COLUMN_HOADON_NGAYHD,hoaDon.getNgayHD());
        values.put(COLUMN_HOADON_MANV,hoaDon.getMaNV());
        values.put(COLUMN_HOADON_MAKH,hoaDon.getMaKH());
        values.put(COLUMN_HOADON_NO,hoaDon.getHoaDonNo());

        int i =  sqLiteDatabase.update(TABLE_HOADON,values,COLUMN_HOADON_SOHD+ " = ?",
                new String[]{hoaDon.getSoHD()});
        sqLiteDatabase.close();
        return i;
    }

    public void deleteHoaDon(HoaDon hoaDon){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_HOADON,COLUMN_HOADON_SOHD+" = ?",
                new String[]{hoaDon.getSoHD()});
        sqLiteDatabase.close();
    }

    // CRUD Khach Hang
    public void initKhachHang(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KHACHHANG_ID,"MaKH01");
        values.put(COLUMN_KHACHHANG_HOTEN,"Khách Lẻ");
        values.put(COLUMN_KHACHHANG_EMAIL,"");
        values.put(COLUMN_KHACHHANG_DIACHI,"");
        values.put(COLUMN_KHACHHANG_SODT,"");
        values.put(COLUMN_KHACHHANG_GHICHU,"");
        sqLiteDatabase.insert(TABLE_KHACHHANG,null,values);
        sqLiteDatabase.close();
    }

    public boolean addKhachHang(KhachHang khachHang){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KHACHHANG_ID,khachHang.getMaKH());
        values.put(COLUMN_KHACHHANG_HOTEN,khachHang.getTenKH());
        values.put(COLUMN_KHACHHANG_EMAIL,khachHang.getEmail());
        values.put(COLUMN_KHACHHANG_DIACHI,khachHang.getDiaChi());
        values.put(COLUMN_KHACHHANG_SODT,khachHang.getSoDT());
        values.put(COLUMN_KHACHHANG_GHICHU,khachHang.getGhiChu());
        long i = sqLiteDatabase.insert(TABLE_KHACHHANG,null,values);
        sqLiteDatabase.close();
        return i>0;
    }
    public KhachHang getKhachHang(String khachHangID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String [] stringColumns = {
                COLUMN_KHACHHANG_ID,
                COLUMN_KHACHHANG_HOTEN,
                COLUMN_KHACHHANG_EMAIL,
                COLUMN_KHACHHANG_SODT,
                COLUMN_KHACHHANG_DIACHI,
                COLUMN_KHACHHANG_GHICHU
        };
        Cursor cursor = sqLiteDatabase.query(TABLE_KHACHHANG,stringColumns,COLUMN_KHACHHANG_ID + "=?",
                new String[]{khachHangID},null,null,null);
        KhachHang khachHang = null;
        if(cursor.moveToFirst()) {
            khachHang = new KhachHang(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(4),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(5)
            );
            cursor.close();
        }
        return khachHang;
    }

    public List<KhachHang> getListKhachHang(){
        List<KhachHang> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_KHACHHANG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                KhachHang khachHang = new KhachHang(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                list.add(khachHang);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    public int getCountKhachHang(){
        String selectQuery = "SELECT * FROM "+TABLE_KHACHHANG;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public boolean updateKhachHang(KhachHang khachHang){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KHACHHANG_HOTEN,khachHang.getTenKH());
        values.put(COLUMN_KHACHHANG_EMAIL,khachHang.getEmail());
        values.put(COLUMN_KHACHHANG_DIACHI,khachHang.getDiaChi());
        values.put(COLUMN_KHACHHANG_SODT,khachHang.getSoDT());
        values.put(COLUMN_KHACHHANG_GHICHU,khachHang.getGhiChu());
        int i =  sqLiteDatabase.update(TABLE_KHACHHANG,values,COLUMN_KHACHHANG_ID+ " = ?",
                new String[]{khachHang.getMaKH()});
        sqLiteDatabase.close();
        return i>0;
    }

    public boolean deleteKhachHang(KhachHang khachHang){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = -1;
        if(!khachHang.getMaKH().equals("MaKH01")){
            i = sqLiteDatabase.delete(TABLE_KHACHHANG,COLUMN_KHACHHANG_ID+" = ?",
                    new String[]{khachHang.getMaKH()});
        }
        sqLiteDatabase.close();
        return i>0;
    }

    // CRUD Khach Hang
    public boolean initNhanVien(byte [] bytes){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHANVIEN_MANV,"MaNV01");
        values.put(COLUMN_NHANVIEN_HOTEN,"cuong");
        values.put(COLUMN_NHANVIEN_EMAIL,"ad@gmail.com");
        values.put(COLUMN_NHANVIEN_SODT,"");
        values.put(COLUMN_NHANVIEN_QUYEN,1);
        values.put(COLUMN_NHANVIEN_IMAGE,bytes);
        values.put(COLUMN_NHANVIEN_PASSWORD,"123");
        long a = sqLiteDatabase.insert(TABLE_NHANVIEN,null,values);
        sqLiteDatabase.close();
        return a>0;
    }

    public boolean addNhanVien(NhanVien nhanVien){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHANVIEN_MANV,nhanVien.getMaNV());
        values.put(COLUMN_NHANVIEN_HOTEN,nhanVien.getHoTenNV());
        values.put(COLUMN_NHANVIEN_EMAIL,nhanVien.getEmailNhanVien());
        values.put(COLUMN_NHANVIEN_SODT,nhanVien.getSoDT());
        values.put(COLUMN_NHANVIEN_PASSWORD,nhanVien.getPassNV());
        values.put(COLUMN_NHANVIEN_IMAGE,nhanVien.getByteImgs());
        long i = sqLiteDatabase.insert(TABLE_NHANVIEN,null,values);
        sqLiteDatabase.close();
        return i>0;
    }
    public NhanVien getNhanVien(String nhanVienID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String [] stringColumns = {
                COLUMN_NHANVIEN_MANV,
                COLUMN_NHANVIEN_HOTEN,
                COLUMN_NHANVIEN_EMAIL,
                COLUMN_NHANVIEN_SODT,
                COLUMN_NHANVIEN_QUYEN,
                COLUMN_NHANVIEN_PASSWORD,
                COLUMN_NHANVIEN_IMAGE
        };
        Cursor cursor = sqLiteDatabase.query(TABLE_NHANVIEN,stringColumns,COLUMN_NHANVIEN_MANV + " = ? ",
                new String[]{nhanVienID},null,null,null);
        NhanVien nhanVien = null;
        if(cursor.moveToFirst()) {
            nhanVien = new NhanVien(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
            );
            cursor.close();
        }
        return nhanVien;
    }
    public NhanVien getUser(String hoTenNhanVien){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String [] stringColumns = {
                COLUMN_NHANVIEN_MANV,
                COLUMN_NHANVIEN_HOTEN,
                COLUMN_NHANVIEN_EMAIL,
                COLUMN_NHANVIEN_SODT,
                COLUMN_NHANVIEN_QUYEN,
                COLUMN_NHANVIEN_PASSWORD,
                COLUMN_NHANVIEN_IMAGE
        };
        Cursor cursor = sqLiteDatabase.query(TABLE_NHANVIEN,stringColumns,COLUMN_NHANVIEN_HOTEN + " = ? ",
                new String[]{hoTenNhanVien},null,null,null);
        NhanVien nhanVien = null;
        if(cursor.moveToFirst()) {
            nhanVien = new NhanVien(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
            );
            cursor.close();
        }
        return nhanVien;
    }
    public NhanVien getUserEmail(String emailNhanVien){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String [] stringColumns = {
                COLUMN_NHANVIEN_MANV,
                COLUMN_NHANVIEN_HOTEN,
                COLUMN_NHANVIEN_EMAIL,
                COLUMN_NHANVIEN_SODT,
                COLUMN_NHANVIEN_QUYEN,
                COLUMN_NHANVIEN_PASSWORD,
                COLUMN_NHANVIEN_IMAGE
        };
        Cursor cursor = sqLiteDatabase.query(TABLE_NHANVIEN,stringColumns,COLUMN_NHANVIEN_EMAIL + " = ? ",
                new String[]{emailNhanVien},null,null,null);
        NhanVien nhanVien = null;
        if(cursor.moveToFirst()) {
            nhanVien = new NhanVien(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
            );
            cursor.close();
        }
        return nhanVien;
    }



    public List<NhanVien> getListNhanVien(){
        List<NhanVien> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_NHANVIEN;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                NhanVien nhanVien = new NhanVien(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getInt(4),
                        cursor.getString(6),
                        cursor.getBlob(5)
                );
                list.add(nhanVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    public int getCountNhanVien(){
        String selectQuery = "SELECT * FROM "+TABLE_NHANVIEN;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public boolean updateNhanVien(NhanVien nhanVien){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHANVIEN_HOTEN,nhanVien.getHoTenNV());
        values.put(COLUMN_NHANVIEN_PASSWORD,nhanVien.getPassNV());
        values.put(COLUMN_NHANVIEN_SODT,nhanVien.getSoDT());
        values.put(COLUMN_NHANVIEN_EMAIL,nhanVien.getEmailNhanVien());
        values.put(COLUMN_NHANVIEN_QUYEN,nhanVien.getQuyen());
        values.put(COLUMN_NHANVIEN_IMAGE,nhanVien.getByteImgs());

        int i =  sqLiteDatabase.update(TABLE_NHANVIEN,values,COLUMN_NHANVIEN_MANV+ " = ?",
                new String[]{nhanVien.getMaNV()});
        sqLiteDatabase.close();
        return i>0;
    }

    public boolean deleteNhanVien(NhanVien nhanVien){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = -1;
        if(!nhanVien.getMaNV().equals("MaNV01")){
            i = sqLiteDatabase.delete(TABLE_NHANVIEN,COLUMN_NHANVIEN_MANV+" = ?",
                    new String[]{nhanVien.getMaNV()});
        }
        sqLiteDatabase.close();
        return i>0;
    }

}
