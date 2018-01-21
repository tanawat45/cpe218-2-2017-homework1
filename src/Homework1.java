import java.util.Stack;
import javax.imageio.metadata.IIOMetadataNode;
import org.w3c.dom.Node;

public class Homework1 {

	static Stack stack = new Stack();
	static Node root;
	/*public static void main(String[] args) {
		// Begin of arguments input sample
		String input = "";
		if (args.length > 0) {
			input=args[1];
			if (input.equalsIgnoreCase("251-*32*+")) {
				System.out.println("(2*(5-1))+(3*2)=14");
			}
		}else {
			System.out.printf("Your input not equal");
			System.exit(404);
		}
		char[] input1 = input.toCharArray();
		for(int i=0;i<input.length();i++){
			stack.push((char)input1[i]);
		}
		root = new IIOMetadataNode(stack.pop().toString());
		Inorder(root);
		Infix(root);
		System.out.println("=" + root.getNodeValue());
	}
	// End of arguments input sampl*/

	public static void main(String[] args) {
		// Begin of arguments input sample
		String input = "251-*32*+";
		if (args.length > 0) {
			input = args[0];
		}
		char[] data = input.toCharArray();
		for (int i = 0; i < data.length; i++) {
			stack.push((char)data[i]);
		}
		root = new IIOMetadataNode(stack.pop().toString());
		Inorder(root);
		Infix(root);
		System.out.println(" = " + root.getNodeValue());
	}

	static public void Infix(Node n) {
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

	static public void Inorder(Node a) {
		if(!Operate(a.getNodeName().charAt(0))) {
			a.setNodeValue(a.getNodeName());
			return;
		}
		Node right = new IIOMetadataNode(stack.pop().toString());
		Inorder(right);
		Node left = new IIOMetadataNode(stack.pop().toString());
		Inorder(left);
		a.appendChild(right);
		a.appendChild(left);
		Calculate(a);
	}

	static public void Calculate(Node n) {
		int right = Integer.parseInt(n.getChildNodes().item(0).getNodeValue());
		int left = Integer.parseInt(n.getChildNodes().item(1).getNodeValue());
		int result = 0;
		String s = n.getNodeName();
		if (s.equals(" + ")) {
			result = left + right;
		} else if (s.equals(" - ")) {
			result = left - right;
		} else if (s.equals(" * ")) {
			result = left * right;
		} else if (s.equals(" / ")) {
			result = left / right;
		}
		n.setNodeValue(Integer.toString(result));
	}

	static public boolean Operate(char n) {
		if(n == '+' || n == '-' || n == '*' || n == '/') {
			return true;
		}else {
			return false;
		}
	}
}
