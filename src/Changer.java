class Changer{

	char key;
	State result;

	public Changer(char c, State s){
		key = c;
		result = s;
	}

	public char getKey(){
		return key;
	}

	public State getResult(){
		return result;
	}
}