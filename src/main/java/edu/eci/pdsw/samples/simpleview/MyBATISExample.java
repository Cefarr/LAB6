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

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

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
        
        //Consulta con=new Consulta();
        Eps ep=new Eps("SaludTotal", "8456986");        
        Paciente pa= new Paciente(21100089,"CC","ELERICK", Date.valueOf("2016-12-12"),ep);
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);

        registrarNuevoPaciente(pmapper, pa);
        
        //pmapper.insertarPaciente(pa);
        /**
        List<Paciente> pacientes=pmapper.loadPacientes();
        for (int i=0; i<pacientes.size(); i++){
            Paciente re=pacientes.get(i);
            System.out.print("miremos"+re.getNombre());
        }
        * */
        sqlss.commit();
         
                //conn.close();         
        }
    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        
        Consulta co=new Consulta(Date.valueOf("2016-12-12"),"Esta con mamoneria",1234);
        pmap.insertarPaciente(p);
        pmap.insertConsulta(co, p.getId(), co.getFechayHora(), p.getTipoId(), co.getResumen(), (int)co.getCosto());
        //pmap.insertConsulta(co, 9999, "CC", 90786);

        
    }
    
    
}
