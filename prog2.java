//StringNode.java

//class String node
class StringNode {
    private String word;
    private StringNode left, right;


    public StringNode(String w) {
        word = w;
    }
    public String getString() {
        return word;
    }
    public StringNode getLeft() {
        return left;
    }
    public void setLeft(StringNode pt) {
        left = pt;
    }
    public StringNode getRight() {
        return right;
    }
    public void setRight(StringNode pt) {
        right = pt;
    }
}

//class wrap string
class WrapString {
    public String string;
    public WrapString(String string) {
        this.string = string;
    }
}

//class SplayBST
class SplayBST {

    StringNode root;

    public SplayBST() {
        root = null;
    }

    public SplayBST(SplayBST t) {
        this.root = copyBST(t.root);
    }

    private StringNode copyBST(StringNode rt) {
        StringNode temp = copyNode(rt);
        if(rt != null) {
            temp.setLeft(copyBST(rt.getLeft()));
            temp.setRight(copyBST(rt.getRight()));
        }
        return temp;
    }

    public static String myName() {
        return "ABC";
    }

    //insert method
    public void insert(String s) {
        root = insert(root,s);
    }

    private StringNode copyNode(StringNode rt) {
        StringNode temp = null;
        if(rt != null){
            temp = new StringNode(rt.getString());
        }

        return temp;
    }

    private StringNode insert(StringNode stringNode, String s) {
        if(stringNode == null) {
            stringNode = new StringNode(s);
            stringNode.setLeft(null);
            stringNode.setRight(null);
        } else {
            if(s.compareTo(stringNode.getString()) < 0) {
                stringNode.setLeft(insert(stringNode.getLeft(), s));
            } else if (s.compareTo(stringNode.getString()) > 0) {
                stringNode.setRight(insert(stringNode.getRight(), s));
            }
        }
        stringNode = splay(stringNode, s);
        return stringNode;
    }

    //method to search string
    public StringNode search(String string) {
        WrapString temp = new WrapString(string);
        StringNode previousStr = new StringNode(root.getString());
        root = search(temp, root,previousStr);
        StringNode tempNode = null;
        if(string.compareTo(temp.string) == 0) {
            tempNode = root;
        }
        return tempNode;
    }

    //method to search string node
    public StringNode search(WrapString item, StringNode currentNode, StringNode previousString) {
        if(currentNode == null) {
            item.string = previousString.getString();
        } else {
            if(currentNode.getString().compareTo(item.string) > 0) {
                currentNode.setLeft(search(item,currentNode.getLeft(),currentNode));
            } else if (currentNode.getString().compareTo(item.string) < 0) {
                currentNode.setRight(search(item,currentNode.getRight(),currentNode));
            } else {
                previousString = currentNode;
            }
        }

        currentNode = splay(currentNode, item.string);
        return currentNode;
    }
    public static StringNode rotateLeft(StringNode t) {
        StringNode tempNode = t.getRight();
        t.setRight(tempNode.getLeft());
        tempNode.setLeft(t);
        return tempNode;
    }
    public static StringNode rotateRight(StringNode t) {
        StringNode tempNode = t.getLeft();
        t.setLeft(tempNode.getRight());
        tempNode.setRight(t);
        return tempNode;
    }

    //method to return leaf count
    public int leafCt() {
        return leafCt(root);
    }
    private int leafCt(StringNode root) {
        if(root != null) {
            if(root.getLeft() == null && root.getRight() == null) {
                return 1;
            } else {
                return (leafCt(root.getLeft()) + leafCt(root.getRight()));
            }
        }
        return 0;
    }
    
    //method to return stick count
    public int stickCt(){
            return stickCtHelper(root);
        }
        private static int stickCtHelper(StringNode r) {
            if(r == null) return 0;
            if(r.getRight() != null && r.getLeft() == null) return 1 + stickCtHelper(r.getRight());
            else if(r.getRight() == null && r.getLeft() != null) return 1 + stickCtHelper(r.getLeft());
            else return stickCtHelper(r.getRight()) + stickCtHelper(r.getLeft());
        }
     

