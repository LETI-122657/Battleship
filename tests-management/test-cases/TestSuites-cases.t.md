# Unit Tests

## 1 Core domain tests
* 1 CompassTest
        * Classe testada: `Compass`
        * Verificar todos os valores do enum
        * Testar conversão para string / uso em construtores das ships
* 2 PositionTest
    * Classe testada: `Position`
    * Criação de posições válidas
    * Igualdade, `getRow()`, `getColumn()`
    * Posições em limites do tabuleiro (0 e BOARD_SIZE-1)

## 2 Fleet & contracts
* C3 FleetTest  (classe JUnit: `Fleet_Test`)
        * Classe testada: `Fleet`
        * `addShip()` em cenários válidos/inválidos
        * `colisionRisk(IShip)`
        * `isInsideBoard(IShip)`
        * `getShipsLike(String)`
        * `getFloatingShips()`
* C4 IShipTest
    * Interface/implementações: `IShip` / `Ship`
    * Criação de ships genéricos
    * Comportamento de hit / sunk
* C5 IPositionTest
    * Interface: `IPosition`
    * Consistência entre `IPosition` e `Position`
* C6 ShipTest
    * Classe testada: `Ship`
    * Gestão da lista de posições
    * Cálculo de tamanho (`getSize()`)
    * Nome (`getName()`), estado flutuante / afundado

## 3 Individual ship types
* C7 BargeTest
  * Classe testada: `Barge`
  * Construtor para NORTH/SOUTH/EAST/WEST
  * Posições geradas correctas no tabuleiro
* C8 CaravelTest
    * Classe testada: `Caravel`
    * Construtor e posições por bearing
    * Tamanho correcto (`getSize()`)
* C9 CarrackTest
    * Classe testada: `Carrack`
    * Construtor e posições por bearing
    * Tamanho e nome correctos
* C10 FrigateTest
    * Classe testada: `Frigate`
    * Construtor com `Compass` + `IPosition`
    * Verificar quatro orientações
    * `IllegalArgumentException` em bearings inválidos
* C11 GalleonTest
    * Classe testada: `Galleon`
    * Posições correctas por bearing
    * Verificar integração com `Ship` (afundar / flutuar)

## 4 Game & Tasks
* C12 Game core tests
  * Classes: `Game`, `IGame`
  * Estado inicial do jogo
  * Adição de fleet/jogadores (quando aplicável)
* C13 TasksTest
    * Classe testada: `Tasks`
    * Criação e execução de tarefas
    * Métodos públicos relevantes (ex.: run, reset, etc.)