class AVLNode {
    int key;
    AVLNode left, right;
    int height = 1;

    AVLNode(int key) {
        this.key = key;
    }
}

static AVLNode rotateRight(AVLNode y) {
    AVLNode x = y.left;
    AVLNode T2 = x.right;

    x.right = y;
    y.left = T2;

    updateHeight(y);
    updateHeight(x);

    return x;
}

static AVLNode rotateLeft(AVLNode x) {
    AVLNode y = x.right;
    AVLNode T2 = y.left;

    y.left = x;
    x.right = T2;

    updateHeight(x);
    updateHeight(y);

    return y;
}

static AVLNode insert(AVLNode node, int key) {

    if(node == null)
        return new AVLNode(key);

    if(key < node.key)
        node.left = insert(node.left,key);
    else if(key > node.key)
        node.right = insert(node.right,key);
    else
        return node;

    updateHeight(node);

    int balance = balance(node);

    if(balance > 1 && key < node.left.key)
        return rotateRight(node);

    if(balance < -1 && key > node.right.key)
        return rotateLeft(node);

    if(balance > 1 && key > node.left.key) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    if(balance < -1 && key < node.right.key) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    return node;
}