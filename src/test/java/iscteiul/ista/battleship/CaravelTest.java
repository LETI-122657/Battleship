package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    @Test
    @DisplayName("Caravel NORTH: positions should be vertical")
    void testNorthPlacement() {
        Position p = new Position(5, 5);
        Caravel c = new Caravel(Compass.NORTH, p);

        assertEquals(2, c.getSize());
        assertEquals("Caravela", c.getCategory());

        assertEquals(new Position(5, 5), c.getPositions().get(0));
        assertEquals(new Position(6, 5), c.getPositions().get(1));
    }

    @Test
    @DisplayName("Caravel SOUTH: positions should be vertical")
    void testSouthPlacement() {
        Position p = new Position(2, 1);
        Caravel c = new Caravel(Compass.SOUTH, p);

        assertEquals(new Position(2, 1), c.getPositions().get(0));
        assertEquals(new Position(3, 1), c.getPositions().get(1));
    }

    @Test
    @DisplayName("Caravel EAST: positions should be horizontal")
    void testEastPlacement() {
        Position p = new Position(1, 1);
        Caravel c = new Caravel(Compass.EAST, p);

        assertEquals(new Position(1, 1), c.getPositions().get(0));
        assertEquals(new Position(1, 2), c.getPositions().get(1));
    }

    @Test
    @DisplayName("Caravel WEST: positions should be horizontal")
    void testWestPlacement() {
        Position p = new Position(3, 3);
        Caravel c = new Caravel(Compass.WEST, p);

        assertEquals(new Position(3, 3), c.getPositions().get(0));
        assertEquals(new Position(3, 4), c.getPositions().get(1));
    }

    @Test
    @DisplayName("Constructor must throw NullPointerException if bearing is null")
    void testNullBearing() {
        assertThrows(NullPointerException.class,
                () -> new Caravel(null, new Position(0, 0))
        );
    }

    // Usa apenas se o enum Compass tiver valores não tratados no switch.
    // Se não tiver, podes remover este teste.
    @Test
    @DisplayName("Constructor must throw IllegalArgumentException if bearing is invalid")
    void testInvalidBearing() {
        // Força um bearing inválido por reflexão OU enum alterado
        assertThrows(IllegalArgumentException.class,
                () -> new Caravel(Compass.valueOf("NORTHEAST"), new Position(0, 0))
        );
    }

    @Test
    @DisplayName("getSize must always return 2")
    void testSize() {
        Caravel c = new Caravel(Compass.NORTH, new Position(0, 0));
        assertEquals(2, c.getSize());
    }
}
