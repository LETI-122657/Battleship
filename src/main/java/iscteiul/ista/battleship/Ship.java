package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Abstract representation of a ship in the Battleship game.
 */
public abstract class Ship implements IShip {

    // Ship category constants
    private static final String GALEAO = "galeao";
    private static final String FRAGATA = "fragata";
    private static final String NAU = "nau";
    private static final String CARAVELA = "caravela";
    private static final String BARCA = "barca";

    /**
     * Factory method that creates the right subclass of Ship depending on category.
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        if (shipKind == null || bearing == null || pos == null) {
            return null;
        }

        switch (shipKind.toLowerCase()) {
            case BARCA:
                return new Barge(bearing, pos);
            case CARAVELA:
                return new Caravel(bearing, pos);
            case NAU:
                return new Carrack(bearing, pos);
            case FRAGATA:
                return new Frigate(bearing, pos);
            case GALEAO:
                return new Galleon(bearing, pos);
            default:
                return null;
        }
    }

    private final String category;
    private final Compass bearing;
    private final IPosition pos;
    protected final List<IPosition> positions;

    /**
     * Base constructor for ships.
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        this.category = Objects.requireNonNull(category, "Category cannot be null");
        this.bearing = Objects.requireNonNull(bearing, "Bearing cannot be null");
        this.pos = Objects.requireNonNull(pos, "Position cannot be null");
        this.positions = new ArrayList<>();
    }

    @Override
    public String getCategory() {
        return category;
    }

    public List<IPosition> getPositions() {
        return positions;
    }

    @Override
    public IPosition getPosition() {
        return pos;
    }

    @Override
    public Compass getBearing() {
        return bearing;
    }

    @Override
    public boolean stillFloating() {
        // Returns true if at least one position is NOT hit
        for (IPosition p : positions) {
            if (!p.isHit()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTopMostPos() {
        int top = positions.get(0).getRow();
        for (IPosition p : positions) {
            if (p.getRow() < top) {
                top = p.getRow();
            }
        }
        return top;
    }

    @Override
    public int getBottomMostPos() {
        int bottom = positions.get(0).getRow();
        for (IPosition p : positions) {
            if (p.getRow() > bottom) {
                bottom = p.getRow();
            }
        }
        return bottom;
    }

    @Override
    public int getLeftMostPos() {
        int left = positions.get(0).getColumn();
        for (IPosition p : positions) {
            if (p.getColumn() < left) {
                left = p.getColumn();
            }
        }
        return left;
    }

    @Override
    public int getRightMostPos() {
        int right = positions.get(0).getColumn();
        for (IPosition p : positions) {
            if (p.getColumn() > right) {
                right = p.getColumn();
            }
        }
        return right;
    }

    @Override
    public boolean occupies(IPosition pos) {
        Objects.requireNonNull(pos, "Position cannot be null");
        for (IPosition p : positions) {
            if (p.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tooCloseTo(IShip other) {
        Objects.requireNonNull(other, "Other ship cannot be null");
        for (IPosition op : other.getPositions()) {
            if (tooCloseTo(op)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tooCloseTo(IPosition pos) {
        Objects.requireNonNull(pos, "Position cannot be null");
        for (IPosition p : positions) {
            if (p.isAdjacentTo(pos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void shoot(IPosition pos) {
        Objects.requireNonNull(pos, "Position cannot be null");
        for (IPosition p : positions) {
            if (p.equals(pos)) {
                p.shoot();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", category, bearing, pos);
    }
}
