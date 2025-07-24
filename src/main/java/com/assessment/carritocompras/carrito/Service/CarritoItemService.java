package com.assessment.carritocompras.carrito.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assessment.carritocompras.Model.Carrito;
import com.assessment.carritocompras.Model.CarritoItem;
import com.assessment.carritocompras.Model.Productos;
import com.assessment.carritocompras.Repository.CarritoItemRepository;
import com.assessment.carritocompras.Repository.CarritoRepository;
import com.assessment.carritocompras.Repository.ProductosRepository;

@Service
public class CarritoItemService {
    private final CarritoItemRepository itemRepository;
    private final CarritoRepository carritoRepository;
    private final ProductosRepository productosRepository;

    public CarritoItemService(CarritoItemRepository itemRepository, CarritoRepository carritoRepository, ProductosRepository productosRepository){
        this.itemRepository=itemRepository;
        this.carritoRepository = carritoRepository;
        this.productosRepository = productosRepository;
    }
    
    public String addItemToCartById(Long cartId, CarritoItem itemBody) {
        if (itemBody.getProducto() == null) {
            throw new IllegalArgumentException("El campo 'producto' es nulo en el body recibido");
        }
        if (itemBody.getProducto().getId() == null) {
            throw new IllegalArgumentException("El campo 'id' de producto es nulo en el body recibido");
        }

        Carrito carrito = carritoRepository.findById(cartId).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado");
        }

        Productos producto = productosRepository.findById(itemBody.getProducto().getId()).orElse(null);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        CarritoItem item = new CarritoItem();
        item.setCarritoCompras(carrito);
        item.setProducto(producto);
        item.setCantidad(itemBody.getCantidad());
        item.setPrecioUnitario(producto.getPrecio());
        itemRepository.save(item);
        // Calcula el total y la cantidad de productos en el carrito
        List<CarritoItem> items = carritoRepository.findById(cartId).get().getCarritoItem();
        int totalProductos = items.stream().mapToInt(CarritoItem::getCantidad).sum();
        double total = items.stream().mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario()).sum();

        return "Se insertaron " + totalProductos + " elementos y el total es " + total;
    }

   

    //Obtener todos los elementos de un carrito, buscado por ID
    public List<CarritoItem> getCartAllItemsById(Long id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if (carrito.isPresent()){
            return carrito.get().getCarritoItem();
        }
        return new ArrayList<>();
    }


    //Borrar todos los elementos de un carrito, carrito buscado por id
    public String deleteAllItemsFromCartById(Long id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if (carrito.isPresent()){
            carrito.get().getCarritoItem().clear();
            carritoRepository.save(carrito.get());
            return "Shopping carg with ID: " + id + " is now empty";
        }else{
            return "Shopping cart with ID: " + id + "not found";
        }
    }


    //Obtener un elemento en específico del carrito id y id del elemento
    public CarritoItem getItemByCartAndProductId(Long carritoId,Long productId){
        Optional<Carrito> carrito = carritoRepository.findById(carritoId);
        if(carrito.isPresent()){
            return carrito.get().getCarritoItem().stream().filter(item ->item.getProducto().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product ID: " + productId + " nof found"));
        }else{
            throw new RuntimeException("Shopping cart with ID: " + carritoId + " not found");
        }
    }

    //Eliminar un elemento en específico del carrito id y id del elemento
    public String deleteItemByCartAndProductId(Long carritoId,Long productId){
        Optional<Carrito> carrito = carritoRepository.findById(carritoId);
        if(carrito.isPresent()){
            CarritoItem elementoEliminar = carrito.get().getCarritoItem().stream().filter(item->item.getProducto().getId().equals(productId)).findFirst().orElseThrow(()-> new RuntimeException("Product not found in shopping cart"));
            itemRepository.delete(elementoEliminar);
            return "Item eliminado del carrito";
        }else{
            throw new RuntimeException("Cart not found");
        }
    }


    

}
