🛒 Carrito de Compras – Spring Boot API
<br>

Este proyecto es una API RESTful desarrollada con Spring Boot para gestionar carritos de compras. Está conectada a una base de datos relacional y permite operaciones CRUD sobre carritos, productos y los ítems del carrito.
<br>
<br>

🚀 Características principales
<br>
<br>
CRUD completo para:
Carritos
Productos
Ítems del carrito
Persistencia en bases de datos relacionales (MySQL, PostgreSQL, H2, etc.)
<br>
<br>
Relaciones:
- Un carrito puede contener múltiples ítems
- Cada ítem está asociado a un producto
- Validaciones de datos y manejo de errores
- Serialización JSON optimizada (evita recursión infinita)
- Documentación de endpoints con Swagger / OpenAPI
<br>
<br>

🧱 Estructura de entidades
<br>
📦 Carrito (Carrito)
<br>

* Tabla: shopping_cart
* Campo: cartID
* Relación: @OneToMany con CarritoItem
<br>
<br>

🛍️ Producto (Producto)
<br>
Tabla: products
<br>
Campos:
* id
* nombre (product_name)
* precio (product_price)
<br>
<br>

🧾 Ítem del Carrito (CarritoItem)
<br>
Tabla: car_item
<br>
Campos:
* id
* carrito_id
* product_id
* cantidad
* precioUnitario
<br>
<br>

Relaciones:

@ManyToOne con Carrito
<br>

@ManyToOne con Producto
<br>
<br>

🔗 Endpoints principales
📂 Carrito
<br>

GET /shoppingCart/allCarts
Obtener todos los carritos
<br>

POST /shoppingCart/cart
Crear un nuevo carrito vacío
<br>

GET /shoppingCart/cart/{id}
Obtener un carrito por ID
<br>

DELETE /shoppingCart/cart/{id}
Eliminar un carrito por ID
<br>
<br>
<br>

🧾 Ítems del Carrito
<br>

POST /shoppingCart/cart/{cartId}/item
Agregar un producto al carrito
<br>

Body ejemplo:

json
```
{
  "producto": { "id": 15 },
  "cantidad": 2
}
```
<br>

GET /shoppingCart/cart/{cartId}/product/{productId}
Obtener un ítem específico por ID de carrito y producto
<br>

DELETE /shoppingCart/carItems/{cartId}
Vaciar todos los ítems de un carrito
<br>

DELETE /shoppingCart/cart/{cartId}/product/{productId}
Eliminar un ítem específico de un carrito
<br>
<br>

📦 Productos
<br>

GET /shoppingCart/allProducts
Obtener todos los productos disponibles
<br>
<br>

🔁 Serialización JSON
Para evitar la recursión infinita en las relaciones bidireccionales:
<br>

@JsonManagedReference en la lista de ítems (Carrito)
<br>

@JsonBackReference en la referencia al carrito (CarritoItem)
<br>
<br>

✅ Ejemplo de respuesta (al agregar ítem)
```
HTTP/1.1 201 Created
Content-Type: application/json

{
  "mensaje": "Se insertaron 2 elementos y el total es $500"
}
```
<br>
<br>

⚙️ Requisitos
Java 17 o superior

Maven

Base de datos relacional (configurada en application.properties)
<br>
<br>

▶️ Ejecución
Clona el repositorio
<br>

Configura la conexión a la base de datos en src/main/resources/application.properties
<br>
Establecer lo siguiente antes de ejecutar por primer vez y ya que esté creada la base de datos en mysql (carritocompras)
```
spring.sql.init.mode=always 
```
<br>
Ya una vez que se corre la aplicación por primera vez, se establece en application.properties lo siguiente
```
spring.sql.init.mode=never
```

Ejecuta la aplicación con Maven o desde tu IDE
<br>
<br>

Accede a Swagger UI en:

http://localhost:8080/swagger-ui.html

o http://localhost:8080/swagger-ui/index.html
<br>
<br>


📌 Notas adicionales
Asegúrate de tener productos y carritos creados antes de insertar ítems

El campo producto debe enviarse como objeto con campo id en el JSON

El manejo de errores proporciona mensajes claros ante entradas inválidas

👨‍💻 Autor
Ismael Martínez Urueta, HPEL Software Developer Engineer.