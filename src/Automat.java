class Automat{

	String alph;
	State current;
	State[] states;

	public Automat(String automat){
		alph = "";
		current = null;
		states = evalStates(automat);
	}

	private void changeState(char c){
		if(current == null) current = states[0];
		current = current.changeState(c);
	}

	public boolean checkWord(String s){
		current = states[0];
		for(int i = 0; i < s.length(); i ++){
			if(alph.indexOf(s.charAt(i)) >= 0){
				changeState(s.charAt(i));
			}
		}
		return current.end();
	}

	public State[] evalStates(String s){

		//eliminates line breakes
		s = s.replace("\n", " ");

		//removes tabs
		s = s.replace("\t", " ");

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
			stateCommands[i] = expand(sts[i].split("\\:")[1].trim());
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

		current = ss[0];

		return ss;
	}

	public int find(String[] ss, String s){
		for(int i = 0; i < ss.length; i++){
			if(ss[i].equals(s)) return i;
		}
		return -1;
	}

	public String expand(String s){

		//devide by results
		String[] inputs = s.split("\\s+");

		//expand ranges
		for(int i = 0; i < inputs.length; i++){
			String keys = inputs[i].split("\\->")[0].trim();
			while(keys.indexOf("..") >= 0){
				String range = keys.substring(keys.indexOf("..")-3, keys.indexOf("..")+5);
				keys = keys.replace(range, range(range));
			}
			//replaces the original with the expanded version
			inputs[i] = inputs[i].replace(inputs[i].split("\\->")[0].trim(), keys);
		}

		String ret = "";

		//give every key a result and build new string
		for(int i = 0; i < inputs.length; i++){
			String result = inputs[i].split("\\->")[1].trim();
			String keys = inputs[i].split("\\->")[0].trim();
			int next = 1;
			while(next < keys.length()){
				ret+= "'" + keys.charAt(next) + "'->" + result + " ";
				next = next+3;
			}
		}

		return ret.trim();
	}

	public String range(String s){
		String res = "";
		char bottom = s.charAt(1);
		char top = s.charAt(6);
		while(bottom <= top){
			res+= "'" + bottom + "'";
			bottom++;
		}
		return res;
	}
}