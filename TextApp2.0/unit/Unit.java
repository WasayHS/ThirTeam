package unit;

import map.Position;

public class Unit {

    private Stats stats;
    private Position position;

    public Unit (int hp, int str, int mag, int def, int x, int y) {
        this.stats = new Stats(hp, str, mag, def);
        this.position = new Position(x, y);
    }

    public Unit (int hp, int str, int mag, int def, Position p) {
        this.stats = new Stats(hp, str, mag, def);
        this.position = p;
    }

    public Stats getStats() {
        return stats;
    }

    public Position getPosition() {
        return position;
    }

}
