package com.example.app_bancaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class Model {
    public SQLiteDatabase conexion(Context context){
        Database conexion = new Database(context,"bank",null,1);
        SQLiteDatabase basededatos = conexion.getWritableDatabase();
        return basededatos;
    }

    boolean insert(Context context, Campos datos){
        boolean insert = false;
        ContentValues content = new ContentValues();
        content.put("nombre",datos.getNombre());
        content.put("apellido",datos.getApellido());
        content.put("identificacion",datos.getIdentificacion());
        content.put("correo",datos.getCorreo());
        content.put("contrasena",datos.getContrasena());
        content.put("cuenta",datos.getCuenta());
        content.put("monto",datos.getMonto());
        content.put("estado",datos.getEstado());
        SQLiteDatabase basededatos = this.conexion(context);
        try{
            basededatos.insert("cliente",null, content);
            insert=true;
        }catch (Exception e){
            Log.e(null,e.toString());
        }
        return  insert;
    }

    Bundle readlogin2(Context context, String correo, String contrasena){
        Bundle campos = new Bundle();
        campos.putBoolean("rsql",false);
        SQLiteDatabase basededatos = this.conexion(context);
        Cursor resultado = basededatos.query("cliente",new String[]{"*"},
                "correo='"+correo+"' AND contrasena='"+contrasena+"'",
                null,null,null,null);
        if(resultado.moveToFirst()){
            campos.putBoolean("rsql",true);
            campos.putString("nombre",resultado.getString(1));
            campos.putString("apellido",resultado.getString(2));
            campos.putString("identificacion",resultado.getString(3));
            campos.putString("correo",resultado.getString(4));
            campos.putString("contrasena",resultado.getString(5));
            campos.putString("cuenta",resultado.getString(6));
            campos.putDouble("monto",resultado.getDouble(7));
            campos.putString("estado",resultado.getString(8));
        }
        return campos;
    }

    Bundle consignar(Context context, Bundle bundle, Double monto){
        bundle.putBoolean("rsql",false);
        Double consignacion = (bundle.getDouble("monto") + monto)-2000;
        bundle.putDouble("monto",consignacion);
        SQLiteDatabase basededatos = this.conexion(context);
        try{
            Cursor resultado = basededatos.rawQuery("UPDATE cliente SET monto="+bundle.getDouble("monto")+" WHERE cuenta=?",new String[]{bundle.getString("cuenta")});
            resultado.moveToFirst();
            if(resultado!=null) {
                bundle.putBoolean("rsql", true);
            }
        }catch(Exception e){
            Log.e(null,e.toString());
        }
        return bundle;
    }

    Bundle retirar(Context context, Bundle bundle, Double monto){
        bundle.putBoolean("rsql",false);
        Double consignacion = (bundle.getDouble("monto") - monto)-5000;
        bundle.putDouble("monto",consignacion);
        SQLiteDatabase basededatos = this.conexion(context);
        try{
            Cursor resultado = basededatos.rawQuery("UPDATE cliente SET monto="+bundle.getDouble("monto")+" WHERE cuenta=?",new String[]{bundle.getString("cuenta")});
            resultado.moveToFirst();
            if(resultado!=null) {
                bundle.putBoolean("rsql", true);
            }
        }catch(Exception e){
            Log.e(null,e.toString());
        }
        return bundle;
    }

    boolean validarcuenta(Context context, String cuenta){
        boolean  v = false;
        SQLiteDatabase basededatos = this.conexion(context);
        Cursor valcuenta =  basededatos.rawQuery("SELECT * FROM cliente WHERE cuenta=?", new String[]{cuenta});
        valcuenta.moveToFirst();
        if(valcuenta.getCount()>0){
            v= true;
        }
        return v;
    }

    Bundle enviardinero(Context context, Bundle bundle, String cuenta, Double monto){
        bundle.putBoolean("rsql",false);
        SQLiteDatabase basededatos = this.conexion(context);
        Cursor saldocuentaenvio =  basededatos.rawQuery("SELECT monto FROM cliente WHERE cuenta=?", new String[]{cuenta});
        if(saldocuentaenvio.moveToFirst()) {
            Double nuevosaldo = saldocuentaenvio.getDouble(0) + monto;
            try {
                Cursor resultado = basededatos.rawQuery("UPDATE cliente SET monto=" + nuevosaldo + " WHERE cuenta=?", new String[]{cuenta});
                resultado.moveToFirst();
                if (resultado != null) {
                    bundle.putBoolean("rsql", true);
                    bundle = retirar(context, bundle, (monto)-3000);
                }
            } catch (Exception e) {
                Log.e(null, e.toString());
            }
        }
        return bundle;
    }

    Cursor usuarios(Context context){
        SQLiteDatabase basededatos = this.conexion(context);
        Cursor user = basededatos.rawQuery("SELECT * FROM cliente", null);
        return  user;
    }
    boolean verestado(Context context, String correo){
        boolean v;
        SQLiteDatabase basededatos = this.conexion(context);
        Cursor user = basededatos.rawQuery("SELECT estado FROM cliente WHERE correo=?", new String[]{correo});
        user.moveToFirst();
        if(user.getString(user.getColumnIndex("estado")).equals("habilitado")){
            v=true;
        }else{
            v=false;
        }
        return v;
    }

    boolean deshabilitarusuario(Context context, String correo){
        boolean v = false;
        SQLiteDatabase basededatos = this.conexion(context);
        try{
            Cursor resultado = basededatos.rawQuery("UPDATE cliente SET estado='deshabilitado' WHERE correo=?", new String[]{correo});
            resultado.moveToFirst();
            if(resultado != null){
                v=true;
            }
        }catch (Exception e){
            Log.e(null, e.toString());
        }
        return v;
    }

    boolean habilitarusuario(Context context, String correo){
        boolean v = false;
        SQLiteDatabase basededatos = this.conexion(context);
        try{
            Cursor resultado = basededatos.rawQuery("UPDATE cliente SET estado='habilitado' WHERE correo=?", new String[]{correo});
            resultado.moveToFirst();
            if(resultado != null){
                v=true;
            }
        }catch (Exception e){
            Log.e(null, e.toString());
        }
        return v;
    }
}
