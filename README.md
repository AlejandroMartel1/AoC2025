# Advent of Code 2025

Este repositorio plantea una posible forma de resolver los problemas planteados en el [Advent of Code 2025](https://adventofcode.com/2025), desarrollado aplicando los conocimientos adquiridos en la asignatura de Ingeniería de Software II.

## Documentación por día

Cada enlace lleva a una explicación detallada del problema, los algoritmos y técnicas aplicados, el modelado en clases, los principios aplicados y resultados obtenidos.

| Día | Problema | Algoritmo principal                             |
|---|---|-------------------------------------------------|
| [1](doc/day01.md) | Dial circular con rotaciones | Aritmética modular                    |
| [2](doc/day02.md) | Catálogo de IDs con detección de patrones | Búsqueda de patrones repetidos                  |
| [3](doc/day03.md) | Selección de dígitos para máxima joltage | Stack monótono decreciente + greedy             |
| [4](doc/day04.md) | Compactador de basura por capas | Poda iterativa                                  |
| [5](doc/day05.md) | Base de datos de ingredientes | Fusión de rangos + greedy                       |
| [6](doc/day06.md) | Hoja de problemas matemáticos | Strategy pattern + enum con comportamiento      |
| [7](doc/day07.md) | Propagación de haz tachyónico | BFS (level-order)                               |
| [8](doc/day08.md) | Circuitos por componentes conexas | Union-Find                                      |
| [9](doc/day09.md) | Geometría con ray casting | Regla par-impar + compresión                    |
| [10](doc/day10.md) | Configuración de máquinas | Fuerza bruta + divide y vencerás                |
| [11](doc/day11.md) | Conteo de caminos en un DAG | DFS memoizado + factorización por permutaciones |
| [12](doc/day12.md) | Empaquetado 2D de regalos | Branch and bound + transformaciones isométricas |



## Principios de diseño aplicados

El proyecto sigue un conjunto consistente de principios a lo largo de todos los días:

**Single Responsibility Principle (SRP)**: cada clase tiene una sola razón para cambiar. Los datos del dominio, los algoritmos y la orquestación viven en clases separadas.

**DRY (Don't Repeat Yourself)**: la duplicación se elimina extrayendo operaciones comunes, frecuentemente con higher-order functions.

**YAGNI (You Aren't Gonna Need It)**: las abstracciones solo se introducen cuando aportan valor real. A lo largo del proyecto se descartan clases que serían contenedores de getters.

**Tell, Don't Ask**: los objetos exponen comportamiento, no datos para que otros decidan. 

## Convenciones del proyecto

Todos los días comparten un estilo consistente:

- **Records** para los datos del dominio (coordenadas, rangos, formas, dispositivos).
- **Fluent API** en los orquestadores: `X.empty().loadedFrom(input).computeResult()`.
- **Factory methods** estáticos (`parse`, `empty`, `from`) en lugar de constructores públicos.
- **Métodos cortos**: la mayoría de 1 a 5 líneas, ninguno excede ~9.
- **Good Naming**: los métodos tienen nombres que declaran su función. 


## Resultados

| Día | Parte A | Parte B |
|---|--|--|
| 1 | 992 | 6133 |
| 2 | 19219508902 | 27180728081 |
| 3 | 17034 | 168798209663590 |
| 4 | 1349 | 8277 |
| 5 | 643 | 342018167474526 |
| 6 | 5316572080628 | 11299263623062 |
| 7 | 1649 | 16937871060075 |
| 8 | 50568 | 36045012 |
| 9 | 4754955192 | 1568849600 |
| 10 | 545 | 22430 |
| 11 | 613 | 372918445876116 |
| 12 | 454 | — |


