import java.awt.geom.Rectangle2D;

public class Hero {

    private int x;
    private int y;
    private int originalY;
    private int[][] runningCoordinates;
    private boolean running;
    private int runningFastCount, runningCount;
    private int[][] jumpingCoordinates;
    private boolean jumping, falling;
    private int jumpingFastCount, jumpingCount;
    private boolean onBox;

    public Hero(int x, int y, int[][] runningCoordinates, int[][] jumpingCoordinates) {
        this.x = x;
        this.y = y;
        originalY = y;
        this.runningCoordinates = runningCoordinates;
        running = false;
        runningFastCount = 0;
        runningCount = 0;
        this.jumpingCoordinates = jumpingCoordinates;
        jumping = false;
        falling = false;
        jumpingFastCount = 0;
        jumpingCount = 0;
        onBox = false;
    }

    public int getX() {
        return x;
    }

    public int getOriginalColumn() {
        return (int)(x / 50.0);
    }

    public int getColumn() {
        return (getOriginalColumn() + (int)(runningFastCount / 50.0)) % 90;
    }

    public int getY() {
        return y;
    }

    public int getOriginalY() {
        return originalY;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (!this.running)
            runningCount = 0;
    }

    public int getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(int runningCount) {
        this.runningCount = runningCount;
    }

    public void setRunningCount() {
        runningFastCount++;
        if (runningFastCount % 25 == 0) {
            runningCount++;
            if (runningCount == runningCoordinates.length)
                runningCount = 1;
        }
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public int getJumpingCount() {
        return jumpingCount;
    }

    public void setJumpingCount(int jumpingCount) {
        this.jumpingCount = jumpingCount;
    }

    public void updateJumping() {
        jumpingFastCount++;
        y--;
        if (jumpingFastCount % 25 == 0) {
            jumpingCount++;
            if (jumpingCount == jumpingCoordinates.length / 2) {
                jumping = false;
                falling = true;
            }
        }
    }

    public void updateFalling() {
        if (y < originalY) {
            jumpingFastCount++;
            y++;
            if (jumpingFastCount % 25 == 0) {
                jumpingCount++;
                if (jumpingCount == jumpingCoordinates.length)
                    jumpingCount = 0;
            }
        }
        else {
            falling = false;
            jumpingCount = 0;
        }
    }

    public boolean isOnBox() {
        return onBox;
    }

    public void setOnBox(boolean onBox) {
        this.onBox = onBox;
    }

    public Rectangle2D getEdge(String name) {
        int x = 0, y = 0, width = 0, height = 0;
        switch (name) {
            case "top":
                x = this.x;
                y = this.y - 1;
                width = runningCoordinates[runningCount][2] * 2;
                height = 2;
                break;
            case "bottom":
                x = this.x;
                y = this.y + (runningCoordinates[0][3] * 2) - 1;
                width = runningCoordinates[runningCount][2] * 2;
                height = 2;
                break;
            case "right":
                x = this.x + ((jumping || falling) ? jumpingCoordinates[jumpingCount][2] * 2 : runningCoordinates[runningCount][2] * 2) - 1;
                y = this.y;
                width = 2;
                height = runningCoordinates[0][3] * 2;
                break;
            case "left":
                x = this.x - 1;
                y = this.y;
                width = 2;
                height = runningCoordinates[0][3] * 2;
                break;
            default:
                break;
        }
        return new Rectangle2D.Double(x, y, width, height);
    }

}
