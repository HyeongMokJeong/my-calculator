import java.util.ArrayList;
import java.util.Stack;

public class cal_part {
	
	public double cal(String result){
		double re = 0;
		double temp = 0;
		ArrayList <String> ary = new ArrayList<>();
		Stack<Double> restack = new Stack<>();
		
		int last = 0;
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == '+' | result.charAt(i) == '-' | result.charAt(i) == '*' | result.charAt(i) == '/') {
				ary.add(result.substring(last, i));
				ary.add(Character.toString(result.charAt(i)));
				last = i + 1;
			}
		}
		ary.add(result.substring(last, result.length()));
		
		while (ary.size() != 1) {
			int idx1 = ary.indexOf("*");
			int idx2 = ary.indexOf("/");
			int idx3;
			if (idx1 == -1 & idx2 == -1) 
				break;
			else if (idx1 == -1 | idx2 == -1)
				idx3 = Math.max(idx1, idx2);
			else
				idx3 = Math.min(idx1, idx2);
			if (idx3 == idx1)
				temp = Double.parseDouble(ary.get(idx3 - 1)) * Double.parseDouble(ary.get(idx3 + 1));
			else 
				temp = Double.parseDouble(ary.get(idx3 - 1)) / Double.parseDouble(ary.get(idx3 + 1));
			ary.subList(idx3 - 1, idx3 + 2).clear();
			restack.add(temp);
		}
		
		while (ary.size() != 1) {
			int idx1 = ary.indexOf("+");
			int idx2 = ary.indexOf("-");
			int idx3;
			if (idx1 == -1 & idx2 == -1) 
				break;
			else if (idx1 == -1 | idx2 == -1)
				idx3 = Math.max(idx1, idx2);
			else
				idx3 = Math.min(idx1, idx2);
			if (idx3 == idx1) {
				double des = restack.pop();
				if (restack.empty())
					re +=  des + Double.parseDouble(ary.get(idx3 + 1));
				else
					re += des + restack.pop();
			}
			else 
				temp = Double.parseDouble(ary.get(idx3 - 1)) - Double.parseDouble(ary.get(idx3 + 1));
			ary.subList(idx3 - 1, idx3 + 2).clear(); 
			ary.add(Double.toString(temp));
		}
		return Double.parseDouble(ary.get(0));
	}

}
