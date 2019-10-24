/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Stores license information on any class, interface and enumeration.
 * 
 * @author Andrea Funto'
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Inherited
public @interface License {
	
	/**
	 * The most common license types.
	 * 
	 * @author Andrea Funto'
	 */
	public enum Type {
    	
    	/**
    	 * No license.
    	 */
    	NONE("No License"),
    	
    	/**
    	 * The Apache License version 2.0.
    	 */
		APACHE("Apache License 2.0"),

		/**
		 * The GNU General Public License (GPL) version 3.0.
		 */
		GNU_GPL_v30("GNU General Public license (GPL) version 3.0"),
		
		/**
		 * The GNU Affero General Public License (AGPL) version 3.0.
		 */
		GNU_AGPL_v30("GNU Affero General Public License (AGPL) version 3.0"),
		
		/**
		 * The GNU General Public License (GPL) version 2.0. 
		 */
		GNU_GPL_v20("GNU General Public License (GPL) version 2.0"),
		
		/**
		 * The GNU Lesser General Public License (LGPL) version 3.0.
		 */
		GNU_LGPL_v30("GNU Lesser General Public License (LGPL) version 3.0."),

		/**
		 * The GNU Lesser General Public License (LGPL) version 2.1.
		 */
		GNU_LGPL_v21("GNU Lesser General Public License (LGPL) version 2.1"),
		
		/**
		 * The Original 4-clause BSD License.
		 */
		BSD("Original BSD (4-clause) License"),

		/**
		 * The Revised (or 'New') 3-clause BSD License.
		 */
		REVISED_BSD("Revised BSD or New BSD (3-clause) License"),
		
		/**
		 * The Simplified BSD or Free-) 
		 */
		FREE_BSD("Simplified BSD or FreeBSD (2-clause) License"),
		
		/**
		 * The Eclipse Public License version 1.0.
		 */
		EPL_v10("Eclipse Public License version 1.0"),
		
		/**
		 * The mozilla Public License version 2.0.
		 */
		MOZILLA_v20("Mozilla Public License version 2.0"),
		
		/**
		 * The MIT License.
		 */
		MIT("MIT License"),
		
		/**
		 * The Artistic License version 2.0
		 */
		ARTISTIC("Artistic License version 2.0"),
		
		/**
		 * The Creative Commons version 1.0 Universal License.
		 */
		CC0_v10("Creative Commons version 1.0 Universal License"),
		
		/**
		 * The ISC License.
		 */
		ISC("ISC License"),
		
		/**
		 * The Public Domain (Unlicense).
		 */
		PUBLIC_DOMAIN("Public Domain (Unlicense)");
		
    	/**
    	 * @see java.lang.Enum#toString()
    	 */
		@Override
		public String toString() {
			return name;
		}
		
		/**
		 * Constructor.
		 *
		 * @param name
		 *   the name of the license.
		 */
		private Type(String name) {
			this.name = name;
		}
		
		/**
		 * The name of the license.
		 */
		private String name;
    }
    
    /**
     * A string containing the copyright holder, if any.
     * 
     * @return
     *   the copyright holder.
     */
    String copyright() default "Copyright (c) 2012-2015 Andrea Funto'";
    
    /**
     * The type of license, among the {@link License.Type} enumeration values.
     * 
     * @return
     *   the type of license, among the {@link License.Type} enumeration values.
     */
    Type type() default Type.GNU_LGPL_v30;    
}
