package com.webgallery.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.webgallery.demo.model.Carrito;
import com.webgallery.demo.model.Usuario;
import com.webgallery.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/api/usuario")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:3000" })
public class UsuarioController {
	@Autowired
	private UsuarioRepository RepositorioUsuario;

	private static Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		List<Usuario> listaUsuarios = RepositorioUsuario.findAll();
		if (!listaUsuarios.isEmpty()) {
			return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario user) {
		
		try {
			Carrito carrito = new Carrito(user);
			user.setCarrito(carrito);
			Usuario usuario = RepositorioUsuario.save(user);
			return new ResponseEntity<>(usuario, HttpStatus.CREATED);

		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") int id, @RequestBody Usuario u) {
		Optional<Usuario> datoUsuario = RepositorioUsuario.findById(id);
		if (datoUsuario.isPresent()) {
			Usuario user = datoUsuario.get();

			String nuevoNombre = u.getNombres();
			String nuevoUsername = u.getUsername();
			String nuevoEmail = u.getEmail();
			String nuevaPass = u.getPassword();
			String nuevoDoc = u.getDocumento();
			String nuevaEdad = u.getEdad();
			String nuevoTelefono = u.getTelefono();
			String nuevaDireccion = u.getDireccion();
			String nuevaFoto = u.getFoto();
			String nuevaDescripcion = u.getDescripcion();

			user.setNombres(nuevoNombre);
			user.setUsername(nuevoUsername);
			user.setEmail(nuevoEmail);
			user.setPassword(nuevaPass);
			user.setDocumento(nuevoDoc);
			user.setEdad(nuevaEdad);
			user.setTelefono(nuevoTelefono);
			user.setDireccion(nuevaDireccion);
			user.setFoto(nuevaFoto);
			user.setDescripcion(nuevaDescripcion);

			Usuario userActualizado = RepositorioUsuario.save(user);
			return new ResponseEntity<>(userActualizado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Usuario> eliminarObra(@PathVariable("id") int id) {
		try {
			RepositorioUsuario.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
