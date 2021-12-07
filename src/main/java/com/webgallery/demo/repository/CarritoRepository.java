package com.webgallery.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.webgallery.demo.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Integer>{

}
