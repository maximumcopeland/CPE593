/*
    ExpressionTree.java
    Author: Max Copeland
    "I pledge my honor that I have abided by the Stevens Honor System"
    Borrowing node skeleton code from Dov Kruger's Github, isNumeric function from Geeksforgeeks
    This code is garbage and barely works but it's late and I want to die
*/
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Stack;

public class ExpressionTree {
    private static class Node {
        // Class for tree Nodes
		public String val;
        public Node left, right;
        
        public Node(String v) {
            // Simple constructor
            val = v;
            left = null;
            right = null;
        }

		public Node(String v, Node L, Node R) {
            // Constructor that specifies left and right child
            val = v; 
            left = L; 
            right = R;
        }

		public void inorder() {
            // Inorder traversal
			if (left != null)
				left.inorder();
			System.out.print(val + " ");
			if (right != null)
				right.inorder();
		}
		public void preorder() {
            // Preorder traversal
			System.out.print(val + " ");
			if (left != null)
				left.preorder();
			if (right != null)
				right.preorder();
		}
		public void postorder() {
            // Postorder Traversal
			if (left != null)
				left.postorder();
			if (right != null)
				right.postorder();
			System.out.print(val + " ");
		}
    }
    
    private Node root; // Root of tree
    
    public ExpressionTree(Node r) {
        // Constructor for tree
		root = r;
    }

    public Node getRoot() {
        return root;
    }

    static boolean isOperator(String s) {
        char v = s.charAt(0);
        if (v == '+' || v == '-' || v == '*' || v == '/') {
            return true;
        }
        return false;
    }

    static boolean isNumeric(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    static String compute(String o, String i, String j) {
        // Compute the operator o of i and j
        char p = o.charAt(0); // Obtain the operator
        String strRes = "";
        if (!isNumeric(i)) {
            // Evaluating variables
            switch (p) {
                case '+':
                    strRes = ("2" + i);
                    break;
                case '-':
                    strRes = ("0");
                    break;
            }
            return strRes;
        }
        int n = Integer.parseInt(i);
        int m = Integer.parseInt(j);
        int res = 0;
        switch (p) {
            case '+': 
                res = n + m;
                break;
            case '-': 
                res = n - m;
                break;
            case '*': 
                res = n * m;
                break;
            case '/': 
                res = n / m;
                break;
        }
        return Integer.toString(res);
    }

    static public Node optimize(Node r) {
        // Optimize the given node
        if (r.left == null && r.right == null) {
            return r;
        }
        Node left = optimize(r.left);
        Node right = optimize(r.right);
        if (isOperator(r.val)) {
            if (!isNumeric(left.val) || !isNumeric(right.val)) {
                // At least one of the nodes is a variable
                if (left.val.equals(right.val)) {
                    // Variables can still be evaluated
                    return new Node(compute(r.val, left.val, right.val));
                }
                else {
                    return new Node(left.val + r.val + right.val);
                }
            }
            // Evaluate the children
            return new Node(compute(r.val, left.val, right.val));
        }
        return r;
    }

    public void preorder() {
        // Preorder traversal for the Expression Tree
        root.preorder();
        System.out.println();
    }

    public void inorder() {
        // Inorder traversal for the Expression Tree
        root.inorder();
        System.out.println();
    }

    public void postorder() {
        // Postorder traversal for the Expression Tree
        root.postorder();
        System.out.println();
    }
    
    public static void main(String[] args) throws IOException {
        Stack<Node> stack = new Stack();
        Scanner data = new Scanner(new File("./hw8inp.dat"));
        int count = 0; // Keeping track of number of expression trees parsed
        
        // Evaluate each expression tree
        while (data.hasNextLine()) {
            ++count;
            // Parse through the file line by line
            System.out.println("Parsing Expression Tree #" + count + "...");
            String line = data.nextLine();
            //System.out.println(line);
            for (int i = 0; i < line.length(); i++) {
                // Parse through the line and construct the tree
                String element = "" + line.charAt(i);
                if (element.equals(" ")) {
                    continue;
                }
                //System.out.println(element + " ");
                if (isOperator(element)) {
                    Node right = stack.pop();
                    Node left = stack.pop();
                    stack.push(new Node(element, left, right));
                }
                else {
                    stack.push(new Node(element));
                }
            }
            ExpressionTree test = new ExpressionTree(stack.pop());
            System.out.println("Printing unoptimized trees...");
            System.out.print("Preorder: ");
            test.preorder();
            System.out.print("Inorder: ");
            test.inorder();
            System.out.print("Postorder: ");
            test.postorder();
            System.out.println("Optimizing Tree...");
            ExpressionTree opt = new ExpressionTree(optimize(test.getRoot()));
            System.out.println("Printing optimized tree...");
            System.out.print("Inorder: ");
            opt.inorder();
            System.out.println();
        }
        data.close();
    }
}