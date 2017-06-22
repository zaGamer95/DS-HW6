/**
 * Created by zaGamer on 2017-06-06.
 */
class rb_tree {
    private final boolean RED = true;
    private final boolean BLACK = false;
    private final Node nil = new Node(-1);

    public Node root = nil;
    int total, nb, ins=0, del=0, miss=0;

    class Node {
        int val;
        Node l = nil, r = nil, p = nil;
        boolean c = BLACK;
        Node(int n) {val = n;}
    }

    public Node min(Node n){
        while(n.l != nil) n=n.l;
        return n;
    }

    public void insert(int n) {
        Node z = new Node(n);
        Node y = nil;
        Node x = root;

        while (x != nil) {
            y = x;
            if (z.val < x.val)
                x = x.l;
            else x = x.r;
        } z.p = y;

        if (y == nil) {
            root = z;
            z.c = BLACK;
            z.p = nil;
        } else if (z.val < y.val) y.l = z;
        else y.r = z;
        z.l = nil;
        z.r = nil;
        z.c = RED;
        while (z.p.c == RED) {
            Node y2 = nil;
            if (z.p == z.p.p.l) {
                y2 = z.p.p.r;

                if (y2.c == RED) {
                    z.p.c = BLACK;
                    y2.c = BLACK;
                    z.p.p.c = RED;
                    z = z.p.p;
                    continue;
                } else if (z == z.p.r) {
                    z = z.p;
                    l_rot(z);
                }
                z.p.c = BLACK;
                z.p.p.c = RED;
                r_rot(z.p.p);
            } else {
                y2 = z.p.p.l;
                if (y2.c == RED) {
                    z.p.c = BLACK;
                    y2.c = BLACK;
                    z.p.p.c = RED;
                    z = z.p.p;
                    continue;
                } else if (z == z.p.l) {
                    z = z.p;
                    r_rot(z);
                }
                z.p.c = BLACK;
                z.p.p.c = RED;
                l_rot(z.p.p);
            }
        } root.c = BLACK;
        ins++;
    }


    public void l_rot (Node n){
        if (n.p == nil) {
            Node r = root.r;
            root.r = r.l;
            r.l.p = root;
            root.p = r;
            r.l = root;
            r.p = nil;
            root = r;
        } else {
            if (n == n.p.l) n.p.l = n.r;
            else n.p.r = n.r;
            n.r.p = n.p;
            n.p = n.r;
            if (n.r.l != nil)  n.r.l.p = n;
            n.r = n.r.l;
            n.p.l = n;
        }
    }

    public void r_rot (Node n){
        if (n.p == nil) {
            Node left = root.l;
            root.l = root.l.r;
            left.r.p = root;
            root.p = left;
            left.r = root;
            left.p = nil;
            root = left;
        } else {
            if (n == n.p.l) n.p.l = n.l;
            else n.p.r = n.l;
            n.l.p = n.p;
            n.p = n.l;
            if (n.l.r != nil) n.l.r.p = n;
            n.l = n.l.r;
            n.p.r = n;
        }
    }

    void transplant (Node u, Node v){
        if(u.p == nil) root = v;
        else if(u == u.p.l) u.p.l = v;
        else u.p.r = v;
        v.p = u.p;
    }

    public void delete (int n) {
        Node z, x, y;
        if((z = search(root, n))==null) {miss++;return;}
        y = z;
        boolean y_original_color = y.c;

        if(z.l == nil){
            x = z.r;
            transplant(z, z.r);
        }else if(z.r == nil){
            x = z.l;
            transplant(z, z.l);
        }else{
            Node t = z.r;
            while (t.l != nil) t = t.l;
            y = t;
            y_original_color = y.c;
            x = y.r;
            if(y.p == z) x.p = y;
            else{
                transplant(y, y.r);
                y.r = z.r;
                y.r.p = y;
            } transplant(z, y);
            y.l = z.l;
            y.l.p = y;
            y.c = z.c;
        }
        if(y_original_color==BLACK)
            while(x != root && x.c == BLACK){
                if(x == x.p.l){
                    Node w = x.p.r;
                    if(w.c == RED){
                        w.c = BLACK;
                        x.p.c = RED;
                        l_rot(x.p);
                        w = x.p.r;
                    }
                    if(w.l.c == BLACK && w.r.c == BLACK){
                        w.c = RED;
                        x = x.p;
                        continue;
                    }
                    else if(w.r.c == BLACK){
                        w.l.c = BLACK;
                        w.c = RED;
                        r_rot(w);
                        w = x.p.r;
                    }
                    if(w.r.c == RED){
                        w.c = x.p.c;
                        x.p.c = BLACK;
                        w.r.c = BLACK;
                        l_rot(x.p);
                        x = root;
                    }
                }else{
                    Node w = x.p.l;
                    if(w.c == RED){
                        w.c = BLACK;
                        x.p.c = RED;
                        r_rot(x.p);
                        w = x.p.l;
                    }
                    if(w.r.c == BLACK && w.l.c == BLACK){
                        w.c = RED;
                        x = x.p;
                        continue;
                    }
                    else if(w.l.c == BLACK){
                        w.r.c = BLACK;
                        w.c = RED;
                        l_rot(w);
                        w = x.p.l;
                    }
                    if(w.l.c == RED){
                        w.c = x.p.c;
                        x.p.c = BLACK;
                        w.l.c = BLACK;
                        r_rot(x.p);
                        x = root;
                    }
                }
            } x.c = BLACK; del++; return;
    }

    public Node search (Node t, int val) {
        if (t == nil) return null;
        if (val == t.val) return t;
        else if (val < t.val&&t.l != nil) return search(t.l, val);
        else if (val > t.val&&t.r != nil) return search(t.r, val);
        return null;
    }
    public int[] smallbig(Node tree, int val) {
        int s[] = {0,0};
        if (tree == nil) return s;
        smallbig(tree.l,val);
        if(val>tree.val) s[0] = tree.val;
        else if(val<tree.val){s[1] = tree.val;return s;}
        smallbig(tree.r,val);
        return s;
    }

    int b_height () {
        int level = 0;
        Node n = root;
        while (n != nil) {
            if (n.c == BLACK) level++;
            n = n.r;
        } return level;
    }

    void numbering (Node tree) {
        if (tree == root) {total = 0; nb = 0;}
        if (tree == nil) return;
        numbering(tree.l);
        total ++;
        if (tree.c == BLACK) nb++;
        numbering(tree.r);
    }
}
