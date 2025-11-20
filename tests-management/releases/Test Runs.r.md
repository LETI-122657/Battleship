# Test Runs

## Testing checklists

### S5 Release checklist
* [unknown] C14 Reports & cobertura
    * Confirmar geração dos relatórios JaCoCo:
        * `reports/htmlReport-LET1-122641`
        * `reports/htmlReport-LET1-122645`
        * `reports/htmlReport_122657`
    * Cobertura mínima:
        * Módulo `battleship` ≥ 80% linhas
        * Classes principais (`Fleet`, `Ship`, `Position`,
          `Barge`, `Caravel`, `Carrack`, `Frigate`, `Galleon`,
          `Tasks`) ≥ 90% métodos
    * Guardar screenshots da cobertura no IntelliJ


### S6 Automated tests checklist
* [unknown] C15 Execução testes JUnit
    * Correr todos os testes no pacote `iscteiul.ista.battleship`:
        * `CompassTest`
        * `Fleet_Test`
        * `IFleetTest` (sub-testes: `FleetTest`, `IPositionTest`, `IShipTest`, `PositionTest`, `ShipTest`)
        * `BargeTest`, `CaravelTest`, `CarrackTest`, `FrigateTest`, `GalleonTest`
        * `TasksTest` (quando existir)
    * Verificar:
        * Todos verdes no IntelliJ
        * `mvn test` termina com sucesso
        * Relatório JaCoCo actualizado na pasta `reports`


## Unit Tests

### S1 Core domain tests
* [unknown] C1 CompassTest

* [unknown] C2 PositionTest
    * Classe testada: `Position`
    * Criação de posições válidas
    * Igualdade, `getRow()`, `getColumn()`
    * Posições em limites do tabuleiro (0 e BOARD_SIZE-1)


### S2 Fleet & contracts
* [unknown] C3 FleetTest  (classe JUnit: `Fleet_Test`)

* [unknown] C4 IShipTest
    * Interface/implementações: `IShip` / `Ship`
    * Criação de ships genéricos
    * Comportamento de hit / sunk

* [unknown] C5 IPositionTest
    * Interface: `IPosition`
    * Consistência entre `IPosition` e `Position`

* [unknown] C6 ShipTest
    * Classe testada: `Ship`
    * Gestão da lista de posições
    * Cálculo de tamanho (`getSize()`)
    * Nome (`getName()`), estado flutuante / afundado


### S3 Individual ship types
* [unknown] C7 BargeTest
    * Classe testada: `Barge`
    * Construtor para NORTH/SOUTH/EAST/WEST
    * Posições geradas correctas no tabuleiro

* [unknown] C8 CaravelTest
    * Classe testada: `Caravel`
    * Construtor e posições por bearing
    * Tamanho correcto (`getSize()`)

* [unknown] C9 CarrackTest
    * Classe testada: `Carrack`
    * Construtor e posições por bearing
    * Tamanho e nome correctos

* [unknown] C10 FrigateTest
    * Classe testada: `Frigate`
    * Construtor com `Compass` + `IPosition`
    * Verificar quatro orientações
    * `IllegalArgumentException` em bearings inválidos

* [unknown] C11 GalleonTest
    * Classe testada: `Galleon`
    * Posições correctas por bearing
    * Verificar integração com `Ship` (afundar / flutuar)


### S4 Game & Tasks
* [unknown] C12 Game core tests
    * Classes: `Game`, `IGame`
    * Estado inicial do jogo
    * Adição de fleet/jogadores (quando aplicável)

* [unknown] C13 TasksTest
    * Classe testada: `Tasks`
    * Criação e execução de tarefas
    * Métodos públicos relevantes (ex.: run, reset, etc.)


