public class Main
{
	public static void main(String[] s) throws Exception{
		Automat auto = new Automat("0123456789NSOWd; 0: '1'..'9'->N1 '0'->N2 default->Kaputt; N1: 'd'->GR1 '0'..'9'->N2 default->Kaputt; N2: 'd'->GR1 default->Kaputt; GR1: 'N''S'->R1 default->Kaputt; R1: '0'->W3 '1'..'9'->W1 default->Kaputt; W1: 'd'->GR2 '0'..'9'->W2 default->Kaputt; W2: 'd'->GR2 '0'..'9'->W3 default->Kaputt; W3: 'd'->GR2; GR2: 'O''W'->R2 default->Kaputt; R2: default->Kaputt; Kaputt: default->Kaputt; R2");
		System.out.println(auto.checkWord(s[0]));
	}
}