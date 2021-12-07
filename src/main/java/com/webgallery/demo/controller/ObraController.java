package com.webgallery.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.webgallery.demo.model.Obra;
import com.webgallery.demo.model.Usuario;
import com.webgallery.demo.repository.ObraRepository;
import com.webgallery.demo.repository.UsuarioRepository;


@Controller
@RequestMapping("/api/obra")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class ObraController {
	@Autowired
	private ObraRepository RepositorioObra;
	private UsuarioRepository RepositorioUsuario;
	
	private static Logger LOG = LoggerFactory.getLogger(ObraController.class);
	
	@GetMapping("/listar")
	public ResponseEntity<List<Obra>>getObra(){
		List<Obra> listaObras = RepositorioObra.findAll();
		LOG.info("Funciona esta vaina");
		if(listaObras.isEmpty()) {
			LOG.warn("No hay datos");
			return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		}else {
			LOG.error("Si hay datos");
			return new ResponseEntity<>(listaObras, HttpStatus.OK);
		}
	}
	@PostMapping("/crear/{id}")
	public ResponseEntity<Obra> crearObra(@PathVariable("id") int id, @RequestBody Obra o){
		Optional<Usuario> datoUsuario = RepositorioUsuario.findById(id);
		if(datoUsuario.isPresent()) {
			try {
				LOG.info("Si se guarda");
				o.setAutor(datoUsuario.get());
				Obra obr = RepositorioObra.save(o);
				return new ResponseEntity<>(obr,HttpStatus.CREATED);
			}catch (Exception e) {
				System.out.println("No se guarda");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/editar/{id}")
	public ResponseEntity<Obra> actualizarObra(@PathVariable("id") int id, @RequestBody Obra o){
		Optional<Obra> datosObras = RepositorioObra.findById(id);
		if(datosObras.isPresent()) {
			Obra obr= datosObras.get();
			
			Usuario nuevoAutor = o.getAutor();
			String nuevoNombre = o.getNombre();
			String nuevaDescripcion = o.getDescripcion();
			String nuevoPrecio = o.getPrecio();
			String nuevaFoto = o.getFoto();
			String nuevoEstado = o.getEstado();
			String nuevaFechaCreacion = o.getFechaCreacion();
			
			obr.setAutor(nuevoAutor);
			obr.setNombre(nuevoNombre);
			obr.setDescripcion(nuevaDescripcion);
			obr.setPrecio(nuevoPrecio);
			obr.setFoto(nuevaFoto);
			obr.setEstado(nuevoEstado);
			obr.setFechaCreacion(nuevaFechaCreacion);
			
			Obra obraActualizada = RepositorioObra.save(obr);
			return new ResponseEntity<>(obraActualizada, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Obra> eliminarObra(@PathVariable("id") int id){
		try {
			RepositorioObra.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
