# Día 10: Configuración de máquinas con XOR y descomposición binaria

## Enunciado

En el décimo día tenemos máquinas, cada una con un panel de luces objetivo, unos botones, y unos contadores de joltage objetivo. Cada botón afecta a ciertas posiciones.

- **Parte A**: cada botón hace XOR sobre un patrón de bits. Encuentra el mínimo número de pulsaciones de botones para que el panel de luces coincida con el patrón objetivo.
- **Parte B**: cada botón incrementa en 1 los contadores que afecta cada vez que se pulsa. Encuentra el mínimo número total de pulsaciones para que cada contador alcance exactamente su joltage objetivo.

## Algoritmos y técnicas

- **Parte A**: **fuerza bruta sobre subconjuntos de botones** (`2^n` combinaciones), aprovechando que el XOR es involutivo (cada botón se pulsa 0 o 1 veces).
- **Parte B**: **divide y vencerás binario con memoización**. Cada nivel de recursión fija un bit del vector de pulsaciones, reduciendo el problema a la mitad.
- **Bitmask**: las luces y los botones se representan como máscaras de bits (`int`), permitiendo operaciones rápidas (XOR, AND, OR).
- **Filtrado por paridad**: en la parte B, solo se consideran subconjuntos cuyo efecto coincide en paridad con el joltage restante.
- **Memoización**: estados de joltage que se repiten se calculan una sola vez.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `IndicatorLight` | Panel de luces objetivo, parseado a una máscara de bits |
| `Button` | Botón individual con su cableado (wiring), parseado a una máscara de bits |
| `Machine` | Composición: luces + botones + joltage. Resuelve la parte A; delega la parte B |
| `JoltageSystem` | Algoritmo de la parte B: descomposición binaria recursiva con memoización |
| `Factory` | Orquestador: colección de máquinas, sumas de pulsaciones mínimas |
| `PartA`, `PartB` | Entry points con fluent API |


## Patrones de diseño aplicados

### Factory Method

`Factory.empty()`, `Machine.parse(line)`, `IndicatorLight.parse(diagram)`, `Button.parse(schematic)` son factory methods estáticos que encapsulan el parsing.

### Fluent API

`Factory` implementa fluent API: `empty().withMachinesFrom(input).fewestTotalButtonPresses()`.

### Inmutabilidad

`IndicatorLight`, `Button`, `Machine` son records inmutables. `Factory` es final. La única clase con estado mutable es `JoltageSystem`, que mantiene un cache de memoización encapsulado.



## Principios aplicados

- **SRP**: `IndicatorLight` (luces), `Button` (cableado), `Machine` (composición + parte A), `JoltageSystem` (algoritmo parte B), `Factory` (orquestación). Disjuntas.
- **Encapsulación de mutabilidad**: `JoltageSystem` mantiene un cache de memoización, pero es un detalle interno; la API pública (`minimumPresses`) es funcional.
- **Tell, Don't Ask**: `machine.fewestButtonPresses()`, `light.value()`. El caller pide el resultado, no extrae datos para calcularlo.



## Resultados

| Parte | Respuesta |
|---|---|
| A | 545 |
| B | 22430 |