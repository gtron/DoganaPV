package com.gsoft.doganapt.data;

import com.gsoft.doganapt.data.adapters.UtenteAdapter;
import com.gtsoft.utils.common.FormattedDate;
import com.gtsoft.utils.sql.IDatabase2;
import com.gtsoft.utils.user2.Persona;

public class Utente extends Persona {

	protected String password = null ;
	protected String username = null ;
	protected String email = null ;

	protected Integer level = null ;

	protected FormattedDate cDate = null ;
	protected boolean active = false ;


	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setActive(Integer active) {
		this.active = active.intValue() > 0 ;
	}
	public FormattedDate getCDate() {
		return cDate;
	}
	public void setCDate(FormattedDate date) {
		cDate = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLevel() {
		if ( level == null )
			return -1;

		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public static synchronized UtenteAdapter newAdapter() throws Exception {
		return new UtenteAdapter() ;
	}
	public static synchronized UtenteAdapter newAdapter(IDatabase2 db) throws Exception {
		return new UtenteAdapter(db) ;
	}

	public interface Levels {
		static int ME = 99 ;
		static int SUPER_ADMIN = 95 ;
		static int ADMIN = 90 ;
		static int NORMAL= 0 ;
	}

	public Boolean isAdmin() {
		int l = getLevel() ;
		if ( l > 0 && l == Levels.ADMIN || l == Levels.SUPER_ADMIN || l == Levels.ME )
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

}
