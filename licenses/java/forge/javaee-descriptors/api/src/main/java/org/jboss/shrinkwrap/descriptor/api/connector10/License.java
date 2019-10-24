package org.jboss.shrinkwrap.descriptor.api.connector10; 

import org.jboss.shrinkwrap.descriptor.api.Child;
/**
 * This interface defines the contract for the <code> license </code> xsd type 
 * @author <a href="mailto:ralf.battenfeld@bluewin.ch">Ralf Battenfeld</a>
 * @author <a href="mailto:alr@jboss.org">Andrew Lee Rubinger</a>
 */
public interface License<T> extends Child<T>
{
 
   // --------------------------------------------------------------------------------------------------------||
   // ClassName: License ElementName: xsd:string ElementType : description
   // MaxOccurs: -  isGeneric: true   isAttribute: false isEnum: false isDataType: true
   // --------------------------------------------------------------------------------------------------------||

   /**
    * Sets the <code>description</code> element
    * @param description the value for the element <code>description</code> 
    * @return the current instance of <code>License<T></code> 
    */
   public License<T> description(String description);

   /**
    * Returns the <code>description</code> element
    * @return the node defined for the element <code>description</code> 
    */
   public String getDescription();

   /**
    * Removes the <code>description</code> element 
    * @return the current instance of <code>License<T></code> 
    */
   public License<T> removeDescription();

 
   // --------------------------------------------------------------------------------------------------------||
   // ClassName: License ElementName: xsd:string ElementType : license-required
   // MaxOccurs: -  isGeneric: true   isAttribute: false isEnum: false isDataType: true
   // --------------------------------------------------------------------------------------------------------||

   /**
    * Sets the <code>license-required</code> element
    * @param licenseRequired the value for the element <code>license-required</code> 
    * @return the current instance of <code>License<T></code> 
    */
   public License<T> licenseRequired(String licenseRequired);

   /**
    * Returns the <code>license-required</code> element
    * @return the node defined for the element <code>license-required</code> 
    */
   public String getLicenseRequired();

   /**
    * Removes the <code>license-required</code> element 
    * @return the current instance of <code>License<T></code> 
    */
   public License<T> removeLicenseRequired();
}
