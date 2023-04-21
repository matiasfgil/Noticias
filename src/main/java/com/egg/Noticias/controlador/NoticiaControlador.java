/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.Noticias.controlador;

import com.egg.Noticias.entidades.Noticia;
import com.egg.Noticias.excepciones.MiException;
import com.egg.Noticias.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author matia
 */
@Controller
@RequestMapping("/noticia")
public class NoticiaControlador { //localhost:8080/noticia

    @Autowired
    NoticiaServicio noticiaService;

    @GetMapping("/registrar")
    public String registrar() {
        return "noticia_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo) throws Exception {
        try {
            noticiaService.crearNoticia(titulo, cuerpo);
            modelo.put("exito", "La noticia se ha creado con exito");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "noticia_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Noticia> noticias = noticiaService.listarNoticias();
        modelo.addAttribute("noticias", noticias);

        return "noticia_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("noticia", noticiaService.getOne(id));

        return "noticia_modificar.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo) throws Exception {

        try {
            noticiaService.modificarNoticia(id, titulo, cuerpo);

            return "redirect:../lista";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "noticia_modificar.html";
        }
    }

    @GetMapping("/eliminar")
    public String eliminar(ModelMap modelo) {

        List<Noticia> noticias = noticiaService.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "noticia_delete.html";
    }

    @PostMapping("/eliminacion")
    public String eliminacion(@RequestParam String titulo, ModelMap modelo) {

        try {
            noticiaService.eliminarNoticia(titulo);
            modelo.put("exito", "La noticia se ha eliminado correctamente");

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "noticia_delete.html";

        }

        return "index.html";
    }

}
