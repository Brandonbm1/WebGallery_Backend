package com.webgallery.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webgallery.demo.model.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer>{

}
