package modelo.EntityServices.utils;

import org.h2.tools.Server;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.List;


public class DBService {

    private static DBService instancia;


    public static DBService getInstancia() {
        if(instancia == null){
            instancia = new DBService();
        }
        return instancia;
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
