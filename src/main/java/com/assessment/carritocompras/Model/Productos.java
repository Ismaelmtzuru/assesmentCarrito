package com.assessment.carritocompras.Model;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Productos {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //product name field
    @NotBlank(message = "Product Name Should be specified")
    @Size(min=3,max=30, message="The product name # of characters has to be between 3 to 30 chars.")
    @Column(name="product_name", length=30)
    private String nombre;
    //price field
    @Positive(message = "Price must be positive")
    @Column(name="product_price")
    @NumberFormat(style= NumberFormat.Style.CURRENCY, pattern="#,##0.00")
    private Double precio;

}
