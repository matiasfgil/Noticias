/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.Noticias.servicios;

import com.egg.Noticias.entidades.Noticia;
import com.egg.Noticias.excepciones.MiException;
import com.egg.Noticias.repositorios.NoticiasRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author matia
 */
@Service
public class NoticiaServicio {
    

    @Autowired
    private NoticiasRepositorio noticiasRepositorio;
    
    @Transactional
    public void crearNoticia (String titulo, String cuerpo) throws MiException, Exception {
        validarAtributos(titulo, cuerpo);
        
        Noticia noticia = new Noticia();
        
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        
        noticiasRepositorio.save(noticia);
        
    }
    
    public List<Noticia> listarNoticias(){
        List<Noticia> noticias = new ArrayList(); 
        noticias = noticiasRepositorio.findAll();
        return noticias;
    }
    
   
    
    @Transactional
    public void modificarNoticia(String id, String titulo, String cuerpo) throws MiException, Exception{
        
        validarAtributos(titulo, cuerpo);
        
        Optional<Noticia> respuesta = noticiasRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            
            noticiasRepositorio.save(noticia);
        }else{
            throw new MiException("No se encontro la noticia");
        }
        
    
    }
    
    public Noticia getOne(String id){
        return noticiasRepositorio.getOne(id);
        
    }
    
    public void eliminarNoticia(String titulo) throws MiException{
        Noticia noticiaEncontrada = noticiasRepositorio.buscarPorTitulo(titulo);
        validarAtributos(noticiaEncontrada.getTitulo(), noticiaEncontrada.getCuerpo());
        noticiasRepositorio.delete(noticiaEncontrada);
        
    }
    private void validarAtributos (String titulo , String cuerpo) throws MiException{
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacio");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
             throw new MiException("El cuerpo no puede estar vacio");
        }
    }
}
