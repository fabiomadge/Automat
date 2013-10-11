class State{

	Changer[] changers;
	State def;
	boolean end;

	public State(){
		changers = null;
		def = null;
		end = false;
	}

	public State(Changer[] c, State d, boolean b){
		changers = c;
		def = d;
		end = b;
	}

	public State changeState(char c){
		for (int i = 0; i < changers.length; i++) {
			if(changers[i].getKey() == c) return changers[i].getResult();
		}
		if(def != null) return def;
		return null;
	}

	public boolean end(){
		if(end == true) return true;
		return false;
	}

	public void setEnd(boolean b){
		end = b;
	}

	public void setDefault(State s){
		def = s;
	}

	public void setChangers(Changer[] ss){
		changers = ss;
	}
}