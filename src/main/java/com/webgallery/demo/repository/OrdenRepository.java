package com.webgallery.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webgallery.demo.model.Orden;

public interface OrdenRepository extends JpaRepository<Orden,Integer>{

}
