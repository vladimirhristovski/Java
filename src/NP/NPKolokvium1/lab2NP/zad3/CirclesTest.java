package NP.NPKolokvium1.lab2NP.zad3;

import java.util.Arrays;
import java.util.Scanner;

enum TYPE {
    POINT, CIRCLE
}

enum DIRECTION {
    UP, DOWN, LEFT, RIGHT
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();

    TYPE getType();
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

class MovablePoint implements Movable {
    private final int xSpeed;
    private final int ySpeed;
    private int x;
    private int y;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    private void tryMoving(int x, int y) throws ObjectCanNotBeMovedException {
        if (this.x + x > MovablesCollection.getX_MAX() || this.x + x < 0 || this.y + y > MovablesCollection.getY_MAX() || this.y + y < 0) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x + x, this.y + y));
        }
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        tryMoving(0, ySpeed);
        y += ySpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        tryMoving(0, -ySpeed);
        y -= ySpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        tryMoving(xSpeed, 0);
        x += xSpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        tryMoving(-xSpeed, 0);
        x -= xSpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public TYPE getType() {
        return TYPE.POINT;
    }

    @Override
    public String toString() {
        return "Movable point with coordinates (" + x + "," + y + ")";
    }
}

class MovableCircle implements Movable {
    private final int radius;
    MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        center.moveLeft();
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    @Override
    public TYPE getType() {
        return TYPE.CIRCLE;
    }

    @Override
    public String toString() {
        return "Movable circle with center coordinates " + "(" + center.getCurrentXPosition() + "," + center.getCurrentYPosition() + ") and radius " + radius;
    }

    public int getRadius() {
        return radius;
    }
}

class MovablesCollection {
    private static int x_MAX = 0;
    private static int y_MAX = 0;
    private Movable[] movable;
    private int n = 0;

    public MovablesCollection(int xMAX, int yMAX) {
        x_MAX = xMAX;
        y_MAX = yMAX;
        movable = new Movable[n];
    }

    public static void setxMax(int i) {
        x_MAX = i;
    }

    public static void setyMax(int i) {
        y_MAX = i;
    }

    public static int getX_MAX() {
        return x_MAX;
    }

    public static int getY_MAX() {
        return y_MAX;
    }

    public boolean canFit(Movable m) {
        int x = m.getCurrentXPosition();
        int y = m.getCurrentYPosition();
        int r = 0;
        if (m.getType() == TYPE.CIRCLE) {
            r = ((MovableCircle) m).getRadius();
        }
        return x - r >= 0 && x + r <= MovablesCollection.x_MAX && y - r >= 0 && y + r <= MovablesCollection.y_MAX;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (!canFit(m)) {
            if (m.getType() == TYPE.POINT) {
                throw new MovableObjectNotFittableException(String.format("Movable point with center (%d,%d) can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition()));
            } else {
                throw new MovableObjectNotFittableException(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition(), ((MovableCircle) m).getRadius()));
            }
        }
        movable = Arrays.copyOf(movable, n + 1);
        movable[n++] = m;
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (int i = 0; i < n; i++) {
            if (movable[i].getType() == type) {
                if (direction == DIRECTION.UP) {
                    try {
                        movable[i].moveUp();
                    } catch (ObjectCanNotBeMovedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (direction == DIRECTION.DOWN) {
                    try {
                        movable[i].moveDown();
                    } catch (ObjectCanNotBeMovedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (direction == DIRECTION.LEFT) {
                    try {
                        movable[i].moveLeft();
                    } catch (ObjectCanNotBeMovedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (direction == DIRECTION.RIGHT) {
                    try {
                        movable[i].moveRight();
                    } catch (ObjectCanNotBeMovedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection of movable objects with size ").append(n).append(":").append("\n");
        for (Movable object : movable) {
            sb.append(object.toString()).append("\n");
        }
        return sb.toString();
    }
}

public class CirclesTest {
    public static void main(String[] args) {
        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                try {
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                try {
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        System.out.println(collection);

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection);

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection);

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection);

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection);
    }
}