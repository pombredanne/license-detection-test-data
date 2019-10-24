<?php
/**
 * PHP Exif Copyright ValueObject
 *
 * @link        http://github.com/PHPExif/php-exif-common for the canonical source repository
 * @copyright   Copyright (c) 2016 Tom Van Herreweghe <tom@theanalogguy.be>
 * @license     http://github.com/PHPExif/php-exif-common/blob/master/LICENSE MIT License
 * @category    PHPExif
 * @package     Common
 * @codeCoverageIgnore
 */

namespace PHPExif\Common\Data\ValueObject;

use PHPExif\Common\Data\ValueObject\StringObject;

/**
 * Copyright class
 *
 * A value object to describe the Copyright data
 *
 * @category    PHPExif
 * @package     Common
 */
class Copyright extends StringObject
{
    /**
     * Creates a new instance from given Copyright object
     *
     * @param Copyright $copyright
     *
     * @return Copyright
     */
    public static function fromCopyright(Copyright $copyright)
    {
        return new self(
            (string) $copyright
        );
    }
}
