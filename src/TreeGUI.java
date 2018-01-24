//import javax.imageio.metadata.IIOMetadataNode;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.soap.Node;
import java.awt.*;
import java.util.Scanner;
import java.util.Stack;

public class TreeGUI extends JPanel implements TreeSelectionListener {
    private JTree tree;
    private JEditorPane html;
    private static boolean b1 = false;
    public static Stack<Character> stack = new Stack<Character>();
    static DefaultMutableTreeNode root;

    public void valueChanged(TreeSelectionEvent e){
        DefaultMutableTreeNode n = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        String data = Inorder(n);
        if (!n.isLeaf()){
            data = data.substring(1,data.length()-1) + " = " + Calculate(n);
        }
        html.setText(data);
    }

    private static String Inorder(DefaultMutableTreeNode n){
//        if(!Operate(a.getNodeName().charAt(0))) {
//            a.setNodeValue(a.getNodeName());
//            return null;
//        }
//        Node right = (Node) new IIOMetadataNode(stack.pop().toString());
//        Inorder(right);
//        Node left = (Node) new IIOMetadataNode(stack.pop().toString());
//        Inorder(left);
//        a.appendChild(right);
//        a.appendChild(left);
//        Calculate((DefaultMutableTreeNode) a);
//        return null;
        String key = (String) n.getUserObject();
        String left = Inorder(n.getNextNode());
        String right = Inorder(n.getNextNode().getNextSibling());
        if (!BACKUP.Homework1.Operate(key.charAt(0))){
            return key;
        }
        return "(" + left + key + right + ")";
    }

    private static int Calculate(DefaultMutableTreeNode n) {
        String key =(String) n.getUserObject();
        int left = Calculate(n.getNextNode());
        int right = Calculate(n.getNextNode().getNextSibling());
        int result=0;
        if(n.isLeaf()) {
            return Integer.valueOf(key);
        }
        if (key.equals("+")) {
            result = left + right;
        } else if (key.equals(" - ")) {
            result = left - right;
        } else if (key.equals(" * ")) {
            result = left * right;
        } else if (key.equals(" / ")) {
            result = left / right;
        }
        return result;
    }

    static public void Infix(org.w3c.dom.Node n) {
        if(n != null) {
            if(Operate(n.getNodeName().charAt(0)) && n != root) {
                System.out.print("(");
            }
            Infix(n.getChildNodes().item(1));
            System.out.print(n.getNodeName());
            Infix(n.getChildNodes().item(0));
            if(Operate(n.getNodeName().charAt(0)) && n != root) {
                System.out.print(")");
            }
        }
    }

    public TreeGUI(){
       super(new GridLayout(1,0));
       DefaultMutableTreeNode t1 = new DefaultMutableTreeNode();
       tree = new JTree(t1);
       tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

       ImageIcon icon = new ImageIcon(TreeGUI.class.getResource("middle.gif"));
       DefaultTreeCellRenderer rend = new DefaultTreeCellRenderer();
       rend.setClosedIcon(icon);
       rend.setOpenIcon(icon);
       tree.setCellRenderer(rend);
       tree.addTreeSelectionListener(this);
       if (b1){
           System.out.println("line = none");
           tree.putClientProperty("lineStyle","none");
       }
       JScrollPane view = new JScrollPane(tree);
       html = new JEditorPane();
       html.setEditable(false);
       JScrollPane viewht = new JScrollPane(html);
       JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       splitPane.setTopComponent(view);
       splitPane.setBottomComponent(viewht);
       Dimension minsize = new Dimension(100,50);
       viewht.setMinimumSize(minsize);
       view.setMinimumSize(minsize);
       splitPane.setDividerLocation(100);
       splitPane.setPreferredSize(new Dimension(500,300));
       add(splitPane);
    }

    private static void CreateGui() {
        JFrame frame = new JFrame("Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreeGUI());
        frame.pack();
        frame.setVisible(b1);
        if (b1){
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch (Exception e){
                System.out.printf("Couldn't use system");
            }
        }
    }

    private DefaultMutableTreeNode CreateNode(Node n){
        char key = n.getNodeName().charAt(0);
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(n.getNodeName());
        if (Homework1.Operate(key)){
            DefaultMutableTreeNode dat = CreateNode((Node) n.getChildNodes().item(1));
            DefaultMutableTreeNode dat1 = CreateNode((Node) n.getChildNodes().item(0));
            node1.add(dat);
            node1.add(dat1);
        }
        return node1;
    }

    private static boolean Operate(char key) {
        if(key == '+' || key == '-' || key == '*' || key == '/') {
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner inout = new Scanner(System.in);
        String x = inout.nextLine();
        System.out.print("Input : ");
        int i = 0;
        do {
            stack.push(x.charAt(i));
            i++;
        }while (i < x.length());
            root = (DefaultMutableTreeNode) new DefaultMutableTreeNode(stack.pop().toString());
            Inorder(root);
            Infix((org.w3c.dom.Node) root);
            System.out.printf("=" + Calculate(root));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    TreeGUI.CreateGui();
                }
            });
    }
}
