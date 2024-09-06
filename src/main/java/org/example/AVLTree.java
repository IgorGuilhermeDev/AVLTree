package org.example;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private class Node {
        Element element;
        Node left, right;
        int height;

        Node(Element element) {
            this.element = element;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(Element element) {
        root = insert(root, element);
    }

    private Node insert(Node node, Element element) {
        if (node == null) return new Node(element);

        if (element.value < node.element.value)
            node.left = insert(node.left, element);
        else if (element.value > node.element.value)
            node.right = insert(node.right, element);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node, element.value);
    }

    private Node balance(Node node, int key) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (key < node.left.element.value) return rotateRight(node);
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1) {
            if (key > node.right.element.value) return rotateLeft(node);
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void delete(Element element) {
        root = delete(root, element);
    }

    private Node delete(Node node, Element element) {
        if (node == null) return null;
        if (element.value < node.element.value) {
            node.left = delete(node.left, element);
        } else if (element.value > node.element.value) {
            node.right = delete(node.right, element);
        } else {
            if (node.left == null || node.right == null) {

                node = (node.left != null) ? node.left : node.right;
            } else {

                Node temp = maxValueNode(node.left);
                node.element = temp.element;
                node.left = delete(node.left, temp.element);
            }
        }

        if (node == null) return null;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node, element.value);
    }

    private Node maxValueNode(Node node) {
        while (node.right != null) node = node.right;
        return node;
    }

    private Node minValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        List<List<Node>> levels = new ArrayList<>();
        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        levels.add(currentLevel);

        while (true) {
            List<Node> nextLevel = new ArrayList<>();
            boolean hasMoreNodes = false;

            for (Node node : currentLevel) {
                if (node != null) {
                    nextLevel.add(node.left);
                    nextLevel.add(node.right);
                    if (node.left != null || node.right != null) {
                        hasMoreNodes = true;
                    }
                } else {
                    nextLevel.add(null);
                    nextLevel.add(null);
                }
            }

            if (!hasMoreNodes) break;
            levels.add(nextLevel);
            currentLevel = nextLevel;
        }

        int maxLevel = levels.size();
        int maxNodesInLevel = (int) Math.pow(2, maxLevel - 1);
        int nodeWidth = 3;
        int fullWidth = maxNodesInLevel * (nodeWidth + 4);

        for (int i = 0; i < maxLevel; i++) {
            List<Node> level = levels.get(i);
            int levelNodeCount = level.size();
            int spaceBetween = fullWidth / levelNodeCount;

            printLevelNodes(level, spaceBetween, nodeWidth);

            if (i < maxLevel - 1) {
                printLevelSlashes(level, spaceBetween, nodeWidth);
            }
        }
    }

    private void printLevelNodes(List<Node> level, int spaceBetween, int nodeWidth) {
        for (Node node : level) {
            if (node != null) {
                System.out.print(centerString(String.valueOf(node.element.value), spaceBetween));
            } else {
                System.out.print(centerString("", spaceBetween));
            }
        }
        System.out.println();
    }

    private void printLevelSlashes(List<Node> level, int spaceBetween, int nodeWidth) {
        for (Node node : level) {
            String leftSlash = node != null && node.left != null ? "/" : " ";
            String rightSlash = node != null && node.right != null ? "\\" : " ";
            System.out.print(centerString(leftSlash + rightSlash, spaceBetween));
        }
        System.out.println();
    }

    private String centerString(String str, int width) {
        int padding = width - str.length();
        int padLeft = padding / 2;
        int padRight = padding - padLeft;
        return " ".repeat(padLeft) + str + " ".repeat(padRight);
    }
}
