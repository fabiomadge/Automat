public class Main
{
	public static void main(String[] s) throws Exception{
		Automat auto = new Automat("ab; 0: 'a'->1 'b'->0; 1: 'a'->1 'b'->0; 1");
		System.out.println(auto.checkWord(s[0]));
	}
}