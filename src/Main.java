import java.util.Random;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head; // head of the list

    // Insert at the beginning
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
    }

    // Insert in a sorted List
    public void insertSorted(int data) {
        Node newNode = new Node(data);
        if (head == null || head.data >= data) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.data < data) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    // Delete first node
    public void deleteFirstNode() {
        if (head != null) {
            head = head.next;
        }
    }

    // Delete last node
    public void deleteLastNode() {
        if (head == null || head.next == null) {
            head = null;
        } else {
            Node secondLast = head;
            while (secondLast.next.next != null) {
                secondLast = secondLast.next;
            }
            secondLast.next = null;
        }
    }

    // Delete a node by value (assumed sorted list for the purpose of this example)
    public void deleteByValue(int data) {
        if (head == null) return;

        if (head.data == data) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Split the list into two halves
    public void frontBackSplit(LinkedList front, LinkedList back) {
        if (head == null || head.next == null) {
            front.head = head;
            back.head = null;
            return;
        }

        Node slow = head;
        Node fast = head.next;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }

        front.head = head;
        back.head = slow.next;
        slow.next = null;
    }

    // Function to merge two sorted lists
    public static Node sortedMerge(Node a, Node b) {
        Node result = null;
        if (a == null) return b;
        if (b == null) return a;

        if (a.data <= b.data) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    // Function to sort a linked list using merge sort
    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        LinkedList front = new LinkedList();
        LinkedList back = new LinkedList();
        LinkedList temp = new LinkedList();
        temp.head = head;
        temp.frontBackSplit(front, back);

        front.head = mergeSort(front.head);
        back.head = mergeSort(back.head);

        return sortedMerge(front.head, back.head);
    }

    // Utility method to print the linked list
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        LinkedList listA = new LinkedList();
        LinkedList listB = new LinkedList();

        // Insert a bunch of random numbers into list
        for(int i = 1; i < 20; i++){
            // get a random number between 1-50
            int randomNumber = random.nextInt(50) + 1;
            listA.insertAtBeginning(randomNumber);
        }
        System.out.print("Unsorted List: \n");
        listA.printList();

        // Demonstrate inserting
        System.out.print("Insert 7 at start of the list: \n");
        listA.insertAtBeginning(7);
        listA.printList();
        System.out.print("Insert 3 at end of the list: \n");
        listA.insertAtEnd(3);
        listA.printList();

        // Demonstrate deletions
        System.out.println("Delete first node: \n");
        listA.deleteFirstNode();
        listA.printList();
        System.out.println("Delete last node: \n");
        listA.deleteLastNode();
        listA.printList();

        // Splitting the list into two halves
        LinkedList front = new LinkedList();
        LinkedList back = new LinkedList();
        listA.frontBackSplit(front, back);

        System.out.println("Front List Before Sorting: ");
        front.printList();

        System.out.println("Back List Before Sorting: ");
        back.printList();

        // Sort both halves
        front.head = LinkedList.mergeSort(front.head);
        back.head = LinkedList.mergeSort(back.head);

        // Merge both sorted halves into a single sorted list
        Node sortedListHead = LinkedList.sortedMerge(front.head, back.head);
        LinkedList sortedList = new LinkedList();
        sortedList.head = sortedListHead;

        System.out.println("Merged and Sorted List: ");
        sortedList.printList();

    }
}