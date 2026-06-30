# Día 11: Conteo de caminos en un DAG (Grafo Acíclico Dirigido)

## Enunciado

El undécimo día cuenta con un reactor descrito como un grafo dirigido: cada dispositivo envía señal a otros dispositivos. El grafo es acíclico (DAG).

- **Parte A**: cuenta cuántos caminos distintos hay desde el dispositivo origen (`you`) hasta el destino (`out`).
- **Parte B**: cuenta cuántos caminos van desde el origen (`svr`) hasta el destino (`out`) pasando obligatoriamente por todos los puntos de paso intermedios (`dac` y `fft`), en cualquier orden.

## Algoritmos y técnicas

- **Parte A**: **DFS con memoización** sobre un DAG para contar caminos en tiempo lineal.
- **Parte B**: **factorización multiplicativa por permutaciones** de los puntos de paso obligatorios.
- **Memoización**: cada nodo se computa una sola vez, convirtiendo conteo exponencial en lineal.
- **Generación de permutaciones**: backtracking in-place para enumerar todos los órdenes de puntos de paso.

## Modelado en clases

| Clase | Responsabilidad |
|---|---|
| `Device` | Un dispositivo con su nombre y la lista de dispositivos a los que envía señal. Parsea una línea del input |
| `Reactor` | El grafo del reactor. Cuenta caminos (parte A) y caminos a través de puntos de paso (parte B) |
| `PartA`, `PartB` | Entry points con fluent API y las constantes de origen, destino y puntos de paso |


## Patrones de diseño aplicados

### Factory Method

`Reactor.empty()` y `Device.parse(line)` son factory methods estáticos.

### Fluent API

`Reactor` implementa fluent API: `empty().withDevicesFrom(input).countPaths(source, target)`.

### Inmutabilidad

`Device` es un record inmutable. `Reactor` es final y su mapa de conexiones se construye con `Map.copyOf`, garantizando inmutabilidad tras la creación. La memoización usa un `HashMap` local a cada llamada, no estado compartido.

## Principios aplicados

- **SRP**: `Device` modela un dispositivo (datos + parsing), `Reactor` modela el grafo y los algoritmos de conteo. Disjuntas.
- **Tell, Don't Ask**: `reactor.countPaths(source, target)` produce el resultado; el caller no recorre el grafo manualmente.
- **Inmutabilidad por defecto**: `Device` es record, `Reactor` usa `Map.copyOf`. La memoización es local a cada llamada, sin estado compartido entre invocaciones.


## Resultados

| Parte | Respuesta |
|---|---|
| A | 613 |
| B | 372918445876116 |
