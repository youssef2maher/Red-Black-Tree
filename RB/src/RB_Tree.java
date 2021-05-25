public class RB_Tree{
    private RBNode root;

    RBNode search(int key, RBNode toFind)
    {
        if(toFind==null)
            return null;
        if(key==toFind.getValue())
            return toFind;

        else if(toFind.getValue()<key)
            return search(key,toFind.getRight());
        else return search(key,toFind.getLeft());
    }


    RBNode predecessor(RBNode n){
        if(n!=null && n.getLeft()!=null)
            n=n.getLeft();
        while(n.getRight()!=null)
            n=n.getRight();
        return n;
    }

    boolean doubleBlack(RBNode doubleBlacked,RBNode sibling)
    {
        RBNode temp;
        if(sibling==null){            						//two children are black (case 2)

            if(doubleBlacked.getParent().getColor())
                doubleBlacked.getParent().changeColor(false);
            else if(doubleBlacked.getParent()!=root)
            {
                doubleBlacked=doubleBlacked.getParent();
                RBNode parent=doubleBlacked.getParent();
                if(doubleBlacked==parent.getLeft())
                    sibling=parent.getRight();
                else sibling=parent.getRight();
                //doubleBlack(doubleBlacked,sibling);
            }

            return true;
        }


        else if(!sibling.getColor())							//sibling is black
        {
            boolean redChild1=false,redChild2=false;

            if(sibling.getLeft()!=null)
                if(sibling.getLeft().getColor())
                    redChild1=true;
            if(sibling.getRight()!=null)
                if(sibling.getRight().getColor())
                    redChild2=true;

            //case 1
            if(redChild1 || redChild2) {
                if(sibling==sibling.getParent().getLeft()) {
                    if(sibling!=predecessor(sibling.getParent()))		//هبد touch
                    {
                        RBNode pre=predecessor(sibling.getParent());
                        temp=pre.getLeft();

                        pre.getParent().setRight(temp);
                        if(temp!=null)
                            temp.setParent(pre.getParent());

                        pre.setParent(sibling.getParent().getParent());
                        if(sibling.getParent()==sibling.getParent().getParent().getLeft())
                            sibling.getParent().getParent().setLeft(pre);
                        else
                            sibling.getParent().getParent().setRight(pre);

                        pre.setLeft(sibling);
                        pre.setRight(sibling.getParent());
                        pre.getLeft().setParent(pre);
                        pre.getRight().setParent(pre);
                        pre.getRight().setLeft(null);

                        //re-coloring
                        pre.changeColor(doubleBlacked.getParent().getColor());
                        if(pre.getLeft()!=null)
                            pre.getLeft().changeColor(false);
                        if(pre.getRight()!=null)
                            pre.getRight().changeColor(false);

                        return true;
                    }
                    boolean left=false;
                    if(sibling.getParent()==sibling.getParent().getParent().getLeft())
                        left=true;

                    temp=sibling.getRight();
                    sibling.setParent(sibling.getParent().getParent());
                    sibling.setRight(doubleBlacked.getParent());
                    sibling.getRight().setParent(sibling);
                    sibling.getRight().setLeft(temp);

                    if(temp!=null)
                        temp.setParent(sibling.getRight());

                    if(sibling.getParent()==null)
                        root=sibling;
                    else if(left)
                        sibling.getParent().setLeft(sibling);
                    else
                        sibling.getParent().setRight(sibling);

                    doubleBlacked.getParent().setParent(sibling);

                    //re-coloring
                    sibling.changeColor(doubleBlacked.getParent().getColor());
                    if(sibling.getLeft()!=null)
                        sibling.getLeft().changeColor(false);
                    if(sibling.getRight()!=null)
                        sibling.getRight().changeColor(false);
                }
                else
                {
                    boolean left=false;
                    if(sibling.getParent().getParent()!=null)
                        if(sibling.getParent()==sibling.getParent().getParent().getLeft())
                            left=true;

                    temp=sibling.getLeft();
                    sibling.setParent(sibling.getParent().getParent());
                    sibling.setLeft(doubleBlacked.getParent());
                    sibling.getLeft().setParent(sibling);
                    sibling.getLeft().setRight(temp);

                    if(temp!=null)
                        temp.setParent(sibling.getLeft());

                    if(sibling.getParent()==null)
                        root=sibling;
                    else if(left)
                        sibling.getParent().setLeft(sibling);
                    else
                        sibling.getParent().setRight(sibling);

                    doubleBlacked.getParent().setParent(sibling);

                    //re-coloring
                    sibling.changeColor(doubleBlacked.getParent().getColor());
                    sibling.getLeft().changeColor(false);
                    sibling.getRight().changeColor(false);
                }
            }

            else													//two children are black (case 2)
            {
                sibling.changeColor(true);
                if(doubleBlacked.getParent().getColor())
                    doubleBlacked.getParent().changeColor(false);
                else if(doubleBlacked.getParent()!=root)
                {
                    doubleBlacked=doubleBlacked.getParent();
                    RBNode parent=doubleBlacked.getParent();
                    if(doubleBlacked==parent.getLeft())
                        sibling=parent.getRight();
                    else sibling=parent.getRight();
                    doubleBlack(doubleBlacked,sibling);
                }
            }
            return true;
        }

        else if(sibling.getColor())									//sibling is red
        {
            if(sibling==sibling.getParent().getLeft()) {
                temp=sibling.getRight();
                sibling.setRight(doubleBlacked.getParent());
                doubleBlacked.getParent().setParent(sibling);
                doubleBlacked.getParent().setLeft(temp);
                temp.setParent(doubleBlacked.getParent());
            }
            else {
                temp=sibling.getLeft();
                sibling.setLeft(doubleBlacked.getParent());
                doubleBlacked.getParent().setParent(sibling);
                doubleBlacked.getParent().setRight(temp);
                temp.setParent(doubleBlacked.getParent());
            }

            sibling.setParent(sibling.getParent().getParent());
            if(doubleBlacked.getParent()==root || sibling.getParent()==null)
                root=sibling;
            else
                sibling.setParent(sibling.getParent().getParent());
            doubleBlacked.getParent().setParent(sibling);

            //re coloring
            sibling.changeColor(false);
            doubleBlacked.getParent().changeColor(true);

            //repeat on the replacement node
            if(doubleBlacked==doubleBlacked.getParent().getRight())
                sibling=doubleBlacked.getParent().getLeft();
            else
                sibling=doubleBlacked.getParent().getRight();

            doubleBlack(doubleBlacked,sibling);
        }

        return false;
    }

    boolean delete(int key)
    {
        RBNode toDelete=search(key,root), replacement;
        RBNode sibling;

        if(toDelete==null)
            return false;

            //Case 1
        else if(toDelete.getLeft()==null && toDelete.getRight()==null && toDelete.getColor())
        {
            if(toDelete==toDelete.getParent().getRight())
            {
                toDelete.getParent().setRight(null);
            }
            else
                toDelete.getParent().setLeft(null);

            toDelete=null; // if there is no reference to the object,it will be deleted by the garbage collector
            return true;
        }

        //case 3
        else if(toDelete.getLeft()==null && toDelete.getRight()==null)
        {
            if(toDelete==root)
            {
                root=null;
                return true;
            }
            else if(toDelete==toDelete.getParent().getRight())
                sibling=toDelete.getParent().getLeft();
            else
                sibling=toDelete.getParent().getRight();

            doubleBlack(toDelete,sibling);

            if(toDelete==toDelete.getParent().getRight())
                toDelete.getParent().setRight(null);
            else
                toDelete.getParent().setLeft(null);


            toDelete=null;
        }
        //case 1.2
        else if(toDelete.getLeft()==null || toDelete.getRight()==null)
        {
            if(toDelete.getParent()==null)
            {
                if(toDelete.getRight()!=null)
                    root=toDelete.getRight();

                else
                    root=toDelete.getLeft();

                root.changeColor(false);
                toDelete=null;
                return true;
            }

            else if(toDelete==toDelete.getParent().getRight()) {
                if(toDelete.getRight()!=null)
                {
                    toDelete.getParent().setRight(toDelete.getRight());
                    replacement=toDelete.getRight();
                }
                else
                {
                    toDelete.getParent().setRight(toDelete.getLeft());
                    replacement=toDelete.getLeft();
                }
                replacement.setParent(toDelete.getParent());
            }
            else {
                if(toDelete.getRight()!=null)
                {
                    toDelete.getParent().setLeft(toDelete.getRight());
                    replacement=toDelete.getRight();
                }
                else
                {
                    toDelete.getParent().setLeft(toDelete.getLeft());
                    replacement=toDelete.getLeft();
                }
                replacement.setParent(toDelete.getParent());
            }

            replacement.changeColor(false);

            toDelete=null;
            return true;
        }
        //case 3.1
        else if(toDelete.getLeft()!=null && toDelete.getRight()!=null){
            RBNode pre=predecessor(toDelete),tempLeft,tempRight;
            if(toDelete.getParent()==null)
            {
                tempLeft=predecessor(toDelete);
                tempLeft.getParent().setRight(null);

                root=tempLeft;
                root.setParent(null);
                root.setRight(toDelete.getRight());
                toDelete.getRight().setParent(root);
                root.setLeft(toDelete.getLeft());
                toDelete.getLeft().setParent(root);
                toDelete.setLeft(null);
                toDelete.setRight(null);
                pre=predecessor(root);
                pre.setRight(toDelete);
                toDelete.setParent(pre);
                toDelete.changeColor(root.getColor());
                root.changeColor(false);
                sibling=pre.getLeft();

                if(toDelete.getParent().getColor())  //Yosry Touch
                    doubleBlack(toDelete,sibling);

                if(toDelete==toDelete.getParent().getLeft())
                    toDelete.getParent().setLeft(null);
                else
                    toDelete.getParent().setRight(null);

                toDelete.setParent(null);
                toDelete=null;
                return true;
            }

            else //if(toDelete==toDelete.getParent().getRight())
            {
                RBNode tempParent=null;
                boolean color=pre.getColor();
                replacement=pre;
                replacement.changeColor(toDelete.getColor());
                toDelete.changeColor(color);
                tempRight=toDelete.getRight();
                tempLeft=toDelete.getLeft();

                replacement.setLeft(tempLeft);
                replacement.setRight(tempRight);
                tempLeft.setParent(replacement);
                tempRight.setParent(replacement);

                tempParent=replacement.getParent();

                if(replacement==replacement.getParent().getLeft())
                {
                    sibling=replacement.getParent().getRight();
                    replacement.getParent().setLeft(toDelete);

                }
                else
                {
                    sibling=toDelete.getParent().getLeft();
                    replacement.getParent().setRight(toDelete);

                }

                replacement.setParent(toDelete.getParent());

                if(toDelete==toDelete.getParent().getLeft())
                {
                    toDelete.getParent().setLeft(replacement);
                }
                else
                    toDelete.getParent().setRight(replacement);


                if(tempParent!=toDelete)
                    toDelete.setParent(tempParent);
                else
                    toDelete.setParent(replacement);
                toDelete.setLeft(null);
                toDelete.setRight(null);

                if(toDelete.getParent().getColor())  //Yosry Touch
                    doubleBlack(toDelete,sibling);

            }

            if(toDelete==toDelete.getParent().getLeft())
                toDelete.getParent().setLeft(null);
            else
                toDelete.getParent().setRight(null);

            toDelete.setParent(null);
            toDelete=null;

        }

        return false;
    }

    public RB_Tree() {

        root = null;
    }


    /**
     * This function takes node as a parameter and checks
     * if the node value is greater than the current node value, it goes right
     * else, it goes left, and do this cases until it finds a null node
     * @param //node
     */
    public void insert(int key) {

        RBNode node=new RBNode(key, true);

        if(root==null){

            root=node;
        }

        else{

            RBNode newNode = root;

            while (true) {

                //new value > current value + right node of current node != null
                if (node.getValue() > newNode.getValue() && newNode.getRight() != null) {
                    newNode = newNode.getRight();
                }

                //new value > current value + right node of current node = null
                else if (node.getValue() > newNode.getValue() && newNode.getRight() == null) {
                    newNode.setRight(node);
                    node.setParent(newNode);
                    break;
                }

                //new value < current value + left node of current node != null
                else if (node.getValue() < newNode.getValue() && newNode.getLeft() != null) {
                    newNode = newNode.getLeft();
                }

                //new value < current value + left node of current node = null
                else if (node.getValue() < newNode.getValue() && newNode.getLeft() == null) {
                    newNode.setLeft(node);
                    node.setParent(newNode);
                    break;
                }
            }
        }

        fix(node);
    }





    public void fix(RBNode node) {

        RBNode parent = node.getParent();

        if (root == node) {

            node.changeColor(false);
        }


        else if (parent.getColor()){

            RBNode grand = parent.getParent();
            RBNode uncle;

            if (grand.getLeft() == parent) {
                uncle = grand.getRight();
            } else {
                uncle = grand.getLeft();
            }

            // End of Section


            //CASE 1
            if(uncle!=null){

                if (uncle.getColor()) {

                    parent.changeColor();
                    uncle.changeColor();
                    grand.changeColor();
                }

                fix(grand);
            }


            else if (uncle==null || !uncle.getColor()){

                //CASE 2 -> Triangle
                if(grand.getLeft()==uncle && parent.getLeft()==node){

                    RightRotate(node);
                    node.changeColor(false);
                    node.getLeft().changeColor(true);
                    node.getRight().changeColor(true);

                    fix(node);
                }

                else if (grand.getRight()==uncle && parent.getRight()==node){

                    LeftRotate(node);
                    node.changeColor(false);
                    node.getLeft().changeColor(true);
                    node.getRight().changeColor(true);

                    fix(node);
                }




                //case 3
                if(grand.getLeft()==uncle && parent.getRight()==node){

                    parent.changeColor();
                    grand.changeColor();
                    LeftRotate(node);
                }

                else if(grand.getRight()==uncle && parent.getLeft()==node){

                    parent.changeColor();
                    grand.changeColor();
                    RightRotate(node);

                }
            }
        }
    }


    /** check if grand is in right or left, check it grand is root */

    public void LeftRotate(RBNode node) {

        RBNode parent = node.getParent();
        RBNode grand = parent.getParent();

        //Triangle
        if(parent.getRight()==node && grand.getLeft()==parent){

            RBNode pGrand= grand.getParent();

            node.setParent(grand.getParent());
            node.setLeft(parent);
            node.setRight(grand);

            if (grand == root) {
                root = node;
            } else {

                if (pGrand.getLeft()==grand){
                    pGrand.setLeft(node);
                } else {
                    pGrand.setRight(node);
                }
            }

            parent.setParent(node);
            parent.setRight(null);

            grand.setParent(node);
            grand.setLeft(null);

        }

        //Line
        else if(parent.getRight()==node && grand.getRight()==parent){

            if(grand!=root){

                RBNode pGrand= grand.getParent();
                RBNode lcParent= parent.getLeft();

                grand.setRight(lcParent);
                grand.setParent(parent);

                if (lcParent != null) {
                    lcParent.setParent(grand);
                }


                parent.setLeft(grand);
                parent.setParent(pGrand);

                if(pGrand.getLeft()==grand){
                    pGrand.setLeft(parent);
                } else {
                    pGrand.setRight(parent);
                }

            }


            else {

                RBNode lcParent= parent.getLeft();

                grand.setRight(lcParent);
                grand.setParent(parent);

                if (lcParent != null) {
                    lcParent.setParent(grand);
                }

                root=parent;
                parent.setParent(null);
                parent.setLeft(grand);
            }
        }
    }





    public void RightRotate(RBNode node) {

        RBNode parent = node.getParent();
        RBNode grand = parent.getParent();

        //Triangle
        if(parent.getLeft()==node && grand.getRight()==parent){

            RBNode pGrand= grand.getParent();

            node.setParent(grand.getParent());
            node.setRight(parent);
            node.setLeft(grand);

            if (grand == root) {
                root = node;
            } else {

                if (pGrand.getLeft()==grand){
                    pGrand.setLeft(node);
                } else {
                    pGrand.setRight(node);
                }
            }

            parent.setParent(node);
            parent.setLeft(null);

            grand.setParent(node);
            grand.setRight(null);
        }

        //Line
        else if(parent.getLeft()==node && grand.getLeft()==parent){

            if(grand!=root){

                RBNode pGrand= grand.getParent();
                RBNode rcParent= parent.getRight();

                grand.setLeft(rcParent);
                grand.setParent(parent);

                if (rcParent != null) {
                    rcParent.setParent(grand);
                }

                parent.setRight(grand);
                parent.setParent(pGrand);

                if(pGrand.getRight()==grand){
                    pGrand.setRight(parent);
                } else {
                    pGrand.setLeft(parent);
                }

            }


            else {

                RBNode rcParent= parent.getRight();

                grand.setLeft(rcParent);
                grand.setParent(parent);

                if (rcParent != null) {
                    rcParent.setParent(grand);
                }


                root=parent;
                parent.setParent(null);
                parent.setRight(grand);
            }
        }
    }

    void inorderRec(RBNode root)
    {
        if (root != null) {
            inorderRec(root.getLeft());
            if(root.getParent()!=null)
                System.out.println(root.getValue() + " " + root.getColor()+"  "+root.getParent().getValue());
            else
                System.out.println(root.getValue() + " " + root.getColor()+"  root");
            inorderRec(root.getRight());
        }
    }

    void clear(RBNode root) {

        if (root != null) {
            clear(root.getLeft());
            clear(root.getRight());
            //delete(root.getValue());

            root.setLeft(null);
            root.setRight(null);
            root.setParent(null);
            RBNode r=getroot();
            if(root==r)
                delete(root.getValue());
            root=null;
        }

    }



    public RBNode getroot(){
        return root;
    }


}