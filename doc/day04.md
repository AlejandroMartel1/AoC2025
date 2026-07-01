# Día 4: Compactador de basura con eliminación por capas

## Enunciado

En el cuarto día se tiene una rejilla de papel donde algunas celdas contienen rolls de basura (`@`) y otras están vacías (`.`). Un roll es **accesible** si tiene menos de 4 vecinos (de los 8 adyacentes) que también sean rolls.

- **Parte A**: cuenta cuántos rolls son accesibles en el estado inicial.
- **Parte B**: elimina repetidamente todos los rolls accesibles, capa a capa. Cada eliminación puede dejar accesibles rolls que antes estaban rodeados. Cuenta el total de rolls eliminados hasta que no quede ninguno accesible.

## Algoritmos y técnicas

- **Algoritmo principal**: **poda iterativa** hasta punto fijo (k-core decomposition con k = 4).
- **Iteración hasta punto fijo**: la parte B repite el proceso de eliminación mientras haya rolls accesibles, deteniéndose cuando el estado se estabiliza.
- **Transformación inmutable (copy-on-write)**: `PaperGrid` es un record inmutable. El método `withAllAt(positions, value)` no
modifica la rejilla actual: crea una copia con los cambios aplicados y devuelve
un nuevo `PaperGrid`. El original sigue intacto.
## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `Position` | Coordenada (fila, columna) con generación de sus 8 vecinos (vecindario de Moore) |
| `PaperGrid` | Rejilla inmutable de caracteres con consultas de contención (`isInside`), acceso (`at`), iteración (`allPositions`) y regeneración (`withAllAt`) |
| `TrashCompactor` | Orquestador con la lógica de accesibilidad y la simulación de eliminación por capas |
| `PartA`, `PartB` | Entry points con fluent API |


## Patrones de diseño aplicados

### Factory Method

`TrashCompactor.empty()` es un factory method que crea un compactador con una rejilla vacía. `withLayoutFrom(input)` parsea el input y produce un compactador cargado.

### Fluent API

`TrashCompactor` implementa fluent API: `empty().withLayoutFrom(input).countAccessibleRolls()`. Las operaciones de consulta no mutan estado.

### Inmutabilidad

`Position` y `PaperGrid` son records inmutables. El constructor compacto de `PaperGrid` aplica `List.copyOf` a las filas recibidas, garantizando que la lista interna no pueda modificarse desde fuera aunque el caller mantenga una referencia a la original. El método `withAllAt(positions, value)` no modifica la rejilla actual: genera una **nueva** con los cambios aplicados.


## Principios aplicados

- **SRP**: `Position` modela coordenadas y vecindario; `PaperGrid` modela la rejilla y sus operaciones; `TrashCompactor` ejecuta la lógica de accesibilidad y simulación. Cada clase cambia por motivos distintos.
- **Tell, Don't Ask**: `Position.neighbors()` produce los vecinos; el compactador no hace aritmética de offsets. `PaperGrid.isInside(p)` y `at(p)` encapsulan el acceso.

## Resultados

| Parte | Respuesta |
|---|---|
| A | 1349 |
| B | 8277 |