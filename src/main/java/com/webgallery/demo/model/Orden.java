package com.webgallery.demo.model;


import javax.persistence.*;

@Entity
@Table(name = "ordenes")
public class Orden {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String fechaCompra;
	
	private String estado;
	
	private String precio;
	
	@ManyToOne
	@JoinColumn(name = "carrito_id", nullable = false)
	private Carrito carrito;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	
	
	
	
}
