<?php

namespace eLife\ApiSdk\Model;

final class Copyright
{
    private $license;
    private $statement;
    private $holder;

    public function __construct(string $license, string $statement, string $holder = null)
    {
        $this->license = $license;
        $this->statement = $statement;
        $this->holder = $holder;
    }

    /**
     * @return string
     */
    public function getLicense() : string
    {
        return $this->license;
    }

    /**
     * @return string
     */
    public function getStatement() : string
    {
        return $this->statement;
    }

    /**
     * @return string|null
     */
    public function getHolder()
    {
        return $this->holder;
    }
}
