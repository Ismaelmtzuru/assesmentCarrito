package com.assessment.carritocompras.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="car_item")
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="carrito_id")
    @NotNull(message = "Cart is required")    
    @JsonIgnore
    private Carrito carritoCompras;

    @ManyToOne
    @JoinColumn(name="product_id")
    @NotNull(message = "Product is required")
    private Productos producto;

    @Column(name="quantity")
    @Positive(message = "Quantity must be positive")
    @NotNull(message = "Quantity must be specified")
    private Integer cantidad;

    @Column(name = "unit_price")
    private double precioUnitario;


    @PrePersist
    @PreUpdate
    public void actualizarPrecio(){
        if(producto != null){
            this.precioUnitario = producto.getPrecio();
        }
    }
}
