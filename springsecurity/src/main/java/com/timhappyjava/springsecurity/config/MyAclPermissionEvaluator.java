package com.timhappyjava.springsecurity.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

//public class MyPermissionEvaluator implements PermissionEvaluator { 
public class MyAclPermissionEvaluator implements PermissionEvaluator{

	protected final Log logger = LogFactory.getLog(getClass());
	private final AclService aclService;

	private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();

	private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();

	private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();

	private PermissionFactory permissionFactory = new DefaultPermissionFactory();

	public MyAclPermissionEvaluator(AclService aclService) {
		this.logger.info("Constructor for MyAclPermissionEvaluator '" + aclService + "'");
		this.aclService = aclService;
	}

	/**
	 * Determines whether the user has the given permission(s) on the domain object using
	 * the ACL configuration. If the domain object is null, returns false (this can always
	 * be overridden using a null check in the expression itself).
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
		if (domainObject == null) {
			return false;
		}
		/*Due to lazy mode in jpa, the object identity changed to hibernate proxy. To turn the object from proxy object to real object
		 * unless the ACL postfilter hasPermssion check will block this kind of proxy object*/
		Object unproxiedEntity = Hibernate.unproxy(domainObject);
		ObjectIdentity objectIdentity = this.objectIdentityRetrievalStrategy.getObjectIdentity(unproxiedEntity);
		
		this.logger.info("Checking permission 1a '" + permission + "' for object '" + domainObject + "'objectIdentity = '"+ objectIdentity +"'");
		return checkPermission(authentication, objectIdentity, permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		this.logger.info("Checking permission 1b '" + permission + "' for object '" + targetId  + "'");
		ObjectIdentity objectIdentity = this.objectIdentityGenerator.createObjectIdentity(targetId, targetType);
		return checkPermission(authentication, objectIdentity, permission);
	}

	private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {
		// Obtain the SIDs applicable to the principal
		List<Sid> sids = this.sidRetrievalStrategy.getSids(authentication);
		List<Permission> requiredPermission = resolvePermission(permission);
		this.logger.debug(LogMessage.of(() -> "Checking permission '" + permission + "' for object '" + oid + "'"));
		try {
			// Lookup only ACLs for SIDs we're interested in
			Acl acl = this.aclService.readAclById(oid, sids);
			if (acl.isGranted(requiredPermission, sids, false)) {
				this.logger.debug("Access is granted");
				return true;
			}
			this.logger.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
		}
		catch (NotFoundException nfe) {
			this.logger.debug("Returning false - no ACLs apply for this principal");
		}
		return false;
	}

	List<Permission> resolvePermission(Object permission) {
		if (permission instanceof Integer) {
			return Arrays.asList(this.permissionFactory.buildFromMask((Integer) permission));
		}
		if (permission instanceof Permission) {
			return Arrays.asList((Permission) permission);
		}
		if (permission instanceof Permission[]) {
			return Arrays.asList((Permission[]) permission);
		}
		if (permission instanceof String) {
			String permString = (String) permission;
			Permission p = buildPermission(permString);
			if (p != null) {
				return Arrays.asList(p);
			}
		}
		throw new IllegalArgumentException("Unsupported permission: " + permission);
	}

	private Permission buildPermission(String permString) {
		try {
			return this.permissionFactory.buildFromName(permString);
		}
		catch (IllegalArgumentException notfound) {
			return this.permissionFactory.buildFromName(permString.toUpperCase(Locale.ENGLISH));
		}
	}

	public void setObjectIdentityRetrievalStrategy(ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
		this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
	}

	public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
		this.objectIdentityGenerator = objectIdentityGenerator;
	}

	public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
		this.sidRetrievalStrategy = sidRetrievalStrategy;
	}

	public void setPermissionFactory(PermissionFactory permissionFactory) {
		this.permissionFactory = permissionFactory;
	}



	/*public boolean hasPermission(Authentication authentication,Object targetDomainObject, Object permission) {
	      if ("user".equals(targetDomainObject)) {
	         return this.hasPermission(authentication, permission);
	      }
	      return false;
	   }

	   //总是认为有权限
	   public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
	      return true;
	   }

	    //简单的字符串比较，相同则认为有权限

	   private boolean hasPermission(Authentication authentication, Object permission) {
		 this.logger.debug(LogMessage.of(() -> "Checking permission 1c '" + permission + "' for auth '" + authentication  + "'"));
	      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	      for (GrantedAuthority authority : authorities) {
	         if (authority.getAuthority().equals(permission)) {
	            return true;
	         }
	      }
	      return false;
	   }*/
	}
