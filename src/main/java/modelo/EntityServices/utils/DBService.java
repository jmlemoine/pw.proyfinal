package modelo.EntityServices.utils;

import org.h2.tools.Server;

import java.sql.SQLException;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.List;
=======
>>>>>>> parent of a2e04db... Update DBService
=======
>>>>>>> parent of a2e04db... Update DBService

public class DBService {

    private static DBService instance;

<<<<<<< HEAD
    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
=======
    public static DBService getInstancia() {
        if(instancia == null){
            instancia = new DBService();
>>>>>>> parent of a2e04db... Update DBService
        }
        return instance;
    }
    public void iniciarDn() {
        try {
            Server.createTcpServer("-tcpPort","9092","-tcpAllowOthers","-tcpDaemon").start();

        }
        catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());

        }
    }

    public void init(){
        iniciarDn();
    }

}
