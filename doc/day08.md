# Día 8: Circuitos por componentes conexas con Union-Find

## Enunciado

En el octavo día tenemos nodos en un espacio 3D, cada uno con coordenadas `(x, y, z)`. Las conexiones entre nodos tienen un peso: la distancia al cuadrado entre ellos. Los nodos se agrupan en circuitos (componentes conexas) al unir conexiones.

- **Parte A**: toma las conexiones más cortas (hasta un límite), une los nodos que conectan, y multiplica los tamaños de los 3 circuitos más grandes resultantes.
- **Parte B**: une conexiones de menor a mayor distancia hasta que todos los nodos formen un único circuito. Devuelve el producto de las coordenadas `x` de los dos nodos de la conexión que logró la unificación final.

## Algoritmos y técnicas

- **Algoritmo principal**: **Union-Find (Disjoint Set Union)** con **path compression** para gestionar componentes conexas dinámicas.
- **Generación de conexiones**: todos los pares de nodos con su distancia al cuadrado, ordenados ascendentemente.
- **Distancia al cuadrado (sin raíz)**: se compara distancia² en lugar de distancia, evitando `Math.sqrt` y manteniendo aritmética entera exacta.
- **Detección de unificación**: la parte B cuenta componentes restantes; cuando llega a 1, el grafo está conectado.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `CircuitNode` | Nodo 3D con coordenadas `(x, y, z)`. Calcula distancia al cuadrado a otro nodo |
| `Connection` | Conexión entre dos nodos (por índice) con su distancia al cuadrado. Comparable por distancia |
| `UnionFind` | Estructura Disjoint Set Union con path compression: `find` y `union` |
| `Playground` | Orquestador: genera conexiones, aplica Union-Find, calcula los resultados de ambas partes |
| `PartA`, `PartB` | Entry points con fluent API |

## Patrones de diseño aplicados

### Factory Method

`Playground.empty()` y `CircuitNode.parse(text)` son factory methods estáticos.

### Fluent API

`Playground` implementa fluent API: `empty().withNodesFrom(input).productOfTopCircuitSizes(...)`.

### Inmutabilidad

`CircuitNode` y `Connection` son records inmutables. `Playground` es final con campo `final`.


## Principios aplicados

- **SRP**: `CircuitNode` (geometría 3D), `Connection` (par + distancia), `UnionFind` (componentes conexas), `Playground` (orquestación). 
- **Encapsulación de mutabilidad**: `UnionFind` es mutable pero su mutabilidad no escapa: solo `find` y `union` son públicos, el array `parent` es privado.
- **Tell, Don't Ask**: `node.squaredDistanceTo(other)` calcula la distancia; el caller no extrae coordenadas. `unionFind.union(a, b)` une; el caller no manipula el array.

## Resultados

| Parte | Respuesta |
|---|---|
| A | 50568 |
| B | 36045012 |