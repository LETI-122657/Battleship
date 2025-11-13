package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Fleet_Test {

    @Test
    void newFleetStartsEmpty() {
        Fleet fleet = new Fleet();

        List<IShip> ships = fleet.getShips();
        assertNotNull(ships, "getShips() nunca deve devolver null");
        assertTrue(ships.isEmpty(), "Uma Fleet nova deve começar sem navios");
    }

    @Test
    void getShipsLikeOnEmptyFleetReturnsEmptyList() {
        Fleet fleet = new Fleet();

        List<IShip> galeoes = fleet.getShipsLike("Galeao");

        assertNotNull(galeoes, "getShipsLike() nunca deve devolver null");
        assertTrue(galeoes.isEmpty(), "Numa Fleet vazia não deve haver navios dessa categoria");
    }

    @Test
    void getFloatingShipsOnEmptyFleetReturnsEmptyList() {
        Fleet fleet = new Fleet();

        List<IShip> floating = fleet.getFloatingShips();

        assertNotNull(floating, "getFloatingShips() nunca deve devolver null");
        assertTrue(floating.isEmpty(), "Numa Fleet vazia não há navios a flutuar");
    }

    @Test
    void shipAtOnEmptyFleetReturnsNull() {
        Fleet fleet = new Fleet();

        // Como não temos implementação de IPosition aqui,
        // usamos simplesmente null: o método percorre a lista e devolve null.
        IShip ship = fleet.shipAt(null);

        assertNull(ship, "Numa Fleet vazia, shipAt() deve devolver null");
    }

    @Test
    void printStatusOnEmptyFleetDoesNotThrow() {
        Fleet fleet = new Fleet();

        assertDoesNotThrow(fleet::printStatus,
                "printStatus() não deve lançar exceção mesmo com Fleet vazia");
    }

    @Test
    void constantsHaveExpectedValues() {
        assertEquals(10, IFleet.BOARD_SIZE);
        assertEquals(10, IFleet.FLEET_SIZE);
    }
}
