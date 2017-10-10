/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.awt.Event.INSERT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBATISExample {

/**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage(),e);
            }
        }
        return sqlSessionFactory;
    }

    /**

        

     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        try{
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="bdprueba";
                        
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(url,user,pwd);
            conn.setAutoCommit(false);
            
            
        
            Consulta con=new Consulta();
            Eps ep=new Eps();        
            Paciente pa= new Paciente(9999,"CC","Oscar", Date.valueOf("2016-12-12"),ep);
            SqlSessionFactory sessionfact = getSqlSessionFactory();
            SqlSession sqlss = sessionfact.openSession();
            PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
            List<Paciente> pacientes=pmapper.loadPacientes();

            registrarNuevoPaciente(pmapper, pa);
            conn.commit();
            
            /**
            //pmapper.insertarPaciente(pa);
            for (int i=0; i<pacientes.size(); i++){
                Paciente re=pacientes.get(i);
                System.out.print("miremos"+re.getNombre());
            }
             */
            }catch(Exception e){}


    }

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        
        Consulta co=new Consulta(Date.valueOf("2016-12-12"),"Esta con mamoneria",1234);
        pmap.insertarPaciente(p);
        
        pmap.insertConsulta(co, 9999, "CC", 90786);
        
        
    }
    
    
}
