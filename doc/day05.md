# Día 5: Base de datos con fusión de rangos

## Enunciado

El quinto día nos presenta una base de datos con rangos de IDs "frescos" (por ejemplo, `10-20`, `15-30`) y, opcionalmente, una lista de IDs concretos a comprobar. Los rangos pueden solaparse entre sí.

- **Parte A**: dada una lista de IDs concretos, cuenta cuántos caen dentro de algún rango fresco.
- **Parte B**: cuenta cuántos IDs únicos cubren todos los rangos frescos en total, teniendo en cuenta que los rangos solapados o adyacentes no deben contar duplicados.

## Algoritmos y técnicas

- **Algoritmo  (Parte B)**: **fusión de intervalos** (ordenar por inicio y recorre fusionando solapados o adyacentes).
- **Algoritmo (Parte A)**: para cada ID, comprobar si pertenece a algún rango con `anyMatch`.
- **Detección de solapamiento y adyacencia**: dos rangos se fusionan si solapan o si son contiguos en enteros (sin hueco entre ellos).

## Modelado en clases

| Clase            | Responsabilidad                                                                                                              |
|------------------|------------------------------------------------------------------------------------------------------------------------------|
| `FreshRange`     | Rango cerrado `[first, last]` con operaciones del dominio: contención, tamaño, detección de solapamiento/adyacencia y fusión |
| `MergedRanges`   | Algoritmo de fusión de rangos solapados; calcula el tamaño total tras fusionar                                               |
| `RangeAnalyzer`  | Orquestador: parsea rangos e IDs, que responde a las dos partes                                                              |
| `PartA`, `PartB` | Entry points con fluent API                                                                                                  |


## Patrones de diseño aplicados

### Factory Method

`RangeAnalyzer.empty()`, `MergedRanges.from(ranges)` y `FreshRange.parse(text)` son factory methods estáticos que comunican intención y encapsulan la lógica de creación.

### Fluent API

`RangeAnalyzer` implementa fluent API: `empty().loadedFrom(input).countAllFreshIds()`.

### Inmutabilidad

`FreshRange` es un record inmutable. `MergedRanges` e `RangeAnalyzer` son finales con campos `final`. La fusión produce nuevas instancias.

## Principios aplicados

- **SRP**: `FreshRange` (opera sobre los rangos), `MergedRanges` (fusión de rangos), `RangeAnalyzer` (orquestador y parsing).
- **DRY**: `overlapsOrAdjacentTo` y `mergedWith` se definen una vez en `FreshRange` y se reutilizan en el algoritmo de fusión. La lógica de "qué es solapar" vive en un solo sitio.
- **Tell, Don't Ask**: `range.contains(id)`, `current.mergedWith(next)`, `current.overlapsOrAdjacentTo(next)`. El caller no extrae `first` y `last` para comparar manualmente; le pide al rango que opere.

## Resultados

| Parte | Respuesta |
|---|---|
| A | 643 |
| B | 342018167474526 |