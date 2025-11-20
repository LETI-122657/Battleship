# Testing checklists
## 5 Release checklist
* 14 Reports & cobertura
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

## 6 Automated tests checklist
* 15 Execução testes JUnit
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

