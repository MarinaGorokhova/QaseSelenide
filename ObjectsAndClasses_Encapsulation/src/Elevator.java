public class Elevator {
    private int currentFloor;
    private final int minFloor;
    private  final int maxFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.currentFloor = 1;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown() {
       if (currentFloor == minFloor) {
           System.out.println("Ошибка");
           return;
       }
       currentFloor = currentFloor - 1;
    }

    public void moveUp() {
        if (currentFloor == maxFloor) {
            System.out.println("Ошибка");
            return;
        }
        currentFloor = currentFloor + 1;
    }
    public void move(int floor) {
        if (floor != currentFloor && floor >= minFloor && floor <= maxFloor) {
            int moveStep = floor > currentFloor ? 1 : -1;
            do {currentFloor += moveStep;
                System.out.println("Этаж: " + currentFloor);
            } while (floor != currentFloor);
        } else {
            System.out.println("Ошибка");
        }
    }
}
