package datastructures;

import model.Vehicle;

public class BSTNode {
    public Vehicle vehicle;
    public BSTNode left, right;

    public BSTNode(Vehicle v) {
        this.vehicle = v;
    }
}