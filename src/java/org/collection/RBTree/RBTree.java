package org.collection.RBTree;

/**
 * 红黑树
 * @author : zhuxueke
 * @since : 2017-12-14 8:54
 **/
public class RBTree<T extends Comparable<T>> {

    //根节点
    private RBNode<T> mRoot;

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     * 红黑树的节点类
     * @param <T>
     */
    public class RBNode<T extends Comparable<T>>{
        boolean color;
        T key;
        RBNode<T> leftChild;
        RBNode<T> rightChild;
        RBNode<T> parent;

        public RBNode(T key,boolean color, RBNode<T> parent, RBNode<T> leftChild, RBNode<T> rightChild) {
            this.color = color;
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }
    }

    /*
     * 对红黑树的节点(x)进行左旋转 (x <==> y)
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     *  y的左孩子为x的右孩子
     *  x的左孩子不变
     *  y的右孩子不变
     *  x的父节点为null，不为空设置y为父节点
     *  x的父节点不为null，判断x为左孩子还是右孩子，并设置
     *  设置x为y的左孩子
     *  设置x的父节点为y
     */
    private void leftChildRotate(RBNode<T> x){
        // 设置x的右孩子为y
        RBNode<T> y = x.rightChild;

        // 将 “y的左孩子” 设为 “x的右孩子”；
        x.rightChild = y.leftChild;

        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        if(y.leftChild != null)
            y.leftChild.parent = x;

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;
        if(x.parent == null){
            // 如果 “x的父亲” 是空节点，则将y设为根节点
            this.mRoot = y;
        }else {
            if(x.parent.leftChild == x)
                // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                x.parent.leftChild = y;
            else
                // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                x.parent.rightChild = y;
        }
        // 将 “x” 设为 “y的左孩子”
        y.leftChild = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }

   /*
    * 对红黑树的节点(y)进行右旋转 (x <==> y)
    * 右旋示意图(对节点y进行左旋)：
    *            py                               py
    *           /                                /
    *          y                                x
    *         /  \      --(右旋)-.            /  \
    *        x   ry                          lx   y
    *       / \                                   / \
    *      lx  rx                                rx  ry
    *
    * x的右孩子为y的左孩子
    * x的左孩子不变
    * y的右孩子不变
    * y的父节点为null，设置x为根节点
    * y的父节点不为null，判断y为左孩子还是右孩子，设置x
    * 设置y为x右孩子
    * 设置y的父节点为x
    */
    private void rightRotate(RBNode<T> y){
        // 设置x是当前节点的左孩子
        RBNode<T> x = y.leftChild;

        // 将 “x的右孩子” 设为 “y的左孩子”
        y.leftChild = x.rightChild;

        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        if (null != x.rightChild){
            x.rightChild.parent = y;
        }
        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if(null == y.parent){
            // 如果 “y的父亲” 是空节点，则将x设为根节点
            this.mRoot = x;
        }else {
            if(y == y.parent.rightChild){
                // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
                y.parent.rightChild = x;
            }else {
                // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
                y.parent.leftChild = x;
            }
        }
        // 将 “y” 设为 “x的右孩子”
        x.rightChild = y;
        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }

    /**
     * 私有方法
     * 将节点插入到红黑树中
     * @param node
     */
    private void insert(RBNode<T> node){
        int cmp;
        RBNode<T> y = null;
        RBNode<T> x = this.mRoot;

        while (x != null){
            y = x;
            cmp = node.key.compareTo(x.key);
            if(cmp < 0){
                x = x.leftChild;
            }else {
                x = x.rightChild;
            }
        }

        node.parent = y;
        if(null != y){
            cmp  = node.key.compareTo(y.key);
            if(cmp < 0){
                y.leftChild = node;
            }else{
                y.rightChild = node;
            }
        }else{
            this.mRoot = node;
        }
        // 设置节点的颜色为红色
        node.color = RED;

        insertFixUp(node);
    }

    /**
     * 公开方法
     * @param key
     */
    public void insert(T key){
        RBNode<T> node = new RBNode<T>(key,BLACK,null,null,null);
        if(null != node){
            insert(node);
        }
    }
    private void insertFixUp(RBNode<T> node){
        RBNode<T> parent, gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        while(((parent = parentOf(node))!=null) && isRed(parent)){
            gparent = parentOf(parent);

            //如果 "父节点"是"祖父节点左孩子"
            if(parent == gparent.leftChild){
                RBNode<T> uncle = gparent.rightChild;
                // Case 1条件：叔叔节点是红色
                if(null != uncle && isRed(uncle)){
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                // Case 2条件：叔叔节点是黑色，并且当前节点是右孩子
                if(parent.rightChild == node){
                    RBNode<T> tmp;
                    leftChildRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            }else{  //如果 "父节点"是"祖父节点左孩子"
                RBNode<T> uncle = gparent.leftChild;
                // Case 1条件：叔叔节点是红色
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if(parent.leftChild == node){
                    RBNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftChildRotate(gparent);
            }
        }
        setBlack(this.mRoot);
    }

    private boolean isRed(RBNode<T> node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }

    private RBNode<T> parentOf(RBNode<T> node) {
        return node!=null ? node.parent : null;
    }

    private boolean colorOf(RBNode<T> node) {
        return node!=null ? node.color : BLACK;
    }
    private boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }
    private void setBlack(RBNode<T> node) {
        if (node!=null)
            node.color = BLACK;
    }
    private void setRed(RBNode<T> node) {
        if (node!=null)
            node.color = RED;
    }
    private void setParent(RBNode<T> node, RBNode<T> parent) {
        if (node!=null)
            node.parent = parent;
    }
    private void setColor(RBNode<T> node, boolean color) {
        if (node!=null)
            node.color = color;
    }
}
