<?php

namespace GPXParser\Models;

use GPXParser\InvalidGPXException;

class Copyright implements FromXML
{
    /** @var  string */
    protected $author;

    /** @var  string */
    protected $year;

    /** @var  string */
    protected $license;

    /**
     * Copyright constructor.
     * @param string $author
     * @param string $year
     * @param string $license
     */
    public function __construct($author, $year = null, $license = null)
    {
        $this->author = $author;
        $this->year = $year;
        $this->license = $license;
    }

    /**
     * @return string
     */
    public function getAuthor()
    {
        return $this->author;
    }

    /**
     * @param string $author
     */
    public function setAuthor($author)
    {
        $this->author = $author;
    }

    /**
     * @return string
     */
    public function getYear()
    {
        return $this->year;
    }

    /**
     * @param string $year
     */
    public function setYear($year)
    {
        $this->year = $year;
    }

    /**
     * @return string
     */
    public function getLicense()
    {
        return $this->license;
    }

    /**
     * @param string $license
     */
    public function setLicense($license)
    {
        $this->license = $license;
    }

    /**
     * @param \SimpleXMLElement $xml
     * @return Copyright
     * @throws InvalidGPXException
     */
    public static function fromXML(\SimpleXMLElement $xml)
    {
        $attributes = $xml->attributes();

        if ( ! $author = $attributes['author']) {
            throw new InvalidGPXException("No author specified on copyright node.");
        }

        $copyright = new Copyright((string) $author);

        if ( ! empty($xml->year)) {
            $copyright->setYear((string) $xml->year[0]);
        }

        if ( ! empty($xml->license)) {
            $copyright->setLicense((string) $xml->license[0]);
        }

        return $copyright;
    }
}