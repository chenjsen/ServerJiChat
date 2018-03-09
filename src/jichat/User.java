package jichat;

public class User {
	public int acount;
	public String password;
	
	public User(int a,String p) {
		this.acount = a;
		this.password = p;
	}
	
	public int getAcount() {
		return acount;
	}
	
	public boolean isCorrectAcount() {
		if(server.allUsers.contains(this)) {
			return true;
		}
		else {
			return false;
		}
	}
	@Override  
    public boolean equals(Object o) {  
        if (o == this) return true;  
        if (!(o instanceof User)) {  
            return false;  
        }  
        User user = (User) o;  
        if((this.acount == user.acount)&&(this.password.equals(user.password))) {
        	return true;
        }
        else {
        	return false;
        }
    }  
}
