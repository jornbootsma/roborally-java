import java.util.ArrayList;
import java.util.Arrays;

public class Robot {

    // Class variables
    private static int xDefault = 0;
    private static int yDefault = 0;

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private Direction facingDefault = Direction.NORTH;

    private String turnLeft = "l";
    private String turnRight = "r";
    private String forward = "f";
    private String backward = "b";

    private int defaultSpeed = 1;
    private int minSpeed = 1;
    private int maxSpeed = 3;

    private int x;
    private int y;
    private int facingIndex;
    private Direction[] facingDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private ArrayList<String> commands = new ArrayList<String>();

    // Default constructor
    Robot() {
        this.x = xDefault;
        this.y = yDefault;
        this.facingIndex = Arrays.asList(facingDirections).indexOf(facingDefault);
    }
    
    // Specified constructor
    Robot(int x, int y, Direction facing) {
        this.x = x;
        this.y = y;
        
        // Check if the given facing is legal. Otherwise, take default.
        if (!Arrays.asList(facingDirections).contains(facing)) {
            facing = facingDefault;
        }
        
        this.facingIndex = Arrays.asList(facingDirections).indexOf(facing);
    }

    /**
     * Update the facing index. In total there are 4 different facing possibilities.
     * In this program index 0 indicates "North", index 1 indicates "East", index 2
     * indicates "South" and index 3 indicates "West".
     * Dependent on whether you turn left or right a different parameter "a" is given as input.
     * 
     * "a" > 0 means turning right. "a" < 0 means turning left.
     * 
     * When the facing index is updated, it is corrected based on the length of the facing direction.
     * E.g. when you have 4 different directions a facing index of -1 is modified to be 3 (the last index of the list).
     * In the same way, a facing index of 4 is modified to be 0 (the first index of the list).
     * @param a
     */
    public void updateFacingIndex(int a) {
        facingIndex = facingIndex + a;
        if (facingIndex < 0) {
            facingIndex = facingDirections.length - 1;
        } else if (facingIndex > facingDirections.length - 1) {
            facingIndex = 0;
        }
    }

    /**
     * Add the command to turn left.
     */
    public void goLeft() {
        this.commands.add(turnLeft);
    }

    /**
     * Execute a left turn.
     */
    public void turnLeft() {
        updateFacingIndex(-1);
    }

    /**
     * Add the command to turn right.
     */
    public void goRight() {
        this.commands.add(turnRight);
    }

    /**
     * Execute a right turn.
     */
    public void turnRight() {
        updateFacingIndex(1);
    }

    /**
     * Add the command to move forward. The parameters "steps" determines
     * the speeds of this move.
     * @param steps
     */
    public void goForward(int steps) {
        if (minSpeed <= steps && steps <= maxSpeed) {
            String command = forward + steps;
            this.commands.add(command);
        } else {
            printSpeedError();
        }
    }

    /**
     * Calls the function goForward(steps) to add the command to move forward with the default speed.
     */
    public void goForward() {
        goForward(defaultSpeed);
    }

    /**
     * Execute a move forward while taking the step size into account.
     * @param steps
     */
    public void forward(int steps) {
        if (Direction.values()[facingIndex].equals(Direction.NORTH)) {
            y += steps;
        } else if (Direction.values()[facingIndex].equals(Direction.SOUTH)) {
            y -= steps;
        } else if (Direction.values()[facingIndex].equals(Direction.EAST)) {
            x += steps;
        } else if (Direction.values()[facingIndex].equals(Direction.WEST)) {
            x -= steps;
        }
    }

    /**
     * Add the command to move backward. The parameters "steps" determines
     * the speeds of this move.
     * @param steps
     */
    public void goBackward(int steps) {
        if (minSpeed <= steps && steps <= maxSpeed) {
            String command = backward + steps;
            this.commands.add(command);
        } else {
            printSpeedError();
        }
    }

    /**
     * Calls the function goBackward(steps) to add the command to move backward with the default speed.
     */
    public void goBackward() {
        goBackward(defaultSpeed);
    }

    /**
     * Execute a move backward while taking the step size into account.
     * @param steps
     */
    public void backward(int steps) {
        if (Direction.values()[facingIndex].equals(Direction.NORTH)) {
            y -= steps;
        } else if (Direction.values()[facingIndex].equals(Direction.SOUTH)) {
            y += steps;
        } else if (Direction.values()[facingIndex].equals(Direction.EAST)) {
            x -= steps;
        } else if (Direction.values()[facingIndex].equals(Direction.WEST)) {
            x += steps;
        }
    }

    /**
     * Print an error if the given step size is not legal.
     */
    public void printSpeedError() {
        System.out.println("The given speed is not legal. The step size can only range from " + minSpeed + " up to " +  maxSpeed);
    }

    /**
     * Print the current state of the robot.
     */
    public void printState() {
        System.out.println("\nNow facing \"" + Direction.values()[facingIndex] + "\" at (" + x + "," + y + ")\n");
    }

    /**
     * Execute the entire list of commands.
     * 
     * Note: the command for a forward move is for example defined as "f2". The "f" says
     * that it is a forward move. The "2" indicates the step size of this move. This is the
     * reason that this command is split in two substrings. The same holds for the backward move.
     */
    public void execute() {
        for (String command : commands) {
            if (command.equals(turnLeft)) {
                turnLeft();
            } else if (command.equals(turnRight)) {
                turnRight();
            } else if (Character.toString(command.charAt(0)).equals(forward)) {
                int steps = Integer.parseInt(command.substring(1));
                forward(steps);
            } else if (Character.toString(command.charAt(0)).equals(backward)) {
                int steps = Integer.parseInt(command.substring(1));
                backward(steps);
            }
        }
    }

    public static void main(String[] args) {
        Robot myFirstRobot = new Robot(2, 5, Direction.WEST);

        myFirstRobot.goRight();
        myFirstRobot.goForward();
        myFirstRobot.goRight();
        myFirstRobot.goBackward();

        myFirstRobot.execute();
        myFirstRobot.printState();
    }
}
