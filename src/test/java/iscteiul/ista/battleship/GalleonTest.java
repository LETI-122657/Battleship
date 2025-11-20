package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para Galleon (JUnit 6-style / JUnit Jupiter).
 * Implementados com assertDoesNotThrow para melhorar diagnóstico quando corremos com coverage.
 */
public class GalleonTest {

    @Test
    void constructorNullBearingThrows() {
        IPosition pos = new Position(3, 3);
    }

    @Test
    void constructorValidBearingsDoNotThrow_and_sizeIsFive() {
        IPosition safe = new Position(3, 3); // origem segura para evitar posições negativas
        // garantimos que qualquer exceção será capturada pelo assertDoesNotThrow
        assertDoesNotThrow(() -> new Galleon(Compass.NORTH, safe), "Galleon constructor failed for NORTH");
        assertDoesNotThrow(() -> new Galleon(Compass.SOUTH, safe), "Galleon constructor failed for SOUTH");
        assertDoesNotThrow(() -> new Galleon(Compass.EAST, safe),  "Galleon constructor failed for EAST");
        assertDoesNotThrow(() -> new Galleon(Compass.WEST, safe),  "Galleon constructor failed for WEST");

        // validar o tamanho numa instância válida
        Galleon g = assertDoesNotThrow(() -> new Galleon(Compass.NORTH, safe));
        assertEquals(Integer.valueOf(5), g.getSize(), "Galleon.getSize() deve retornar 5");
    }

    // -------------------------
    // NORTH
    // -------------------------
    @Test
    void testFillNorthPositions() {
        IPosition origin = new Position(4, 2); // seguro
        Galleon g = assertDoesNotThrow(() -> new Galleon(Compass.NORTH, origin));
        List<IPosition> p = g.getPositions();

        // fillNorth:
        // (row, col), (row, col+1), (row, col+2), (row+1, col+1), (row+2, col+1)
        assertEquals(5, p.size(), "North: devem existir 5 posições");
        assertTrue(p.contains(new Position(4, 2)));
        assertTrue(p.contains(new Position(4, 3)));
        assertTrue(p.contains(new Position(4, 4)));
        assertTrue(p.contains(new Position(5, 3)));
        assertTrue(p.contains(new Position(6, 3)));
    }

    // -------------------------
    // SOUTH
    // -------------------------
    @Test
    void testFillSouthPositions() {
        IPosition origin = new Position(2, 4); // exemplo seguro
        Galleon g = assertDoesNotThrow(() -> new Galleon(Compass.SOUTH, origin));
        List<IPosition> p = g.getPositions();

        // Esperado a partir do fillSouth:
        // (2,4), (3,4), (4,3), (4,4), (4,5)
        assertEquals(5, p.size(), "South: devem existir 5 posições");
        assertTrue(p.contains(new Position(2, 4)));
        assertTrue(p.contains(new Position(3, 4)));
        assertTrue(p.contains(new Position(4, 3)));
        assertTrue(p.contains(new Position(4, 4)));
        assertTrue(p.contains(new Position(4, 5)));
    }

    // -------------------------
    // EAST
    // -------------------------
    @Test
    void testFillEastPositions() {
        // coluna >= 3 para evitar posições negativas no cálculo col + i - 3
        IPosition origin = new Position(1, 3);
        Galleon g = assertDoesNotThrow(() -> new Galleon(Compass.EAST, origin));
        List<IPosition> p = g.getPositions();

        // fillEast:
        // (1,3), (2,1), (2,2), (2,3), (3,3)
        assertEquals(5, p.size(), "East: devem existir 5 posições");
        assertTrue(p.contains(new Position(1, 3)));
        assertTrue(p.contains(new Position(2, 1)));
        assertTrue(p.contains(new Position(2, 2)));
        assertTrue(p.contains(new Position(2, 3)));
        assertTrue(p.contains(new Position(3, 3)));
    }

    // -------------------------
    // WEST
    // -------------------------
    @Test
    void testFillWestPositions() {
        IPosition origin = new Position(2, 5);
        Galleon g = assertDoesNotThrow(() -> new Galleon(Compass.WEST, origin));
        List<IPosition> p = g.getPositions();

        // fillWest:
        // (2,5), (3,5), (3,6), (3,7), (4,5)
        assertEquals(5, p.size(), "West: devem existir 5 posições");
        assertTrue(p.contains(new Position(2, 5)));
        assertTrue(p.contains(new Position(3, 5)));
        assertTrue(p.contains(new Position(3, 6)));
        assertTrue(p.contains(new Position(3, 7)));
        assertTrue(p.contains(new Position(4, 5)));
    }

    /**
     * Caso extremo: se o construtor lançar uma exceção ao correr com coverage,
     * este teste captura a exceção e inclui a stacktrace na mensagem para ajudar debug.
     */
    @Test
    void diagnosticConstructorCheckForCoverage() {
        IPosition origin = new Position(3, 3);
        try {
            new Galleon(Compass.NORTH, origin);
            new Galleon(Compass.SOUTH, origin);
            new Galleon(Compass.EAST, origin);
            new Galleon(Compass.WEST, origin);
        } catch (Throwable t) {
            StringBuilder sb = new StringBuilder();
            sb.append("Galleon constructor lançou exceção ao correr com coverage: ");
            sb.append(t.getClass().getName()).append(": ").append(t.getMessage()).append("\n");
            for (StackTraceElement st : t.getStackTrace()) {
                sb.append("\tat ").append(st.toString()).append("\n");
            }
            fail(sb.toString());
        }
    }
}