   //method to return height
    public int height() {
        return height(root);
    }

    private int height(StringNode stringNode) {
        int height = 0 , leftHeight = 0, rightHeight = 0;
        if(stringNode == null) {
            height = 0;
        } else {
            if(stringNode.getLeft() == null && stringNode.getRight() == null) {
                height = 0;
            } else {
                leftHeight = height(stringNode.getLeft());
                rightHeight = height(stringNode.getRight());
                if(leftHeight == rightHeight) {
                    height = 1 + leftHeight;
                } else if (leftHeight > rightHeight) {
                    height = 1 + leftHeight;
                } else {
                    height = 1 + rightHeight;
                }
            }
        }

        return height;
    }

    //method for splay string node
    private StringNode splay(StringNode stringNode, String string) {
        if(stringNode != null) {
            if(isGrandparent(stringNode, string)) {
                String rotations = getRotations(stringNode, string);
                if (rotations.compareTo("ll") == 0) {
                    stringNode = rotateRight(stringNode);
                    stringNode = rotateRight(stringNode);
                } else if (rotations.compareTo("rr") == 0) {
                    stringNode = rotateLeft(stringNode);
                    stringNode = rotateLeft(stringNode);
                } else if (rotations.compareTo("rl") == 0) {
                    stringNode.setRight(rotateRight(stringNode.getRight()));
                    stringNode = rotateLeft(stringNode);
                } else if (rotations.compareTo("lr") == 0) {
                    stringNode.setLeft(rotateLeft(stringNode.getLeft()));
                    stringNode = rotateRight(stringNode);
                }
                //only runs in the event that we are at the root and the item we need to splay is our child
            } else if(root == stringNode) {
                if(stringNode.getLeft() != null && stringNode.getLeft().getString() == string) {
                    stringNode = rotateRight(stringNode);
                } else if(stringNode.getRight() != null && stringNode.getRight().getString() == string) {
                    stringNode = rotateLeft(stringNode);
                }
            }
        }
        return stringNode;
    }

    private boolean isGrandparent(StringNode rt, String item) {
        boolean grandparent = false;
        if(nodeDistance(rt, item) == 2) {
            grandparent = true;
        }
        return grandparent;
    }

    //gives us the distance from the root given to a particular item
    private int nodeDistance(StringNode rt, String item) {
        int distance = 0;
        if(rt != null) {
            if(rt.getString() != item) {
                if(rt.getString().compareTo(item) > 0) {
                    distance = 1 + nodeDistance(rt.getLeft(), item);
                } else {
                    distance = 1 + nodeDistance(rt.getRight(), item);
                }
            }
        }
        return distance;
    }


    //rotation of string
    private String getRotations(StringNode stringNode, String string) {
        String rotate = "";
        if(stringNode != null) {
            if(stringNode.getString() != string) {
                if(stringNode.getString().compareTo(string) > 0) {
                    rotate = "l" + getRotations(stringNode.getLeft(), string);
                } else {
                    rotate = "r" + getRotations(stringNode.getRight(), string);
                }
            }
        }
        return rotate;
    }


    //method to check non null children
    public int nonNullChildren() {
        return nonNullChildren(root);
    }

    //non null children
    private int nonNullChildren(StringNode stringNode) {
        int count = 0;
        if(stringNode != null) {
            if(childCount(stringNode) == 1) {
                count++;
            }
            count += nonNullChildren(stringNode.getLeft()) + nonNullChildren(stringNode.getRight());
        }

        return count;
    }

    //count child count
    private int childCount(StringNode stringNode) {
        int count = 0;
        if(stringNode != null) {
            if(stringNode.getLeft() != null) {
                count++;
            }
            if(stringNode.getRight() != null) {
                count++;
            }
        }

        return count;
    }
}