# Día 9: Geometría computacional con ray casting y prefix sum 2D

## Enunciado

En el noveno día se tiene un conjunto de baldosas rojas (`RedTile`) con coordenadas `(x, y)`. Estas baldosas forman los vértices de un polígono.

- **Parte A**: encuentra el área del rectángulo más grande que se puede formar usando dos baldosas como esquinas opuestas.
- **Parte B**: encuentra el área del rectángulo más grande cuyas dos esquinas son baldosas **y que está completamente contenido dentro del polígono** formado por todas las baldosas.

## Algoritmos y técnicas

- **Algoritmo principal**: **regla par-impar** (even-odd rule) para determinar interior del polígono, **tabla de sumas acumuladas 2D** para consultar el área, y **compresión de coordenadas** para reducir el grid a las posiciones relevantes.
- **Compresión de coordenadas**: solo importan las coordenadas donde aparecen vértices, reduciendo el grid.
- **Regla par-impar**: desde el centro de cada celda, se lanza un rayo horizontal hacia la derecha y se cuentan los cruces con segmentos verticales del polígono. Si el número de cruces es impar, el punto está dentro; si es par, fuera.
- **Tabla de sumas acumuladas 2D**: precomputa sumas acumuladas para responder cuántas celdas interiores hay en un rectángulo.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `RedTile` | Baldosa con coordenadas `(x, y)`. Calcula el área del rectángulo formado con otra baldosa |
| `VerticalSegment` | Segmento vertical del polígono. Sabe si cruza un rayo horizontal hacia la derecha (ray casting) |
| `PolygonGrid` | Cuadrícula comprimida con tabla de prefix sum. Consulta si un rectángulo está dentro del polígono |
| `MovieTheater` | Orquestador: colección de baldosas, cálculos de rectángulo máximo |
| `PartA`, `PartB` | Entry points con fluent API |

**4 clases de dominio + 2 entry points**.

## Patrones de diseño aplicados

### Factory Method

`MovieTheater.empty()`, `RedTile.parse(text)` y `VerticalSegment.extractFrom(vertices)` son factory methods estáticos.

### Fluent API

`MovieTheater` implementa fluent API: `empty().withRedTilesFrom(input).largestGreenRectangleArea()`.

### Inmutabilidad

`RedTile` y `VerticalSegment` son records inmutables. `PolygonGrid` y `MovieTheater` son finales. La tabla de prefix sum se construye una vez en el constructor y nunca se muta.

## Principios aplicados

- **DRY**: `rectangleSumOver` se define una vez y se usa tanto en `isFullRectangleInside` como en `isCellInside`. La fórmula de inclusión-exclusión está en un solo sitio.
- **SRP**: `RedTile` (baldosa), `VerticalSegment` (segmento con ray casting), `PolygonGrid` (compresión + prefix sum + consultas), `MovieTheater` (orquestación).
- **Tell, Don't Ask**: `segment.crossesRightRayFrom(cx, cy)`, `tile.rectangleAreaWith(other)`, le pide las coordenadas al objeto.


## Resultados

| Parte | Respuesta |
|---|---|
| A | 4754955192 |
| B | 1568849600 |