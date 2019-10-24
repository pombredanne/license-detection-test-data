<?php
/**
 * Jaeger
 *
 * @copyright	Copyright (c) 2015-2016, mithra62
 * @link		http://jaeger-app.com
 * @version		1.0
 * @filesource 	./Validate/Rules/License.php
 */
namespace JaegerApp\Validate\Rules;

use JaegerApp\Validate\AbstractRule;
use JaegerApp\License as m62License;
if (! class_exists('\\mithra62\\Validate\\Rules\\License')) {

    /**
     * mithra62 - License Key Rule
     *
     * Checks an input to see if it's a valid license key
     *
     * @package Validate\Rules
     * @author Eric Lamb <eric@mithra62.com>
     */
    class License extends AbstractRule
    {

        /**
         * The Rule shortname
         * 
         * @var unknown
         */
        protected $name = 'license_key';

        /**
         * the error template
         * 
         * @var string
         */
        protected $error_message = '{field} isn\'t a valid license key';

        /**
         * (non-PHPdoc)
         * 
         * @see \mithra62\Validate\RuleInterface::validate()
         * @ignore
         *
         */
        public function validate($field, $input, array $params = array())
        {
            $license = new m62License();
            return $license->validLicense($input);
        }
    }
}