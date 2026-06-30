# Día 7: Propagación de haz tachyónico a través de un diagrama

## Enunciado

En el séptimo día tenemos un diagrama de filas donde un haz tachyónico se propaga de arriba hacia abajo, empezando en una columna fuente marcada con `S`. Algunas posiciones contienen splitters (`^`) que dividen el haz: cuando el haz alcanza un splitter, se separa en dos haces hacia las columnas izquierda y derecha.

- **Parte A**: cuenta cuántas divisiones (splits) ocurren en total durante la propagación.
- **Parte B**: cuenta cuántos timelines (caminos distintos del haz) existen al final de la propagación.

## Algoritmos y técnicas

- **Algoritmo principal**: **BFS por niveles** (level-order BFS) donde cada fila del diagrama es un nivel. El frente de búsqueda son las columnas activas, que se expanden al encontrar splitters.
- **Set vs multiset según el objetivo**: la parte A usa un conjunto de columnas activas (cuenta eventos de split); la parte B usa un mapa columna → número de caminos (cuenta caminos totales).
- **Acumulación con `Map.merge`**: en la parte B, cuando varios caminos convergen en una misma columna se suman.
- **Inmutabilidad con copia (copy-on-write)**: cada paso de propagación produce un nuevo estado (`BeamState` o `Timelines`).

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `DiagramRow` | Una fila del diagrama. Sabe si hay un splitter en una columna, si una columna está dentro, qué vecinos genera un split, y dónde está la fuente |
| `BeamState` | Estado de propagación para la parte A: conjunto de columnas activas + contador de splits |
| `Timelines` | Estado de propagación para la parte B: mapa columna → número de caminos que pasan por ella |
| `TachyonManifold` | Orquestador: encuentra la fuente, propaga el estado a través de todas las filas |
| `PartA`, `PartB` | Entry points con fluent API |


## Patrones de diseño aplicados

### Factory Method

`TachyonManifold.empty()`, `BeamState.startingAt(col)` y `Timelines.startingAt(col)` son factory methods que crean estados iniciales con intención clara.

### Fluent API

`TachyonManifold` implementa fluent API: `empty().withDiagramFrom(input).countSplits()`.

### Inmutabilidad con copia

`BeamState` y `Timelines` son records inmutables. Cada propagación (`propagatedThrough`) devuelve un **nuevo** estado en lugar de mutar el actual. La simulación es una secuencia de estados inmutables.

### Constantes con nombres del dominio



## Principios aplicados

- **SRP**: `DiagramRow` (estructura de una fila), `BeamState` (estado parte A), `Timelines` (estado parte B), `TachyonManifold` (orquestación).
- **Tell, Don't Ask**: `DiagramRow.isSplitterAt(col)`, `splitNeighborsOf(col)`. Los estados preguntan a la fila en lugar de manipular su texto.
- **Inmutabilidad por defecto**: `DiagramRow`, `BeamState`, `Timelines` son records. Cada propagación regenera el estado.
- **Estructura de datos según el objetivo**: set para presencia (parte A), mapa con conteos para multiplicidad (parte B).


## Resultados

| Parte | Respuesta      |
|---|----------------|
| A | 1649           |
| B | 16937871060075 |