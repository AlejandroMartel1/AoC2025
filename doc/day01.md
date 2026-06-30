# Día 1: Dial circular con rotaciones

## Enunciado

El problema nos plantea un dial circular de 100 posiciones (0 a 99), que empieza en la posición 50. Partiendo de ese punto, se reciben secuencias de instrucciones como `R30` (girar 30 a la derecha) o `L15` (girar 15 a la izquierda). Estos movimientos nos plantean dos retos a solucionar:

- **Parte A**: ¿cuántas veces el dial acaba exactamente en la posición 0 después de aplicar cada instrucción?
- **Parte B**: ¿cuántas veces el dial pasa por la posición 0 durante el recorrido (acumulando cruces, no solo paradas)?

## Algoritmos y técnicas

- **Algoritmo principal**: el problema se ha resuelto aplicando aritmética modular (`Math.floorMod`, `Math.floorDiv`).

- **Parte A**: en la primera parte se aplica de forma iterativa `Math.floorMod(position + step, 100)` para mantener la posición en `[0, 100)`, recontando las paradas exactas en 0.

- **Parte B**: conteo de cruces por **fórmula cerrada** en `O(1)` por rotación, sin simulación paso a paso.

## Modelado en clases

| Clase | Responsabilidad                                                                            |
|---|--------------------------------------------------------------------------------------------|
| `Dial` | Estado del dial (lista de rotaciones) y operaciones de conteo (parte A y parte B)          |
| `Rotation` | Una rotación individual con su paso entero (positivo para derecha, negativo para izquierda) |
| `PartA`, `PartB` | Devuelve la solución mediante fluent API                                                   |

## Patrones de diseño aplicados

### Factory Method

`Dial.empty()` es un factory method estático que reemplaza el constructor público. Comunica intención (un dial vacío) y permite añadir variantes en el futuro sin romper el código existente.

### Fluent API

`Dial` implementa una API fluida donde cada operación devuelve una nueva instancia. Esto permite encadenar llamadas:

```java
Dial.empty().follow(input).countTimesEndingAtZero();
```

### Inmutabilidad

`Rotation` es un record inmutable. `Dial` es una clase final con campos `final` cuyas operaciones devuelven nuevas instancias en lugar de mutar el estado.

## Principios aplicados

- **SRP**: `Dial` gestiona el estado y los conteos; `Rotation` modela una rotación individual con su parsing. Si cambia el formato de input, solo `Rotation.parse` se modifica.
- **YAGNI**: no hay clase `Position` envolviendo el `int` que indica la posición, ni clase `RotationSequence` envolviendo la lista. Serían wrappers sin comportamiento.
- **Inmutabilidad**: `Dial` es final con campos `final`, `Rotation` es record. Cada operación devuelve nuevas instancias.
- **Tell, Don't Ask**: `Rotation.parse(instruction)` produce la rotación directamente; el caller no extrae carácter y substring para construirla.

### Diferencia clave entre las dos partes

La parte A reduce módulo 100 en cada paso y comprueba igualdad con 0. La parte B **no reduce módulo**: trabaja con posiciones absolutas (que pueden crecer o decrecer sin límite) y cuenta cruces de múltiplos de 100. Son **dos modelos mentales distintos** del mismo dial.


La rama `from < to ? ... : ...` maneja los dos sentidos de giro. Cuando vas hacia atrás (`from > to`), el cálculo es ligeramente distinto: `floorDiv(from - 1, SIZE) - floorDiv(to - 1, SIZE)`. El `-1` ajusta para no contar el extremo inicial.


## Resultados

| Parte | Respuesta |
|---|---|
| A | 992 |
| B | 6133 |