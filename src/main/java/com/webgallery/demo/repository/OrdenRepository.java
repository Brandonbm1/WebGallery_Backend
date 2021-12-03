package com.webgallery.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webgallery.demo.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden,Integer>{

}
