package domain;

public class OutFormat{
	public void print(String labels, String context) {
		System.out.printf("%s %s\n",labels,context);
	}
	public void print(String labels, int context) {
		System.out.printf("%s %d\n",labels,context);
	}
	public void print(String labels, double context) {
		System.out.printf("%s %f\n",labels,context);
	}
}