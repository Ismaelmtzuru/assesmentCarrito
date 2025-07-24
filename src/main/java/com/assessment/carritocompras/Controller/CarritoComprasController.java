package com.assessment.carritocompras.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.carritocompras.Model.Carrito;
import com.assessment.carritocompras.Model.CarritoItem;
import com.assessment.carritocompras.Model.Productos;
import com.assessment.carritocompras.carrito.Service.CarritoItemService;
import com.assessment.carritocompras.carrito.Service.CarritoService;



@RestController
@RequestMapping("/shoppingCart")
public class CarritoComprasController {
    private final CarritoService carritoService;
    private final CarritoItemService itemService;

    public CarritoComprasController(CarritoService carritoService,CarritoItemService itemService){
        this.carritoService = carritoService;
        this.itemService = itemService;
    }

    //Get all products endpoint
    @GetMapping("/allProducts")
    ResponseEntity<List<Productos>> getAllProductsFromCatalog(){
        List<Productos> result = carritoService.getAllProductsFromCatalog();
        return ResponseEntity.ok(result);
    }


    //Get all carts with  items
    @GetMapping("/allCarts")
    public ResponseEntity<List<Carrito>> getAllCarts(){
        List<Carrito> result = carritoService.getAllCarts();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cart")
    public ResponseEntity<Carrito> createCart(){
        Carrito newCarrito = carritoService.createNewCart();
        return ResponseEntity.ok(newCarrito);
        
    }

    //Delete cart by id
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartById(@PathVariable Long id){
        String result = carritoService.deleteCartById(id);
        if (result.contains("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart not exist");
        }
        return ResponseEntity.ok(result);
    }

    //Get carts with items
    @GetMapping("/cart/{id}")
    public ResponseEntity<Object> getCart(@PathVariable Long id){
    Object result = carritoService.getCartById(id); 
    
    if (result instanceof String && result.equals("Cart not found")){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart not exist");
    }
    
    return ResponseEntity.ok(result); 
}


    //Empty car, not delete cart
    @DeleteMapping("/carItems/{id}")
    public ResponseEntity<String> emptyCartItemsById(@PathVariable Long id){
        String result = carritoService.deleteAllItemsFromCartById(id);
        if(result.contains("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping Cart does not exists");
        }
        return ResponseEntity.ok(result);
    }


    //Obtener un elemento de un carrito de compras
    @GetMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<Object> getCartItemById(@PathVariable Long cartId, @PathVariable Long productId){
        try{
            CarritoItem item = itemService.getItemByCartAndProductId(cartId, productId);
            return ResponseEntity.ok(item);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Eliminar un item de un carrito
    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteItemInCartById(@PathVariable Long cartId, @PathVariable Long productId){
        try{
            String result = itemService.deleteItemByCartAndProductId(cartId, productId);
            return ResponseEntity.ok(result);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Añadir item a un carrito
    @PostMapping("/cart/{cartId}/item")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId, @RequestBody CarritoItem itemBody){
        try {
            String newItem = itemService.addItemToCartById(cartId, itemBody);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

}
