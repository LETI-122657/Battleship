package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IPositionTest {

    private IPosition pos;

    @BeforeEach
    void setUp() {
        // Usa a implementação concreta Position
        pos = new Position(3, 5);
    }

    @Test
    void testGetRowAndColumn() {
        assertEquals(3, pos.getRow());
        assertEquals(5, pos.getColumn());
    }

    @Test
    void testOccupyAndIsOccupied() {
        assertFalse(pos.isOccupied());
        pos.occupy();
        assertTrue(pos.isOccupied());
    }

    @Test
    void testShootAndIsHit() {
        assertFalse(pos.isHit());
        pos.shoot();
        assertTrue(pos.isHit());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(pos, pos);
    }

    @Test
    void testEqualsDifferentObjectsSameCoordinates() {
        IPosition other = new Position(3, 5);
        assertEquals(pos, other);
        assertEquals(other, pos); // simetria
    }

    @Test
    void testEqualsDifferentObjectsDifferentCoordinates() {
        IPosition other = new Position(2, 5);
        assertNotEquals(pos, other);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(pos, null);
    }

    @Test
    void testEqualsWithOtherType() {
        assertNotEquals(pos, "not a position");
    }

    @Test
    void testIsAdjacentTo() {
        IPosition same = new Position(3, 5);
        IPosition rowPlus1 = new Position(4, 5);
        IPosition rowMinus1 = new Position(2, 5);
        IPosition colPlus1 = new Position(3, 6);
        IPosition colMinus1 = new Position(3, 4);
        IPosition diagonal = new Position(4, 6);
        IPosition far = new Position(5, 7);

        assertTrue(pos.isAdjacentTo(same));       // mesma posição
        assertTrue(pos.isAdjacentTo(rowPlus1));
        assertTrue(pos.isAdjacentTo(rowMinus1));
        assertTrue(pos.isAdjacentTo(colPlus1));
        assertTrue(pos.isAdjacentTo(colMinus1));
        assertTrue(pos.isAdjacentTo(diagonal));
        assertFalse(pos.isAdjacentTo(far));
    }
}
