//delSmall Driver
public int delSmall() {
    return delSmall(root);
}

//looping, it will keep deleting the smallest element of BST
private int delSmall(IntNode T) {
    if (T.getLeft() == null)
    {
     int result = T.getItem();
     IntNode p = T;
     T = T.getRight();
     delete(result);
     return result;
     
    }else{
    
        return delSmall(T.getLeft());
    }
}

//This is the delete helper method
private IntNode delete(IntNode root, int value) {
    if (root == null)
        return null;
    if (root.getItem() > value)
     {
        root.setLeft(delete(root.getLeft(), value));
    } else if (root.getItem() < value) {
        root.setRight(delete(root.getRight(), value));

    } else {
        if (root.getLeft() != null && root.getRight() != null) {
            IntNode temp = root;
            IntNode minNodeForRight = minimumElement(temp.getRight());
            insert((minNodeForRight.getItem()), root);
            root.setRight(delete(root.getRight(), minNodeForRight.getItem()));
        }
        else if (root.getLeft() != null) {
            root = root.getLeft();
        }
        else if (root.getRight() != null) {
            root = root.getRight();
        }
        else
            root = null;
    }
    return root;
}