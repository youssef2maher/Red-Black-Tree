public class RBNode<T> {

    /**
     *
     */
    private int value;
    private RBNode left,right,parent;
    private boolean red;				//true is red
    private boolean doubleBlack;



    RBNode(int v,boolean color) {
        value=v;	red=color;
        left=right=parent=null;
    }
    RBNode() {
        left=right=parent=null;
    }

    void setDoubleBlack(boolean black){
        doubleBlack=black;
    }

    void setLeft(RBNode n)
    {
        left=n;
    }

    void setRight(RBNode n)
    {
        right=n;
    }

    void setParent(RBNode n)
    {
        parent=n;
    }

    boolean getDoubleBlack(){
        return doubleBlack;
    }

    RBNode getLeft()
    {
        return left;
    }

    RBNode getRight()
    {
        return right;
    }

    RBNode getParent()
    {
        return parent;
    }

    void changeColor(boolean color)
    {
        red=color;
    }

    void changeColor()
    {
        if (red) {
            red = false;
        }

        else {
            red = true;
        }
    }

    boolean getColor()
    {
        return red;
    }

    void setValue(int v)
    {
        value=v;
    }

    int getValue()
    {
        return value;
    }
}