<?php

namespace Sasedev\Commons\SharedBundle\HtmlModel\Tags\SpecialLinks;

use Sasedev\Commons\SharedBundle\HtmlModel\Attributes\Href;
use Sasedev\Commons\SharedBundle\HtmlModel\Attributes\Rel;
use Sasedev\Commons\SharedBundle\HtmlModel\Attributes\Title;
use Sasedev\Commons\SharedBundle\HtmlModel\Tags\Link;

/**
 *
 * @author sasedev <seif.salah@gmail.com>
 */
class License extends Link
{

	/**
	 * Constructor
	 *
	 * @param Href|string $href
	 * @param string $title
	 */
	public function __construct($href, $title = null)
	{

		$attributes = array();

		$attributes[] = new Rel('license');
		if (null != $title) {
			$attributes[] = new Title($title);
		}
		if ($href instanceof Href) {
			$attributes[] = $href;
		} else {
			$attributes[] = new Href($href);
		}
		parent::__construct($attributes);

	}

}