# Día 6: Hoja de problemas matemáticos con doble modo de lectura

## Enunciado

Tienes una hoja con problemas matemáticos dispuestos en bloques separados por columnas vacías. Cada bloque contiene números (en las filas superiores) y un operador (`+` o `*`) en la última fila.

- **Parte A**: lee cada bloque **horizontalmente**: cada fila es un número completo, el operador está en la fila final. Aplica el operador a todos los números del bloque y suma los resultados de todos los bloques.
- **Parte B**: lee cada bloque **columnarmente**: los dígitos se leen recorriendo columnas de derecha a izquierda, formando los números de otra manera. Aplica el operador y suma.

## Algoritmos y técnicas

- **Algoritmo principal**: **parsing con dos estrategias** (Strategy pattern) sobre una estructura común de bloques.
- **Detección de bloques por barrido de columnas**: una columna completamente en blanco marca una frontera entre bloques (máquina de estados con `start`/`-1`).
- **Reducción funcional**: cada problema se resuelve con `reduce` aplicando el operador desde su elemento identidad.
- **Enum con comportamiento**: `Operation` lleva su símbolo, su operación binaria y su elemento identidad.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `ProblemSheet` | La hoja como rejilla de caracteres con padding. Detecta bloques (`blockRanges`), accede a celdas y extrae slices |
| `MathProblem` | Un problema individual: lista de números + operador. Calcula su resultado |
| `Operation` | Enum con los operadores (`+`, `*`), cada uno con su operación binaria e identidad |
| `MathWorksheet` | Orquestador: parsea la hoja con la estrategia elegida (horizontal o columnar) y suma los resultados |
| `PartA`, `PartB` | Entry points con fluent API |

## Patrones de diseño aplicados

### Strategy (con interfaz funcional privada)

### Factory Method

`MathWorksheet.empty()`, `ProblemSheet.from(input)` y `Operation.fromSymbol(char)` son factory methods estáticos.

### Fluent API

`MathWorksheet` implementa fluent API con dos cargadores: `loadedFrom` (horizontal) y `loadedColumnarFrom` (columnar), ambos seguidos de `grandTotal()`.

### Enum con comportamiento (Strategy enum)

`Operation` no es un enum plano: cada valor lleva su `LongBinaryOperator` y su elemento identidad. `ADD` usa suma e identidad 0; `MULTIPLY` usa producto e identidad 1. Es Strategy pattern usando enum como contenedor de comportamiento.

### Inmutabilidad

`MathProblem` y `ProblemSheet` son records. `MathWorksheet` es final con fluent API. `Operation` es un enum (inmutable por naturaleza).


## Principios aplicados

- **DRY**: el esqueleto de parsing (dividir en bloques + parsear cada uno) vive una vez en `parseWith` y `parseAllBlocks`. Las dos estrategias solo difieren en cómo parsean un bloque.
- **SRP**: `ProblemSheet` (estructura y detección de bloques), `MathProblem` (un problema y su resultado), `Operation` (operadores), `MathWorksheet` (orquestación).
- **Open/Closed**: añadir un operador nuevo es añadir un valor a `Operation`, sin modificar el código existente.


## Resultados

| Parte | Respuesta |
|---|---|
| A | 5316572080628 |
| B | 11299263623062 |