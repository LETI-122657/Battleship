package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private TestFleet fleet;
    private Game game;

    @BeforeEach
    void setUp() {
        fleet = new TestFleet();
        game = new Game(fleet);
    }

    // ------------------------------------------------------------
    // Estado inicial
    // ------------------------------------------------------------

    @Test
    @DisplayName("Novo Game começa com contadores a zero e sem tiros")
    void newGameInitialState() {
        assertNotNull(game.getShots());
        assertTrue(game.getShots().isEmpty());

        assertEquals(0, game.getInvalidShots());
        assertEquals(0, game.getRepeatedShots());
        assertEquals(0, game.getHits());
        assertEquals(0, game.getSunkShips());
        assertEquals(0, game.getRemainingShips());
    }

    // ------------------------------------------------------------
    // validShot: 4 ramos falsos + ramo verdadeiro
    // ------------------------------------------------------------

    @Test
    @DisplayName("Tiro inválido com linha negativa incrementa invalidShots")
    void fireInvalidShotRowNegative() {
        IPosition pos = new TestPosition(-1, 0); // row < 0

        IShip result = game.fire(pos);

        assertNull(result);
        assertEquals(1, game.getInvalidShots());
        assertTrue(game.getShots().isEmpty());
    }

    @Test
    @DisplayName("Tiro inválido com linha acima do limite incrementa invalidShots")
    void fireInvalidShotRowTooBig() {
        IPosition pos = new TestPosition(IFleet.BOARD_SIZE + 1, 0); // row > BOARD_SIZE

        IShip result = game.fire(pos);

        assertNull(result);
        assertEquals(1, game.getInvalidShots()); // independente do teste anterior por causa do @BeforeEach
        assertTrue(game.getShots().isEmpty());
    }

    @Test
    @DisplayName("Tiro inválido com coluna negativa incrementa invalidShots")
    void fireInvalidShotColumnNegative() {
        IPosition pos = new TestPosition(0, -1); // col < 0

        IShip result = game.fire(pos);

        assertNull(result);
        assertEquals(1, game.getInvalidShots());
        assertTrue(game.getShots().isEmpty());
    }

    @Test
    @DisplayName("Tiro inválido com coluna acima do limite incrementa invalidShots")
    void fireInvalidShotColumnTooBig() {
        IPosition pos = new TestPosition(0, IFleet.BOARD_SIZE + 1); // col > BOARD_SIZE

        IShip result = game.fire(pos);

        assertNull(result);
        assertEquals(1, game.getInvalidShots());
        assertTrue(game.getShots().isEmpty());
    }

    // ------------------------------------------------------------
    // fire: tiro válido que falha (miss)
    // ------------------------------------------------------------

    @Test
    @DisplayName("Tiro válido que não acerta em nenhum navio é registado como miss")
    void fireValidMiss() {
        // um navio algures, mas vamos disparar para outra posição
        TestShip ship = new TestShip("Fragata", false, new TestPosition(3, 3));
        fleet.addShip(ship);

        IPosition missPos = new TestPosition(0, 0); // não ocupado por ship

        IShip result = game.fire(missPos);

        assertNull(result);
        assertEquals(0, game.getInvalidShots());
        assertEquals(0, game.getRepeatedShots());
        assertEquals(0, game.getHits());
        assertEquals(0, game.getSunkShips());
        assertEquals(1, game.getShots().size());
        assertEquals(1, game.getRemainingShips()); // navio ainda a flutuar
    }

    // ------------------------------------------------------------
    // fire: hit sem afundar navio
    // ------------------------------------------------------------

    @Test
    @DisplayName("Tiro válido que acerta num navio mas não o afunda incrementa apenas hits")
    void fireHitNoSink() {
        // navio que nunca afunda (sinkOnFirstHit = false)
        TestShip ship = new TestShip("Fragata", false, new TestPosition(2, 2));
        fleet.addShip(ship);

        IPosition hitPos = new TestPosition(2, 2);

        IShip result = game.fire(hitPos);

        assertNull(result);
        assertEquals(0, game.getInvalidShots());
        assertEquals(0, game.getRepeatedShots());
        assertEquals(1, game.getHits());
        assertEquals(0, game.getSunkShips());
        assertEquals(1, game.getShots().size());
        assertEquals(1, game.getRemainingShips()); // ainda a flutuar
    }

    // ------------------------------------------------------------
    // fire: hit que afunda navio
    // ------------------------------------------------------------

    @Test
    @DisplayName("Tiro válido que afunda navio incrementa hits e sunkShips e devolve o navio")
    void fireHitAndSink() {
        // navio que afunda ao primeiro hit (sinkOnFirstHit = true)
        TestShip ship = new TestShip("Fragata", true, new TestPosition(4, 4));
        fleet.addShip(ship);

        IPosition hitPos = new TestPosition(4, 4);

        IShip result = game.fire(hitPos);

        assertSame(ship, result);
        assertEquals(0, game.getInvalidShots());
        assertEquals(0, game.getRepeatedShots());
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
        assertEquals(1, game.getShots().size());
        assertEquals(0, game.getRemainingShips()); // já não está a flutuar
    }

    // ------------------------------------------------------------
    // fire: repeatedShot
    // ------------------------------------------------------------

    @Test
    @DisplayName("Tiro repetido incrementa repeatedShots e não altera restantes contadores")
    void fireRepeatedShot() {
        TestShip ship = new TestShip("Fragata", false, new TestPosition(1, 1));
        fleet.addShip(ship);

        IPosition pos = new TestPosition(0, 0); // miss mas válido

        // primeiro tiro → registado
        game.fire(pos);

        // segundo tiro na mesma posição → repeatedShot
        IShip result = game.fire(pos);

        assertNull(result);
        assertEquals(0, game.getInvalidShots());
        assertEquals(1, game.getRepeatedShots());
        assertEquals(0, game.getSunkShips());
        assertEquals(0, game.getHits());
        assertEquals(1, game.getShots().size()); // não adiciona outra entrada
    }

    // ------------------------------------------------------------
    // printBoard / printValidShots / printFleet
    // ------------------------------------------------------------

    @Test
    @DisplayName("printBoard funciona com lista vazia sem lançar exceções")
    void printBoardEmptyDoesNotThrow() {
        List<IPosition> empty = new ArrayList<>();
        assertDoesNotThrow(() -> game.printBoard(empty, 'X'));
    }

    @Test
    @DisplayName("printValidShots imprime tiros válidos sem lançar exceções")
    void printValidShotsDoesNotThrow() {
        game.fire(new TestPosition(0, 0)); // tiro válido
        game.fire(new TestPosition(1, 1)); // outro tiro válido

        assertDoesNotThrow(() -> game.printValidShots());
    }

    @Test
    @DisplayName("printFleet imprime posições da frota sem lançar exceções")
    void printFleetDoesNotThrow() {
        TestShip ship1 = new TestShip("Fragata", false,
                new TestPosition(0, 0), new TestPosition(0, 1));
        TestShip ship2 = new TestShip("Galeao", false,
                new TestPosition(2, 2), new TestPosition(2, 3));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        assertDoesNotThrow(() -> game.printFleet());
    }

    // ============================================================
    // Test doubles
    // ============================================================

    private static class TestPosition implements IPosition {
        final int row;
        final int column;
        private boolean occupied;
        private boolean hit;

        TestPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {
            return column;
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            if (!(other instanceof TestPosition o)) return false;
            int dr = Math.abs(row - o.row);
            int dc = Math.abs(column - o.column);
            return (dr <= 1 && dc <= 1) && (dr + dc > 0);
        }

        @Override
        public void occupy() {
            occupied = true;
        }

        @Override
        public void shoot() {
            hit = true;
        }

        @Override
        public boolean isOccupied() {
            return occupied;
        }

        @Override
        public boolean isHit() {
            return hit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestPosition)) return false;
            TestPosition that = (TestPosition) o;
            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            return 31 * row + column;
        }

        @Override
        public String toString() {
            return "(" + row + "," + column + ")";
        }
    }

    private static class TestShip implements IShip {
        private final String category;
        private final boolean sinkOnFirstHit;
        private final List<IPosition> positions;
        private int hits = 0;

        TestShip(String category, boolean sinkOnFirstHit, IPosition... positions) {
            this.category = category;
            this.sinkOnFirstHit = sinkOnFirstHit;
            this.positions = new ArrayList<>();
            for (IPosition p : positions) {
                this.positions.add(p);
            }
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public Integer getSize() {
            return positions.size();
        }

        @Override
        public List<IPosition> getPositions() {
            return new ArrayList<>(positions);
        }

        @Override
        public IPosition getPosition() {
            return positions.isEmpty() ? null : positions.get(0);
        }

        @Override
        public Compass getBearing() {
            return Compass.NORTH;
        }

        @Override
        public boolean stillFloating() {
            if (!sinkOnFirstHit) {
                return true;
            }
            return hits == 0;
        }

        @Override
        public int getTopMostPos() {
            int min = Integer.MAX_VALUE;
            for (IPosition p : positions) {
                min = Math.min(min, p.getRow());
            }
            return positions.isEmpty() ? 0 : min;
        }

        @Override
        public int getBottomMostPos() {
            int max = Integer.MIN_VALUE;
            for (IPosition p : positions) {
                max = Math.max(max, p.getRow());
            }
            return positions.isEmpty() ? 0 : max;
        }

        @Override
        public int getLeftMostPos() {
            int min = Integer.MAX_VALUE;
            for (IPosition p : positions) {
                min = Math.min(min, p.getColumn());
            }
            return positions.isEmpty() ? 0 : min;
        }

        @Override
        public int getRightMostPos() {
            int max = Integer.MIN_VALUE;
            for (IPosition p : positions) {
                max = Math.max(max, p.getColumn());
            }
            return positions.isEmpty() ? 0 : max;
        }

        @Override
        public boolean occupies(IPosition pos) {
            return positions.contains(pos);
        }

        @Override
        public boolean tooCloseTo(IShip other) {
            return false;
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            return false;
        }

        @Override
        public void shoot(IPosition pos) {
            if (occupies(pos)) {
                hits++;
            }
        }
    }

    private static class TestFleet implements IFleet {
        private final List<IShip> ships = new ArrayList<>();

        @Override
        public List<IShip> getShips() {
            return ships;
        }

        @Override
        public boolean addShip(IShip s) {
            ships.add(s);
            return true;
        }

        @Override
        public List<IShip> getShipsLike(String category) {
            List<IShip> res = new ArrayList<>();
            for (IShip s : ships) {
                if (s.getCategory().equals(category)) {
                    res.add(s);
                }
            }
            return res;
        }

        @Override
        public List<IShip> getFloatingShips() {
            List<IShip> res = new ArrayList<>();
            for (IShip s : ships) {
                if (s.stillFloating()) {
                    res.add(s);
                }
            }
            return res;
        }

        @Override
        public IShip shipAt(IPosition pos) {
            for (IShip s : ships) {
                if (s.occupies(pos)) {
                    return s;
                }
            }
            return null;
        }

        @Override
        public void printStatus() {
            // não interessa para estes testes
        }
    }
}
