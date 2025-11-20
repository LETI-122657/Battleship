package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Carrack NORTH: positions should be vertical")
    void testNorthPlacement() {
        Position p = new Position(2, 3);
        Carrack c = new Carrack(Compass.NORTH, p);

        assertEquals("Nau", c.getCategory());
        assertEquals(3, c.getSize());

        assertEquals(new Position(2, 3), c.getPositions().get(0));
        assertEquals(new Position(3, 3), c.getPositions().get(1));
        assertEquals(new Position(4, 3), c.getPositions().get(2));
    }

    @Test
    @DisplayName("Carrack SOUTH: positions should be vertical")
    void testSouthPlacement() {
        Position p = new Position(0, 0);
        Carrack c = new Carrack(Compass.SOUTH, p);

        assertEquals(new Position(0, 0), c.getPositions().get(0));
        assertEquals(new Position(1, 0), c.getPositions().get(1));
        assertEquals(new Position(2, 0), c.getPositions().get(2));
    }

    @Test
    @DisplayName("Carrack EAST: positions should be horizontal")
    void testEastPlacement() {
        Position p = new Position(5, 5);
        Carrack c = new Carrack(Compass.EAST, p);

        assertEquals(new Position(5, 5), c.getPositions().get(0));
        assertEquals(new Position(5, 6), c.getPositions().get(1));
        assertEquals(new Position(5, 7), c.getPositions().get(2));
    }

    @Test
    @DisplayName("Carrack WEST: positions should be horizontal")
    void testWestPlacement() {
        Position p = new Position(7, 2);
        Carrack c = new Carrack(Compass.WEST, p);

        assertEquals(new Position(7, 2), c.getPositions().get(0));
        assertEquals(new Position(7, 3), c.getPositions().get(1));
        assertEquals(new Position(7, 4), c.getPositions().get(2));
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for invalid bearing")
    void testInvalidBearing() {
        // Só funciona se Compass tiver valores fora do switch
        assertThrows(IllegalArgumentException.class,
                () -> new Carrack(Compass.valueOf("NORTHEAST"), new Position(0, 0))
        );
    }

    @Test
    @DisplayName("getSize should always return 3")
    void testSize() {
        Carrack c = new Carrack(Compass.NORTH, new Position(1, 1));
        assertEquals(3, c.getSize());
    }
}
