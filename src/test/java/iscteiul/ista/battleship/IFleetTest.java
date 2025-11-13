package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    private IFleet fleet;

    @BeforeEach
    void setUp() {
        // TODO: se a tua implementação tiver outro nome, substitui "Fleet" por esse nome
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
        List<IShip> ships = fleet.getShips();
        assertNotNull(ships, "getShips() nunca deve devolver null");
        assertTrue(ships.isEmpty(), "Uma fleet nova deve começar sem navios");

        assertNotNull(fleet.getShipsLike("any"),
                "getShipsLike() nunca deve devolver null");
        assertTrue(fleet.getShipsLike("any").isEmpty(),
                "getShipsLike() deve ser vazia numa fleet nova");

        assertNotNull(fleet.getFloatingShips(),
                "getFloatingShips() nunca deve devolver null");
        assertTrue(fleet.getFloatingShips().isEmpty(),
                "getFloatingShips() deve ser vazia numa fleet nova");
    }

    @Test
    @DisplayName("printStatus() não deve lançar exceções numa fleet vazia")
    void printStatusOnEmptyFleetDoesNotThrow() {
        assertDoesNotThrow(() -> fleet.printStatus());
    }
}
