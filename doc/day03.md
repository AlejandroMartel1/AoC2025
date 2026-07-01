# Día 3: Selección de dígitos para máxima joltage

## Enunciado

El tercer día nos presenta bancos de baterías, representando cada banco una secuencia de dígitos (por ejemplo, `"4321567"`). Para cada banco, se debe **encender exactamente `batteriesOn` baterías** eligiendo qué dígitos conservar, manteniendo su orden de aparición original, para formar el **número más grande posible**.

- **Parte A**: hay que encender 2 baterías por banco y suma las joltages máximas obtenidas.
- **Parte B**: hay que encender 12 baterías por banco y suma las joltages máximas obtenidas.

## Algoritmos y técnicas

- **Estrategia Greedy:** toma decisiones localmente óptimas priorizando siempre el dígito más alto posible en la posición más a la izquierda para maximizar el valor.

- **Pila monótona decreciente:** actúa como el motor de la estrategia greedy, expulsando automáticamente los números menores cuando llega uno mayor.

- **Recorrido lineal:** cada dígito se considera una sola vez, decidiendo si añadirlo al resultado o descartar dígitos previos menores.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `BatteryBank` | Banco de baterías como cadena de dígitos. Resuelve `maxJoltageWith(k)` delegando a `Selection`. |
| `Selection` | Estructura mutable que mantiene la pila de dígitos elegidos y aplica el algoritmo del stack monótono. |
| `PowerSupply` | Orquestador con la lista de bancos. Suma las joltages máximas. |
| `PartA`, `PartB` | Entry points con fluent API. |


## Patrones de diseño aplicados

### Factory Method

`Selection.startingFor(totalDigits, batteriesOn)` y `BatteryBank.parse(line)` son factory methods estáticos que comunican intención y encapsulan la lógica de creación.

### Fluent API

`PowerSupply` implementa fluent API: `empty()` devuelve un orquestador vacío, `withBanksFrom(input)` devuelve uno cargado, `totalJoltageWith(k)` consulta.

### Encapsulación de mutabilidad

`Selection` es la **única clase mutable** . Su mutabilidad está encapsulada, el exterior solo invoca `consider(digit)` y `asLong()`. El stack interno (`Deque<Character>`) nunca se expone.

### Inmutabilidad por defecto

`BatteryBank` es un record inmutable. `PowerSupply` es final con campo `final` y fluent API que devuelve nuevas instancias.

## Principios aplicados

- **SRP**: `BatteryBank` modela el dato (cadena de dígitos), `Selection` ejecuta el algoritmo, `PowerSupply` orquesta. Cada clase cambia por motivos distintos.
- **Encapsulación de mutabilidad**: `Selection` es mutable, pero su mutabilidad no escapa.
- **Tell, Don't Ask**: `BatteryBank.maxJoltageWith(k)` produce el resultado directamente.

## Resultados

| Parte | Respuesta |
|---|---|
| A | 17034 |
| B | 168798209663590 |