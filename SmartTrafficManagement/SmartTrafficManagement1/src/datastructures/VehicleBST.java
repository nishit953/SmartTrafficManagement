package datastructures;

import model.Vehicle;

public class VehicleBST {
    private BSTNode root;
    private int size;

    public void insert(Vehicle v) {
        root = insertRec(root, v);
        size++;
    }

    private BSTNode insertRec(BSTNode node, Vehicle v) {
        if (node == null)
            return new BSTNode(v);
        int cmp = v.getVehicleNumber().compareTo(node.vehicle.getVehicleNumber());
        if (cmp < 0)
            node.left = insertRec(node.left, v);
        else
            node.right = insertRec(node.right, v);
        return node;
    }

    public boolean contains(String vehicleNumber) {
        return searchRec(root, vehicleNumber);
    }

    private boolean searchRec(BSTNode node, String n) {
        if (node == null) return false;
        int cmp = n.compareTo(node.vehicle.getVehicleNumber());
        if (cmp == 0)
            return true;
        return (cmp < 0) ? searchRec(node.left, n) : searchRec(node.right, n);
    }

    public boolean delete(String vehicleNumber) {
        root = deleteRec(root, vehicleNumber);
        return false;
    }

    private BSTNode deleteRec(BSTNode node, String n) {
        if (node == null)
            return null;
        int cmp = n.compareTo(node.vehicle.getVehicleNumber());
        if (cmp < 0)
            node.left = deleteRec(node.left, n);
        else if (cmp > 0)
            node.right = deleteRec(node.right, n);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            BSTNode min = node.right;
            while (min.left != null)
                min = min.left;
            node.vehicle = min.vehicle;
            node.right = deleteRec(node.right, min.vehicle.getVehicleNumber());
        }
        return node;
    }

    public int size() {
        return size;
    }

    public int toInOrderArray(Vehicle[] out, int startIndex) {
        Index idx = new Index(startIndex);
        inOrder(root, out, idx);
        return idx.value - startIndex;
    }

    private void inOrder(BSTNode node, Vehicle[] out, Index idx) {
        if (node == null)
            return;
        inOrder(node.left, out, idx);
        if (idx.value < out.length) out[idx.value++] = node.vehicle;
        inOrder(node.right, out, idx);
    }

    private static class Index {
        int value;
        Index(int v) {
            this.value = v;
        }
    }
}