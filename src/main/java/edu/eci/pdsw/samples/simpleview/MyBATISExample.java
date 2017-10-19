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
import java.util.ArrayList;
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
    public static void main(String args[]) throws SQLException, Exception {
   
        Eps ep=new Eps("SaludTotal", "8456986");        
        Paciente pa= new Paciente(7,"CC","ELERICK1", Date.valueOf("2016-12-12"),ep);
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        registrarNuevoPaciente(pmapper, pa);
        actualizarPaciente(pmapper, pa);
        sqlss.commit();
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
     
    }
    /**
     * @obj Actualizar los datos basicos del paciente, con sus respectivas consultas.
     * @pre EL paciente p ya existe
     * @param pmap mapper a traves del cial se hara la operacion.
     * @param p Paciente ya registrado
     */
    
    public static void actualizarPaciente(PacienteMapper pmap, Paciente p) throws Exception{
        try{

            List<Consulta> con = new ArrayList<Consulta>();
            con.add(new Consulta(Date.valueOf("2001-01-01"),"fRACTURA PENE",9878787));            
            con.add(new Consulta(Date.valueOf("2999-05-25"),"fRACTURA hueso",9878787));                        
            Eps ep=new Eps("Medimas","8456985");
            Paciente prueb=p;
            prueb.setNombre("El ÑERO ERICK");
            prueb.setFechaNacimiento(Date.valueOf("2001-01-01"));
            prueb.setEps(ep);
            pmap.actualizarPaciente(prueb);
            for (int i=0; i< con.size();i++){
                Consulta o=con.get(i);
                if(o.getId()==0){  
                    pmap.insertConsulta(o, p.getId(), o.getFechayHora(), p.getTipoId(), o.getResumen(), (int)o.getCosto());
                }
            }    
        }catch(Exception e){
            throw new Exception("PACIENTE NO REGISTRADO");
        }
    }
    
}
