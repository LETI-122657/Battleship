package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Interface IShip contract tests")
class IShipTest {

    static class TestShip extends Ship {
        private final int size;

        public TestShip(String category, Compass bearing, IPosition pos, int size) {
            super(category, bearing, pos);
            this.size = size;
            for (int i = 0; i < size; i++) {
                getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
            }
        }

        @Override
        public Integer getSize() {
            return size;
        }
    }

    IShip ship;

    @BeforeEach
    void setUp() {
        ship = new TestShip("barca", Compass.EAST, new Position(2, 3), 3);
    }

    @Test
    @DisplayName("getCategory, getBearing, getPosition must return correct values")
    void testBasicGetters() {
        assertAll("getters",
                () -> assertEquals("barca", ship.getCategory()),
                () -> assertEquals(Compass.EAST, ship.getBearing()),
                () -> assertEquals(new Position(2, 3), ship.getPosition())
        );
    }

    @Test
    @DisplayName("stillFloating must reflect whether any position remains unhit")
    void testStillFloating() {
        // No shots yet
        assertTrue(ship.stillFloating(), "should start floating");

        // Hit all but last
        ship.getPositions().get(0).shoot();
        ship.getPositions().get(1).shoot();
        assertTrue(ship.stillFloating(), "still floating with one unhit");

        // All hit
        ship.getPositions().get(2).shoot();
        assertFalse(ship.stillFloating(), "should not float after all hit");
    }

    @Test
    @DisplayName("occupies should return true only for positions belonging to the ship")
    void testOccupies() {
        IPosition inside = new Position(2, 4);
        IPosition outside = new Position(5, 5);
        assertTrue(ship.occupies(inside));
        assertFalse(ship.occupies(outside));
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) should detect adjacent positions")
    void testTooCloseToPosition() {
        IPosition adjacent = new Position(1, 3);
        IPosition far = new Position(10, 10);

        assertTrue(ship.tooCloseTo(adjacent));
        assertFalse(ship.tooCloseTo(far));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) should detect proximity between ships")
    void testTooCloseToOtherShip() {
        IShip near = new TestShip("fragata", Compass.EAST, new Position(3, 3), 2);
        IShip far = new TestShip("fragata", Compass.EAST, new Position(10, 10), 2);

        assertTrue(ship.tooCloseTo(near));
        assertFalse(ship.tooCloseTo(far));
    }

    @Test
    @DisplayName("shoot should mark a hit only on matching position")
    void testShoot() {
        IPosition target = new Position(2, 4);
        IPosition miss = new Position(9, 9);

        assertFalse(ship.getPositions().get(1).isHit());
        ship.shoot(target);
        assertTrue(ship.getPositions().get(1).isHit());

        // shooting a position that isn't occupied should not cause exception
        assertDoesNotThrow(() -> ship.shoot(miss));
    }

    @Test
    @DisplayName("getTop/Bottom/Left/RightMostPos should reflect correct bounds")
    void testBoundaries() {
        assertAll("boundaries",
                () -> assertEquals(2, ship.getTopMostPos()),
                () -> assertEquals(2, ship.getBottomMostPos()),
                () -> assertEquals(3, ship.getLeftMostPos()),
                () -> assertEquals(5, ship.getRightMostPos())
        );
    }

    @Test
    @DisplayName("getPositions and getSize should be consistent")
    void testPositionsAndSize() {
        assertEquals(ship.getSize().intValue(), ship.getPositions().size());
        assertTrue(ship.getPositions().get(0) instanceof IPosition);
    }
}
