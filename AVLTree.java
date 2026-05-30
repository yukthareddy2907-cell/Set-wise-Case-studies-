class AVLNode {
    int key;
    int height;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

public class AVLTree {

    AVLNode root;

    int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);

        // RR Case
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR Case
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL Case
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    AVLNode deleteNode(AVLNode root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);

        else if (key > root.key)
            root.right = deleteNode(root.right, key);

        else {

            if (root.left == null || root.right == null) {

                AVLNode temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }

            } else {

                AVLNode temp = minValueNode(root.right);

                root.key = temp.key;

                root.right = deleteNode(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();

        int[] patientIDs = {
            20, 30, 35, 40, 45, 50,
            60, 65, 70, 75, 80, 85, 90
        };

        for (int id : patientIDs) {
            tree.root = tree.insert(tree.root, id);
        }

        System.out.println("AVL Tree After Insertions:");
        tree.inorder(tree.root);

        tree.root = tree.deleteNode(tree.root, 30);
        tree.root = tree.deleteNode(tree.root, 70);
        tree.root = tree.deleteNode(tree.root, 50);

        System.out.println("\n");
        System.out.println("AVL Tree After Deletions (30, 70, 50):");
        tree.inorder(tree.root);
    }
}