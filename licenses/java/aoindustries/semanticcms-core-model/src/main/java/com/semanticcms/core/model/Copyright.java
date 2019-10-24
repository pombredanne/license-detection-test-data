/*
 * semanticcms-core-model - Java API for modeling web page content and relationships.
 * Copyright (C) 2016, 2019  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-core-model.
 *
 * semanticcms-core-model is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-core-model is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-core-model.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.core.model;

import java.util.Objects;

/**
 * The copyright info for a book, a page, or a set of pages.
 *
 * Expected to be used with Dublin Core Terms:
 * <ul>
 * <li>http://stackoverflow.com/questions/6665312/is-the-copyright-meta-tag-valid-in-html5</li>
 * <li>https://wiki.whatwg.org/wiki/MetaExtensions</li>
 * <li>http://dublincore.org/documents/dcmi-terms/</li>
 * </ul>
 */
public class Copyright {

	private final String rightsHolder;
	private final String rights;
	private final String dateCopyrighted;

	/**
	 * @param rightsHolder  The rights holder, "" means none and null will inherit.
	 * @param rights  The rights, "" means none and null will inherit.
	 * @param dateCopyrighted  The date copyrighted, "" means none and null will inherit.
	 */
	public Copyright(
		String rightsHolder,
		String rights,
		String dateCopyrighted
	) {
		// Other checks
		if(
			rightsHolder == null
			&& rights == null
			&& dateCopyrighted == null
		) {
			throw new IllegalArgumentException("At least one of rightsHolder, rights, or dateCopyrighted required");
		}
		this.rightsHolder = rightsHolder;
		this.rights = rights;
		this.dateCopyrighted = dateCopyrighted;
	}

	@Override
	public String toString() {
		if(isEmpty()) {
			return "No copyright";
		} else {
			StringBuilder copy = new StringBuilder();
			copy.append("Copyright ©");
			if(dateCopyrighted != null && !dateCopyrighted.isEmpty()) {
				copy.append(' ').append(dateCopyrighted);
				if(!dateCopyrighted.endsWith(".")) copy.append('.');
			}
			if(rightsHolder != null && !rightsHolder.isEmpty()) {
				copy.append(' ').append(rightsHolder);
				if(!rightsHolder.endsWith(".")) copy.append('.');
			}
			if(rights != null && !rights.isEmpty()) {
				copy.append(' ').append(rights);
				if(!rights.endsWith(".")) copy.append('.');
			}
			return copy.toString();
		}
	}

	private boolean equals(
		String otherRightsHolder,
		String otherRights,
		String otherDateCopyrighted
	) {
		return
			Objects.equals(rightsHolder, otherRightsHolder)
			&& Objects.equals(rights, otherRights)
			&& Objects.equals(dateCopyrighted, otherDateCopyrighted)
		;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Copyright)) return false;
		Copyright o = (Copyright)obj;
		return equals(
			o.rightsHolder,
			o.rights,
			o.dateCopyrighted
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			rightsHolder,
			rights,
			dateCopyrighted
		);
	}

	public String getRightsHolder() {
		return rightsHolder;
	}

	public String getRights() {
		return rights;
	}

	public String getDateCopyrighted() {
		return dateCopyrighted;
	}

	/**
	 * Checks if has all fields (none need inherited).
	 */
	public boolean hasAllFields() {
		return
			rightsHolder != null
			&& rights != null
			&& dateCopyrighted != null
		;
	}

	/**
	 * Checks if the copyright is empty (has all null or blank fields)
	 */
	public boolean isEmpty() {
		return
			(rightsHolder == null || rightsHolder.isEmpty())
			&& (rights == null || rights.isEmpty())
			&& (dateCopyrighted == null || dateCopyrighted.isEmpty())
		;
	}

	/*
	 * Inherits missing fields from the given parent.
	 */
	/*
	public Copyright inheritFieldsFrom(Copyright parent) {
		String newRightsHolder    = this.rightsHolder    != null ? this.rightsHolder    : parent.rightsHolder;
		String newRights          = this.rights          != null ? this.rights          : parent.rights;
		String newDateCopyrighted = this.dateCopyrighted != null ? this.dateCopyrighted : parent.dateCopyrighted;
		// Use "this" if matches
		if(this.equals(newRightsHolder, newRights, newDateCopyrighted)) return this;
		// Use "parent" if matches
		if(parent.equals(newRightsHolder, newRights, newDateCopyrighted)) return parent;
		// Create a new object
		return new Copyright(newRightsHolder, newRights, newDateCopyrighted);
	}
	 */
}
