<?php
/**
 *	File/Class License Data Class.
 *
 *	Copyright (c) 2008-2018 Christian Würker (ceusmedia.de)
 *
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	@category		Library
 *	@package		CeusMedia_Common_ADT_PHP
 *	@author			Christian Würker <christian.wuerker@ceusmedia.de>
 *	@copyright		2015-2018 Christian Würker
 *	@license		http://www.gnu.org/licenses/gpl-3.0.txt GPL 3
 *	@version		$Id$
 *	@since			0.3
 */
/**
 *	File/Class License Data Class.
 *	@category		Library
 *	@package		CeusMedia_Common_ADT_PHP
 *	@author			Christian Würker <christian.wuerker@ceusmedia.de>
 *	@copyright		2015-2018 Christian Würker
 *	@license		http://www.gnu.org/licenses/gpl-3.0.txt GPL 3
 *	@version		$Id$
 *	@since			0.3
 */
class ADT_PHP_License
{
	protected $url		= NULL;
	protected $name		= NULL;

	/**
	 *	Constructor.
	 *	@access		public
	 *	@param		string		$name		License name
	 *	@param		string		$url		License URL
	 *	@return		void
	 */
	public function __construct( $name, $url = NULL )
	{
		$this->setName( $name );
		if( !is_null( $url ) )
			$this->setUrl( $url );
	}

	/**
	 *	Returns license name.
	 *	@access		public
	 *	@return		string
	 */
	public function getName()
	{
		return $this->name;
	}
	
	/**
	 *	Returns license URL.
	 *	@access		public
	 *	@return		string
	 */
	public function getUrl()
	{
		return $this->url;
	}

	public function merge( ADT_PHP_License $license )
	{
		if( $this->name != $license->getName() )
			throw new Exception( 'Not mergable' );
		if( $license->getUrl() )
			$this->setUrl( $license->getUrl() );
	}
	
	/**
	 *	Sets license name.
	 *	@access		public
	 *	@param		string		$name		License name
	 *	@return		void
	 */
	public function setName( $name )
	{
		$this->name	= $name;
	}
	
	/**
	 *	Sets license URL.
	 *	@access		public
	 *	@param		string		$url		License URL
	 *	@return		void
	 */
	public function setUrl( $url )
	{
		$this->url	= $url;
	}
}
?>