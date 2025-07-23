package com.assessment.carritocompras.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.carritocompras.Model.Productos;
import com.assessment.carritocompras.carrito.Service.CarritoService;

@RestController
@RequestMapping("/shoppingCart")
public class CarritoComprasController {
    private final CarritoService carritoService;


    public CarritoComprasController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    //Get all products endpoint
    @GetMapping("/allProducts")
    ResponseEntity<List<Productos>> getAllProductsFromCatalog(){
        List<Productos> result = carritoService.getAllProductsFromCatalog();
        return ResponseEntity.ok(result);
    }









}
