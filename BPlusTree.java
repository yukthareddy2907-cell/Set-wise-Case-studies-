
class BPlusNode {
    boolean isLeaf;
    int[] keys;
    BPlusNode[] children;
    BPlusNode next;

    public BPlusNode(boolean isLeaf, int[] keys) {
        this.isLeaf = isLeaf;
        this.keys = keys;
        this.next = null;
    }
}

public class BPlusTree {

    // Find the leaf node that may contain the lower bound (lo)
    public static BPlusNode findLeaf(BPlusNode root, int lo) {
        BPlusNode current = root;

        while (!current.isLeaf) {
            int i = 0;

            while (i < current.keys.length && lo >= current.keys[i]) {
                i++;
            }

            current = current.children[i];
        }

        return current;
    }

    // Optimized Range Count Function
    public static int rangeCount(BPlusNode root, int lo, int hi) {

        if (root == null) {
            return 0;
        }

        BPlusNode leaf = findLeaf(root, lo);

        int count = 0;

        while (leaf != null) {

            for (int key : leaf.keys) {

                if (key > hi) {
                    return count;
                }

                if (key >= lo && key <= hi) {
                    count++;
                }
            }

            leaf = leaf.next;
        }

        return count;
    }

    public static void main(String[] args) {

        // Leaf Nodes
        BPlusNode leaf1 = new BPlusNode(true,
                new int[]{5, 10, 15, 20});

        BPlusNode leaf2 = new BPlusNode(true,
                new int[]{25, 30, 35, 40});

        BPlusNode leaf3 = new BPlusNode(true,
                new int[]{45, 50, 55, 60});

        // Link leaf chain
        leaf1.next = leaf2;
        leaf2.next = leaf3;

        // Root Node
        BPlusNode root = new BPlusNode(false,
                new int[]{25, 45});

        root.children = new BPlusNode[]{
                leaf1,
                leaf2,
                leaf3
        };

        int lo = 18;
        int hi = 52;

        int result = rangeCount(root, lo, hi);

        System.out.println("Range [" + lo + ", " + hi + "]");
        System.out.println("Number of keys = " + result);
    }
}