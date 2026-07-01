# Día 2: Catálogo de IDs con detección de patrones

## Enunciado

El segundo día nos muestra un catálogo con rangos de IDs separados por comas (por ejemplo, `100-200,500-600`). Cada rango cubre un intervalo cerrado de enteros. Para cada ID del catálogo expandido, se comprueba si los dígitos cumplen una propiedad:

- **Parte A**: tenemos que un ID es **inválido** si tiene longitud par y se compone de dos mitades idénticas (`1212`, es decir, se forma con `12` repetido). Teniendo que sumar todos los IDs inválidos.
- **Parte B**: se considera un id con patrón si sus dígitos forman la repetición de algún sub-patrón (`123123` con patrón `123`, `1111` con patrón `1`). Teniendo que sumar todos los IDs con patrón.

La parte A es un caso particular de la parte B, donde el patrón debe tener exactamente la mitad de la longitud.

## Algoritmos y técnicas

- **Algoritmo principal**: verificación de patrones repetidos en cadenas mediante comparación de prefijo con sub-cadenas.
- **Higher-order programming**: `sumMatchingIds(LongPredicate)` parametriza y permite reutilizar el esqueleto de cálculo.
- **Streams sobre rangos expandidos**: `flatMapToLong(IdRange::ids)` convierte la lista de rangos en un stream de IDs sin materializar todos en memoria.
- **Aprovechamiento de divisores**: evita validaciones innecesarias; el algoritmo solo prueba longitudes de patrón que sean divisores exactos de la longitud total del ID.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `IdRange` | Rango cerrado `[first, last]` con expansión perezosa a `LongStream` y parsing del formato `"first-last"` |
| `GiftShopCatalog` | Fachada del catálogo, con las operaciones de suma de IDs inválidos y patronados |
| `PartA`, `PartB` | Entry points con fluent API |


## Patrones de diseño aplicados

### Factory Method

`GiftShopCatalog.empty()` y `IdRange.parse(text)` son factory methods estáticos que comunican intención (catálogo vacío, rango parseado de un texto) y encapsulan la lógica de creación.

### Fluent API

`GiftShopCatalog` implementa fluent API: `empty()` devuelve un catálogo vacío, `withRangesFrom(input)` devuelve un nuevo catálogo cargado. Las operaciones de consulta (`sumOfInvalidIds`, `sumOfPatternedIds`) no mutan estado.

### Inmutabilidad

`IdRange` es un record inmutable. `GiftShopCatalog` es una clase final con campo `final`.



## Principios aplicados

- **DRY + Higher-order**: `sumMatchingIds(LongPredicate)` extrae el esqueleto común y parametriza la diferencia como función. `matchesPatternOfLength(digits, k)` extrae la primitiva común de los dos predicados.
- **SRP**: `IdRange` modela un rango (datos + parsing + expansión a IDs). `GiftShopCatalog` modela el catálogo y las operaciones agregadas.
- **Tell, Don't Ask**: `IdRange.ids()` devuelve directamente los IDs; el caller no extrae `first` y `last` para iterar.


## Resultados

| Parte | Respuesta |
|---|---|
| A | 19219508902 |
| B | 27180728081 |