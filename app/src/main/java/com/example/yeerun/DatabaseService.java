package com.example.yeerun;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class DatabaseService {
    private static DatabaseService databaseService;
    private DatabaseService(){
    }
    public static DatabaseService getDatabaseService(){
        if(databaseService==null){
            databaseService = new DatabaseService();
        }
        return databaseService;
    }

    public void makeFileWithRoutes(){
        List<Rout> routes = new LinkedList<>();
        Rout r1 = new Rout("Avala", 5.2, "20:35");
        Rout r2 = new Rout("Ada", 7.0, "24:54");
        Rout r3 = new Rout("Banjicka suma", 3.2, "13:25");
//        Rout r4 = new Rout("Zemunski kej", 6.4, "25:23");
//        Rout r5 = new Rout("Avala2", 10.0, "40:25");
//        Rout r6 = new Rout("Kosutnjak", 6.2, "20:32");
//        Rout r7 = new Rout("Ruzveltova", 3.2, "7:33");
//        Rout r8 = new Rout("Kalemegdan", 2.5, "6:42");
//        Rout r9 = new Rout("Kej", 9.3, "32:31");
//        Rout r10 = new Rout("Savamala", 4.2, "15:52");
        routes.add(r1);
        routes.add(r2);
        routes.add(r3);
//        routes.add(r4);
//        routes.add(r5);
//        routes.add(r6);
//        routes.add(r7);
//        routes.add(r8);
//        routes.add(r9);
//        routes.add(r10);


        try{

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "/" + "routs.out");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            for (Rout r:routes) {
                out.writeObject(r);
            }
            out.flush();
            out.close();
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addRoute(String name, double length, String time, LatLng start, LatLng end){
        Rout r = new Rout(name, length, time, start, end);
        LinkedList<Rout> routes = loadRoutes();
        routes.add(r);
        for (Rout k: routes) {
            System.out.println(k.getName() + k.getLength());
        }
        System.out.println("ADDED ROUTE");
        try{

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "/" + "routs.out");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            for (Rout p:routes) {
                out.writeObject(p);
            }
            out.flush();
            out.close();
            System.out.println("SUCCESS");
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public LinkedList<Rout> loadRoutes(){
        LinkedList<Rout> routes = new LinkedList<>();
        try{
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "/" + "routs.out");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try{
                while(true){
                    Rout r = (Rout)(in.readObject());
                    //System.out.println(r.getName());
                    routes.add(r);
                }
            } catch(Exception e){}
            in.close();
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        return routes;
    }
}
