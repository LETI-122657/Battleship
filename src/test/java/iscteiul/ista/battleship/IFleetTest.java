package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    private Fleet fleet;

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
    }

    @Test
    @DisplayName("BOARD_SIZE e FLEET_SIZE têm os valores esperados")
    void constantsHaveExpectedValues() {
        assertEquals(10, IFleet.BOARD_SIZE);
        assertEquals(10, IFleet.FLEET_SIZE);
    }

    @Test
    @DisplayName("Uma nova fleet começa vazia")
    void newFleetStartsEmpty() {
        assertNotNull(fleet.getShips());
        assertTrue(fleet.getShips().isEmpty());

        assertNotNull(fleet.getShipsLike("any"));
        assertTrue(fleet.getShipsLike("any").isEmpty());

        assertNotNull(fleet.getFloatingShips());
        assertTrue(fleet.getFloatingShips().isEmpty());

        assertNull(fleet.shipAt(new TestPosition(0, 0)));
    }

    @Test
    @DisplayName("printStatus() não deve lançar exceções numa fleet vazia")
    void printStatusOnEmptyFleetDoesNotThrow() {
        assertDoesNotThrow(() -> fleet.printStatus());
    }

    // ------------------------------------------------------------
    // addShip + isInsideBoard + colisionRisk
    // ------------------------------------------------------------

    @Test
    @DisplayName("addShip adiciona um navio válido dentro do tabuleiro sem colisão")
    void addShipSuccessInsideBoardNoCollision() {
        TestShip s = new TestShip(
                "Fragata",
                true,           // stillFloating
                false,          // collidesWithAny
                pos(2, 2), pos(2, 3)
        );

        boolean added = fleet.addShip(s);

        assertTrue(added);
        assertEquals(1, fleet.getShips().size());
        assertSame(s, fleet.getShips().get(0));
    }

    @Test
    @DisplayName("addShip falha quando a frota já está cheia")
    void addShipFailsWhenFleetFull() {
        for (int i = 0; i < IFleet.FLEET_SIZE; i++) {
            TestShip s = new TestShip(
                    "Barca",
                    true,
                    false,
                    pos(i, 0)
            );
            assertTrue(fleet.addShip(s));
        }

        TestShip extra = new TestShip(
                "Barca",
                true,
                false,
                pos(9, 9)
        );

        assertFalse(fleet.addShip(extra));
        assertEquals(IFleet.FLEET_SIZE.intValue(), fleet.getShips().size());
    }

    @Test
    @DisplayName("addShip falha quando navio está fora do tabuleiro (isInsideBoard == false)")
    void addShipFailsWhenShipOutsideBoard() {
        // leftMostPos = -1 -> fora do tabuleiro
        TestShip outside = new TestShip(
                "Fragata",
                true,
                false,
                new TestPosition(0, -1),
                new TestPosition(0, 0)
        );

        boolean added = fleet.addShip(outside);

        assertFalse(added);
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip falha quando existe risco de colisão (colisionRisk == true)")
    void addShipFailsWhenCollisionRisk() {
        TestShip first = new TestShip(
                "Galeao",
                true,
                true,           // collidesWithAny = true
                pos(1, 1),
                pos(1, 2)
        );
        assertTrue(fleet.addShip(first));

        TestShip colliding = new TestShip(
                "Galeao",
                true,
                false,
                pos(5, 5)
        );

        boolean added = fleet.addShip(colliding);

        assertFalse(added);
        assertEquals(1, fleet.getShips().size());
    }

    // ------------------------------------------------------------
    // getShipsLike
    // ------------------------------------------------------------

    @Test
    @DisplayName("getShipsLike devolve apenas navios da categoria pedida")
    void getShipsLikeFiltersByCategory() {
        TestShip galeao = new TestShip("Galeao", true, false, pos(0, 0));
        TestShip fragata = new TestShip("Fragata", true, false, pos(1, 0));

        fleet.addShip(galeao);
        fleet.addShip(fragata);

        List<IShip> galeoes = fleet.getShipsLike("Galeao");
        assertEquals(1, galeoes.size());
        assertSame(galeao, galeoes.get(0));

        assertTrue(fleet.getShipsLike("Nau").isEmpty());
    }

    // ------------------------------------------------------------
    // getFloatingShips
    // ------------------------------------------------------------

    @Test
    @DisplayName("getFloatingShips devolve apenas navios ainda a flutuar")
    void getFloatingShipsReturnsOnlyStillFloating() {
        TestShip floating = new TestShip("Fragata", true, false, pos(0, 0));
        TestShip sunk = new TestShip("Fragata", false, false, pos(1, 0));

        fleet.addShip(floating);
        fleet.addShip(sunk);

        List<IShip> res = fleet.getFloatingShips();
        assertEquals(1, res.size());
        assertSame(floating, res.get(0));
    }

    // ------------------------------------------------------------
    // shipAt
    // ------------------------------------------------------------

    @Test
    @DisplayName("shipAt devolve o navio que ocupa a posição indicada")
    void shipAtReturnsShipWhenPositionOccupied() {
        TestShip ship = new TestShip(
                "Barca",
                true,
                false,
                pos(4, 4),
                pos(4, 5)
        );
        fleet.addShip(ship);

        assertSame(ship, fleet.shipAt(pos(4, 4)));
        assertSame(ship, fleet.shipAt(pos(4, 5)));
    }

    @Test
    @DisplayName("shipAt devolve null quando nenhuma posição é ocupada")
    void shipAtReturnsNullWhenNoShipAtPosition() {
        TestShip ship = new TestShip("Barca", true, false, pos(4, 4));
        fleet.addShip(ship);

        assertNull(fleet.shipAt(pos(0, 0)));
    }

    // ------------------------------------------------------------
    // Métodos de impressão
    // ------------------------------------------------------------

    @Test
    @DisplayName("printAllShips não lança exceção com navios na frota")
    void printAllShipsDoesNotThrow() {
        fleet.addShip(new TestShip("Fragata", true, false, pos(0, 0)));
        fleet.addShip(new TestShip("Galeao", true, false, pos(1, 0)));

        assertDoesNotThrow(() -> fleet.printAllShips());
    }

    @Test
    @DisplayName("printFloatingShips não lança exceção")
    void printFloatingShipsDoesNotThrow() {
        TestShip floating = new TestShip("Fragata", true, false, pos(0, 0));
        TestShip sunk = new TestShip("Fragata", false, false, pos(1, 0));

        fleet.addShip(floating);
        fleet.addShip(sunk);

        assertDoesNotThrow(() -> fleet.printFloatingShips());
    }

    @Test
    @DisplayName("printShipsByCategory não lança exceção com e sem navios dessa categoria")
    void printShipsByCategoryDoesNotThrow() {
        fleet.addShip(new TestShip("Galeao", true, false, pos(0, 0)));

        assertDoesNotThrow(() -> fleet.printShipsByCategory("Galeao"));
        assertDoesNotThrow(() -> fleet.printShipsByCategory("CategoriaInexistente"));
    }

    @Test
    @DisplayName("printStatus não lança exceção com navios na frota")
    void printStatusWithShipsDoesNotThrow() {
        fleet.addShip(new TestShip("Galeao", true, false, pos(0, 0)));
        fleet.addShip(new TestShip("Fragata", true, false, pos(1, 0)));

        assertDoesNotThrow(() -> fleet.printStatus());
    }

    // ============================================================
    // Test doubles: IPosition + IShip artificiais
    // ============================================================

    private static TestPosition pos(int row, int col) {
        return new TestPosition(row, col);
    }

    private static class TestPosition implements IPosition {
        final int row;
        final int col;
        private boolean occupied;
        private boolean hit;

        TestPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // ----- IPosition -----

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {          // se na tua interface for getCol(), muda aqui
            return col;
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            if (!(other instanceof TestPosition o)) return false;
            int dr = Math.abs(row - o.row);
            int dc = Math.abs(col - o.col);
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

        // equals(Object) já é exigido; usamos o padrão normal
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestPosition)) return false;
            TestPosition that = (TestPosition) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }

    private static class TestShip implements IShip {
        private final String category;
        private final boolean floating;
        private final boolean collidesWithAny;
        private final List<TestPosition> positions;
        private final int left;
        private final int right;
        private final int top;
        private final int bottom;

        TestShip(String category,
                 boolean floating,
                 boolean collidesWithAny,
                 TestPosition... positions) {

            this.category = category;
            this.floating = floating;
            this.collidesWithAny = collidesWithAny;
            this.positions = new ArrayList<>();
            int minRow = Integer.MAX_VALUE, maxRow = Integer.MIN_VALUE;
            int minCol = Integer.MAX_VALUE, maxCol = Integer.MIN_VALUE;

            for (TestPosition p : positions) {
                this.positions.add(p);
                minRow = Math.min(minRow, p.row);
                maxRow = Math.max(maxRow, p.row);
                minCol = Math.min(minCol, p.col);
                maxCol = Math.max(maxCol, p.col);
            }
            this.top = minRow;
            this.bottom = maxRow;
            this.left = minCol;
            this.right = maxCol;
        }

        // ----- IShip -----

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
            // qualquer valor serve; Fleet não usa
            return Compass.NORTH;
        }

        @Override
        public boolean stillFloating() {
            return floating;
        }

        @Override
        public int getTopMostPos() {
            return top;
        }

        @Override
        public int getBottomMostPos() {
            return bottom;
        }

        @Override
        public int getLeftMostPos() {
            return left;
        }

        @Override
        public int getRightMostPos() {
            return right;
        }

        @Override
        public boolean occupies(IPosition pos) {
            if (!(pos instanceof TestPosition)) return false;
            return positions.contains(pos);
        }

        @Override
        public boolean tooCloseTo(IShip other) {
            return collidesWithAny;
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            // implementação simples, não é usada pelo Fleet
            return collidesWithAny;
        }

        @Override
        public void shoot(IPosition pos) {
            // no-op para testes
        }

        @Override
        public String toString() {
            return "TestShip{" + category + " " + positions + "}";
        }
    }

    @Test
    @DisplayName("addShip falha quando rightMostPos está fora do tabuleiro")
    void addShipFailsWhenRightMostOutsideBoard() {
        // left >= 0, top/bottom válidos; apenas right > 9
        TestShip s = new TestShip(
                "Fragata",
                true,
                false,
                new TestPosition(0, 8),
                new TestPosition(0, 10)   // col = 10 -> rightMostPos = 10 > 9
        );

        assertFalse(fleet.addShip(s));
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip falha quando topMostPos é negativo")
    void addShipFailsWhenTopMostNegative() {
        // coluna válida, mas linha -1
        TestShip s = new TestShip(
                "Fragata",
                true,
                false,
                new TestPosition(-1, 5),   // topMostPos = -1 < 0
                new TestPosition(0, 5)
        );

        assertFalse(fleet.addShip(s));
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip falha quando bottomMostPos está fora do tabuleiro")
    void addShipFailsWhenBottomMostOutsideBoard() {
        // linha 9 e 10 -> bottomMostPos = 10 > 9
        TestShip s = new TestShip(
                "Fragata",
                true,
                false,
                new TestPosition(9, 5),
                new TestPosition(10, 5)
        );

        assertFalse(fleet.addShip(s));
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("printShipsByCategory lança AssertionError se categoria for null")
    void printShipsByCategoryWithNullThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null));
    }

}
