package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testConstructorAndGetters() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getRow());
        assertEquals(5, p.getColumn());
        assertFalse(p.isOccupied());
        assertFalse(p.isHit());
    }

    @Test
    void testOccupy() {
        Position p = new Position(1, 1);
        assertFalse(p.isOccupied());
        p.occupy();
        assertTrue(p.isOccupied());
    }

    @Test
    void testShoot() {
        Position p = new Position(2, 3);
        assertFalse(p.isHit());
        p.shoot();
        assertTrue(p.isHit());
    }

    @Test
    void testEqualsSameObject() {
        Position p = new Position(1, 2);
        assertEquals(p, p);
    }

    @Test
    void testEqualsDifferentObjectsSameCoordinates() {
        Position p1 = new Position(4, 4);
        Position p2 = new Position(4, 4);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEqualsDifferentCoordinates() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 2);
        assertNotEquals(p1, p2);
    }

    @Test
    void testEqualsWithNonPositionObject() {
        Position p = new Position(1, 1);
        Object other = new Object();
        assertNotEquals(p, other);
    }

    @Test
    void testIsAdjacentToTrueCases() {
        Position p = new Position(3, 3);
        assertTrue(p.isAdjacentTo(new Position(3, 4)));  // mesma linha, coluna +1
        assertTrue(p.isAdjacentTo(new Position(2, 3)));  // linha -1
        assertTrue(p.isAdjacentTo(new Position(2, 2)));  // diagonal
        assertTrue(p.isAdjacentTo(p));                   // a si próprio
    }

    @Test
    void testIsAdjacentToFalseCases() {
        Position p = new Position(3, 3);
        assertFalse(p.isAdjacentTo(new Position(5, 3)));  // diferença > 1
        assertFalse(p.isAdjacentTo(new Position(3, 5)));
        assertFalse(p.isAdjacentTo(new Position(1, 1)));
    }

    @Test
    void testToStringFormat() {
        Position p = new Position(4, 7);
        assertEquals("Linha = 4 Coluna = 7", p.toString());
    }
}
