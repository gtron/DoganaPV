package com.gtsoft.utils;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.gsoft.doganapt.cmd.Login;
import com.gtsoft.utils.common.ConfigManager;    

public class ActiveDirectory {

	private static final String PROPERTY_KEY_LDAP_URL = "ldap.url";
	private static final String PROPERTY_KEY_LDAP_BASE = "ldap.base";
	private static final String PROPERTY_KEY_LDAP_GROUP = "ldap.group";

	
	public static final String MEMBER_OF = "memberOf";

	private static LdapContext ctx;

	public static boolean checkPassword(String username, String password) {

		try {
			initContext( username,  password); 
			
			return checkUser(username);
			
		} catch (Exception e) {
			System.out.println("Error with ActiveDirectory : " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static boolean checkUser(String username) throws NamingException {
		ctx.setRequestControls(null);
		
		String ldapSearchBase = ConfigManager.getProperty(PROPERTY_KEY_LDAP_BASE);
		
		NamingEnumeration<?> namingEnum = 
				ctx.search(ldapSearchBase, "(&(objectclass=user)(sAMAccountName="+username+"))", getSimpleSearchControls());
		
		
		if ( ! namingEnum.hasMore()) 
			return false;
		else {
			
			String ldapGroup = ConfigManager.getProperty(PROPERTY_KEY_LDAP_GROUP);
			
			if ( null != ldapGroup && ldapGroup.endsWith(ldapSearchBase) ) {
				return checkGroup((SearchResult) namingEnum.next(), ldapGroup);
			} else {
				Login.debug("Cannot check if the user '"+username+"' belongs to any group! Specify a property: " + PROPERTY_KEY_LDAP_GROUP);
				return true;
			}
//			Login.debug("LDAP username '"+username+"' found in '"+ldapSearchBase+"'! " + 
//					( hasGroup?"With" : "But NOT in") +" group: " + ldapGroup);	
		}
	}

	private static boolean checkGroup(SearchResult user, String group) throws NamingException {
		
		boolean hasGroup = false;
        Attribute memberOf = user.getAttributes().get(MEMBER_OF);
        
        if (memberOf != null) {
            for ( Enumeration<?> e1 = memberOf.getAll() ; e1.hasMoreElements() ; ) {
                String currentGroup = e1.nextElement().toString();
//                Login.debug("Found Group: '"  + currentGroup + "'" ) ;
                if ( currentGroup.equalsIgnoreCase(group) ) {
                	hasGroup = true;
                	break;
                }
            }
        }
		return hasGroup;
	}

	private static Hashtable<String, Object> getEnv(String username, String password) {
		
		String ldapServerUrl = ConfigManager.getProperty(PROPERTY_KEY_LDAP_URL);
		
//		Login.debug("LDAP Url: " + ldapServerUrl );
		
		Hashtable<String, Object> env = new Hashtable<String, Object>(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.PROVIDER_URL, ldapServerUrl);

		env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		return env;
	}
	
	public static void initContext(String username, String password) throws NamingException {
		ctx = new InitialLdapContext(getEnv(username, password), null);
		
//		System.out.println("LDAP ready");
		
	}
	
	private static SearchControls getSimpleSearchControls() {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(10);
	    //String[] attrIDs = {"objectGUID"};
	    //searchControls.setReturningAttributes(attrIDs);
	    return searchControls;
	}
		
//	String organizationUnit = ConfigManager.getProperty(PROPERTY_KEY_LDAP_OU);
//	String organization = ConfigManager.getProperty(PROPERTY_KEY_LDAP_O);
	
//	env.put(Context.SECURITY_AUTHENTICATION, "simple");
//	env.put(Context.SECURITY_PRINCIPAL, 
//			String.format( "cn=%s, ou=%s, o=%s", username, organizationUnit, organization)
//			);
//	env.put(Context.SECURITY_CREDENTIALS, password);
}
