package com.nixal.ssobchenko.model;

import com.nixal.ssobchenko.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public class TreeOfVehicles<E extends Vehicle> {
    private static final Vehicle.ModelComparator MODEL_COMPARATOR = new Vehicle.ModelComparator();
    private static final Vehicle.PriceComparator PRICE_COMPARATOR = new Vehicle.PriceComparator();

    private Node<E> rootNode;

    private BigDecimal leftBranchTotalPrice;
    private BigDecimal rightBranchTotalPrice;

    private int leftOrRightBranch;


    public TreeOfVehicles() {
        rootNode = null;
        leftBranchTotalPrice = BigDecimal.ZERO;
        rightBranchTotalPrice = BigDecimal.ZERO;
    }

    public void add(E element) {
        if (rootNode != null) {
            leftOrRightBranch = (compareElements(element, rootNode) < 0) ? -1 : 1;
        }
        rootNode = addRecursive(rootNode, element);
    }

    private Node<E> addRecursive(Node<E> current, E element) {
        if (current == null) {
            if (leftOrRightBranch > 0) {
                rightBranchTotalPrice = rightBranchTotalPrice.add(element.getPrice());
            } else if (leftOrRightBranch < 0) {
                leftBranchTotalPrice = leftBranchTotalPrice.add(element.getPrice());
            }
            leftOrRightBranch = 0;
            return new Node<>(element);
        }

        if (compareElements(element, current) < 0) {
            current.setLeftChild(addRecursive(current.getLeftChild(), element));
        } else if (compareElements(element, current) > 0) {
            current.setRightChild(addRecursive(current.getRightChild(), element));
        } else {
            return current;
        }

        return current;
    }

    private int compareElements(E element, Node<E> node) {
        return MODEL_COMPARATOR.thenComparing(PRICE_COMPARATOR).compare(element, node.getElement());
    }

    public void showLeftBranchTotalPrice() {
        if (rootNode == null) {
            System.out.println("The tree is empty");
        } else {
            BigDecimal totalPrice = leftBranchTotalPrice.add(rootNode.getElement().getPrice());
            System.out.println("Left branch total price is " + totalPrice);
        }
    }

    public void showRightBranchTotalPrice() {
        if (rootNode == null) {
            System.out.println("The tree is empty");
        } else {
            BigDecimal totalPrice = rightBranchTotalPrice.add(rootNode.getElement().getPrice());
            System.out.println("Right branch total price is " + totalPrice);
        }
    }

    public void print() {
        System.out.println(traversePreOrder(rootNode));
    }

    @Getter
    @Setter
    private static class Node<E extends Vehicle> {
        E element;
        Node<E> leftChild;
        Node<E> rightChild;

        Node(E element) {
            this.element = element;
            leftChild = null;
            rightChild = null;
        }
    }

    private String traversePreOrder(Node<E> node) {

        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(node.getElement().getModel());

        String pointerRight = "└──";
        String pointerLeft = (node.getRightChild() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, node.getLeftChild(), node.getRightChild() != null);
        traverseNodes(sb, "", pointerRight, node.getRightChild(), false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer, Node<E> node, boolean hasRightChild) {
        if (node != null) {
            sb.append("\n")
                    .append(padding)
                    .append(pointer)
                    .append(node.getElement().getModel());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightChild) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRightChild() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeftChild(), node.getRightChild() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRightChild(), false);
        }
    }
}