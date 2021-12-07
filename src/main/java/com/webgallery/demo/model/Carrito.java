package com.webgallery.demo.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "carrito")
public class Carrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCarrito;

	@JoinColumn(name = "comprador_id")
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario comprador;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Obra> obras;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "carrito")
	private List<Orden> ordenes;

	public int getId() {
		return idCarrito;
	}

	public void setId(int idCarrito) {
		this.idCarrito = idCarrito;
	}

	public List<Obra> getObras() {
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}

	public List<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	public Carrito(Usuario comprador) {
		this.comprador = comprador;
		this.obras = null;
		this.ordenes = null;

	}

	public Carrito() {
		super();
	}

}
