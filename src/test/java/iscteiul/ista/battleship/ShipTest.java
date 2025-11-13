package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Generic Ship behavior tests")
class ShipTest {

    /**
     * Subclasse concreta apenas para testar Ship (por ser abstract).
     */
    static class TestShip extends Ship {
        private int size;

        public TestShip(String category, Compass bearing, IPosition pos, int size) {
            super(category, bearing, pos);
            this.size = size;

            // Cria posições artificiais em linha horizontal (colunas consecutivas)
            for (int i = 0; i < size; i++) {
                getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
            }
        }

        @Override
        public Integer getSize() {
            return size;
        }
    }

    Ship ship;

    @BeforeEach
    void setUp() {
        ship = new TestShip("fragata", Compass.EAST, new Position(2, 3), 3);
    }

    @Test
    @DisplayName("Constructor and basic getters should work")
    void testConstructorAndGetters() {
        assertAll("basic getters",
                () -> assertEquals("fragata", ship.getCategory()),
                () -> assertEquals(Compass.EAST, ship.getBearing()),
                () -> assertEquals(new Position(2, 3), ship.getPosition()),
                () -> assertEquals(3, ship.getSize())
        );
    }

    @Test
    @DisplayName("stillFloating should be true until all positions are hit")
    void testStillFloating() {
        assertTrue(ship.stillFloating(), "Ship should start floating");

        // Dispara em todas as posições menos uma
        ship.getPositions().get(0).shoot();
        ship.getPositions().get(1).shoot();
        assertTrue(ship.stillFloating(), "Ship should still float");

        // Última posição também é atingida
        ship.getPositions().get(2).shoot();
        assertFalse(ship.stillFloating(), "Ship should not float after all are hit");
    }

    @Test
    @DisplayName("occupies should detect positions belonging to the ship")
    void testOccupies() {
        IPosition inside = new Position(2, 4);
        IPosition outside = new Position(5, 5);

        assertTrue(ship.occupies(inside));
        assertFalse(ship.occupies(outside));
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) should return true for adjacent positions")
    void testTooCloseToPosition() {
        IPosition adjacent = new Position(1, 3); // directly above the first position
        IPosition distant = new Position(5, 5);

        assertTrue(ship.tooCloseTo(adjacent));
        assertFalse(ship.tooCloseTo(distant));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) should detect proximity to another ship")
    void testTooCloseToShip() {
        Ship other = new TestShip("caravela", Compass.EAST, new Position(3, 3), 2);
        assertTrue(ship.tooCloseTo(other), "Ships should be too close");

        Ship farShip = new TestShip("caravela", Compass.EAST, new Position(10, 10), 2);
        assertFalse(ship.tooCloseTo(farShip), "Ships should not be too close");
    }

    @Test
    @DisplayName("shoot should mark hit position as hit")
    void testShoot() {
        IPosition target = new Position(2, 4);
        assertFalse(ship.getPositions().get(1).isHit(), "Position should start unhit");

        ship.shoot(target);
        assertTrue(ship.getPositions().get(1).isHit(), "Position should be hit after shooting");
    }

    @Test
    @DisplayName("getTopMostPos / getBottomMostPos / getLeftMostPos / getRightMostPos")
    void testPositionBoundaries() {
        // As posições são (2,3), (2,4), (2,5)
        assertAll("boundaries",
                () -> assertEquals(2, ship.getTopMostPos()),
                () -> assertEquals(2, ship.getBottomMostPos()),
                () -> assertEquals(3, ship.getLeftMostPos()),
                () -> assertEquals(5, ship.getRightMostPos())
        );
    }

    @Test
    @DisplayName("toString should include category, bearing, and position")
    void testToString() {
        String output = ship.toString();
        assertTrue(output.contains("fragata"));
    }

    @Test
    @DisplayName("buildShip should create correct subclass for each known category")
    void testBuildShipFactory() {
        Ship s1 = Ship.buildShip("barca", Compass.NORTH, new Position(0, 0));
        Ship s2 = Ship.buildShip("caravela", Compass.NORTH, new Position(0, 0));
        Ship s3 = Ship.buildShip("nau", Compass.NORTH, new Position(0, 0));
        Ship s4 = Ship.buildShip("fragata", Compass.NORTH, new Position(0, 0));
        Ship s5 = Ship.buildShip("galeao", Compass.NORTH, new Position(0, 0));
        Ship s6 = Ship.buildShip("desconhecido", Compass.NORTH, new Position(0, 0));

        assertAll("ship factory",
                () -> assertTrue(s1 instanceof Barge),
                () -> assertTrue(s2 instanceof Caravel),
                () -> assertTrue(s3 instanceof Carrack),
                () -> assertTrue(s4 instanceof Frigate),
                () -> assertTrue(s5 instanceof Galleon),
                () -> assertNull(s6, "Unknown type should return null")
        );
    }
}
