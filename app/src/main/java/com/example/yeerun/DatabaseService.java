package com.example.yeerun;

import android.os.Environment;

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
        Rout r4 = new Rout("Zemunski kej", 6.4, "25:23");
        routes.add(r1);
        routes.add(r2);
        routes.add(r3);
        routes.add(r4);

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
