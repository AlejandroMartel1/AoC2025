# Día 12: Empaquetado 2D de regalos con backtracking y podas

## Enunciado

En el dudodécimo día tenemos un catálogo de formas de regalos (cada una descrita como una rejilla de `#` y `.`) y un conjunto de regiones rectangulares bajo árboles de Navidad. Cada región especifica cuántos regalos de cada forma deben caber en ella. Los regalos pueden rotarse y reflejarse, pero deben colocarse en celdas enteras del grid sin solaparse.

- **Parte A**: cuenta cuántas regiones pueden alojar todos los regalos que se les piden.

## Algoritmos y técnicas

- **Algoritmo principal**: **backtracking con tres podas independientes que reducen  el árbol de búsqueda.
- **Cota inferior por celdas reales**: si la suma de celdas requeridas excede la capacidad, imposible.
- **Cota superior por bounding rectangle**: si los rectángulos envolventes caben sin solaparse, la solución existe trivialmente.
- **Memoización de estados fallidos**: registrar estados del grid demostrados inviables.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `Position` | Coordenada (fila, columna) con traslación |
| `Shape` | Una forma con sus celdas. Genera sus orientaciones, calcula dimensiones y bounding area |
| `Region` | Especificación de una región: dimensiones y cantidades por forma. Calcula su capacidad |
| `Grid` | Tablero mutable de empaquetado: colocar, quitar, consultar, enumerar anchors, hashear contenido |
| `Packer` | Algoritmo de backtracking con podas y memoización |
| `ChristmasTreeFarm` | Orquestador: parsea formas y regiones, cuenta las que caben |
| `PartA` | Entry point con fluent API |


## Patrones de diseño aplicados

### Factory Method

`ChristmasTreeFarm.empty()`, `Shape.parse(rows)` y `Region.parse(line)` son factory methods estáticos.

### Fluent API

`ChristmasTreeFarm` implementa fluent API: `empty().loadedFrom(input).countRegionsThatFit()`.

### Inmutabilidad selectiva

`Position`, `Shape` y `Region` son records inmutables. `Grid` es mutable (para eficiencia del backtracking), con la mutabilidad encapsulada. `Packer` mantiene un registro de estados fallidos.

### Higher-Order Function

`sumOverPieces(region, measure)` recibe una `ToIntFunction<Shape>` para reutilizar el cálculo con distintas medidas (`Shape::size`, `Shape::boundingArea`).


## Principios aplicados

- **DRY + Higher-order**: `sumOverPieces(region, measure)` extrae la suma común de `totalCellsRequired` y `totalBoundingArea`, parametrizando la medida como `ToIntFunction`.
- **SRP**: `Shape` (geometría), `Region` (especificación), `Grid` (estado mutable), `Packer` (algoritmo), `ChristmasTreeFarm` (orquestación).
- **Encapsulación de mutabilidad**: `Grid` es mutable pero su `boolean[][]` no escapa; solo se accede vía operaciones del dominio.

## Resultados

| Parte | Respuesta |
|---|-----------|
| A | 454       |