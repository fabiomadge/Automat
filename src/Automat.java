class Automat{

	String alph;
	State state;
	State[] states;

	public Automat(String automat){
		alph = "";
		state = null;
		states = evalStates(automat);
	}

	private void changeState(char c){
		if(state == null) state = states[0];
		state = state.changeState(c);
	}

	public boolean checkWord(String s){
		s = s.toLowerCase();
		state = states[0];
		for(int i = 0; i < s.length(); i ++){
			if(alph.indexOf(s.charAt(i)) >= 0){
				changeState(s.charAt(i));
			}
		}
		return state.end();
	}

	public State[] evalStates(String s){
		//Slices the input into parts
		String[] parts = s.split("\\;");

		//extracts the alphabet
		alph = parts[0].trim();

		//extracts the possible end states
		String[] ends = parts[parts.length-1].trim().split("\\,");
		for(int i = 0; i < ends.length; i++){
			ends[i] = ends[i].trim();
		}

		//reduces the parts to only contain state instuctions
		String[] sts = new String[parts.length-2];
		for(int i = 0; i < (parts.length-2); i++){
			sts[i] = parts[i+1];
		}

		//extracts the names of all states
		String[] stateNames = new String[sts.length];
		for(int i = 0; i < sts.length; i++){
			stateNames[i] = sts[i].split("\\:")[0].trim();
		}

		//extracts the change infos associated to each state
		String[] stateCommands = new String[sts.length];
		for(int i = 0; i < sts.length; i++){
			stateCommands[i] = sts[i].split("\\:")[1].trim();
		}

		//creates the new states
		State[] ss = new State[sts.length];
		for(int i = 0; i < sts.length; i++){
			ss[i] = new State();
		}

		//checks for end states
		for(int i = 0; i < ss.length; i++){
			ss[i].setEnd(find(ends, stateNames[i]) >= 0);
		}

		//eval changes
		for(int i = 0; i < ss.length; i++){
			String[] commands = stateCommands[i].split("\\s+");
			for(int it = 0; it < commands.length; it++){
				commands[it] = commands[it].trim();
			}

			Changer[] changers = null;
			for(int it = 0; it < commands.length; it++){
				if(commands[it].startsWith("default")) changers = new Changer[commands.length-1];
			}
			if(changers == null) changers = new Changer[commands.length];

			int defaults = 0;
			State defau = null;
			for(int it = 0; it < commands.length; it++){
				if(!commands[it].startsWith("default")){
					String dirtyKey = commands[it].split("\\->")[0].trim();
					String dirtyResult = commands[it].split("\\->")[1].trim();

					changers[it - defaults] = new Changer(dirtyKey.charAt(1), ss[find(stateNames, dirtyResult)]);
				}
				else{
					String dirtyResult = commands[it].split("\\->")[1].trim();

					defau = ss[find(stateNames, dirtyResult)];

					defaults++;
				}
			}

			if(changers.length < commands.length) ss[i].setDefault(defau);

			ss[i].setChangers(changers);
		}

		state = ss[0];

		return ss;
	}

	public int find(String[] ss, String s){
		for(int i = 0; i < ss.length; i++){
			if(ss[i].equals(s)) return i;
		}
		return -1;
	}
}