package com.webgallery.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.webgallery.demo.model.Obra;
import com.webgallery.demo.repository.ObraRepository;
import com.webgallery.demo.security.models.User;


@Controller
@RequestMapping("/api/obra")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
public class ObraController {
	@Autowired
	private ObraRepository RepositorioObra;
	/*@Autowired
	private OrdenRepository RepositorioOrden;
	@Autowired
	private UserRepository RepositorioUsuario;*/
	
	@GetMapping("/listar")
	public ResponseEntity<List<Obra>>getObra(){
		List<Obra> listaObras = RepositorioObra.findAll();
		if(listaObras.isEmpty()) {
			return new ResponseEntity<> (HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(listaObras, HttpStatus.OK);
		}
	}
	@PostMapping("/crear")
	public ResponseEntity<Obra> crearObra(@RequestBody Obra o){
		try {
			Obra obr = RepositorioObra.save(o);
			return new ResponseEntity<>(obr,HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/editar/{id}")
	public ResponseEntity<Obra> actualizarObra(@PathVariable("id") int id, @RequestBody Obra o){
		Optional<Obra> datosObras = RepositorioObra.findById(id);
		if(datosObras.isPresent()) {
			Obra obr= datosObras.get();
			User nuevoAutor = o.getAutor();
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
